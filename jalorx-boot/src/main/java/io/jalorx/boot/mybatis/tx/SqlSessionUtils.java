/**
 * Copyright 2010-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.jalorx.boot.mybatis.tx;

import java.sql.SQLException;

import org.apache.commons.lang3.Validate;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import io.jalorx.boot.mybatis.DefautlDatabaseIdProvider;
import io.jalorx.boot.mybatis.dialect.DialectUtil;
import io.jalorx.boot.mybatis.logging.Logger;
import io.jalorx.boot.mybatis.logging.LoggerFactory;
import io.jalorx.boot.mybatis.tx.transaction.MicronautManagedTransactionFactory;
import io.micronaut.transaction.exceptions.TransactionUsageException;
import io.micronaut.transaction.jdbc.DataSourceUtils;
import io.micronaut.transaction.support.TransactionSynchronizationAdapter;
import io.micronaut.transaction.support.TransactionSynchronizationManager;

/**
 * Handles MyBatis SqlSession life cycle. It can register and get SqlSessions
 * from Spring {@code TransactionSynchronizationManager}. Also works if no
 * transaction is active.
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 */
public final class SqlSessionUtils {

	private static final DatabaseIdProvider ID_PROVIDER = DefautlDatabaseIdProvider.get();

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionUtils.class);

	private static final String NO_EXECUTOR_TYPE_SPECIFIED       = "No ExecutorType specified";
	private static final String NO_SQL_SESSION_FACTORY_SPECIFIED = "No SqlSessionFactory specified";
	private static final String NO_SQL_SESSION_SPECIFIED         = "No SqlSession specified";

	/**
	 * This class can't be instantiated, exposes static utility methods only.
	 */
	private SqlSessionUtils() {
		// do nothing
	}

	/**
	 * Creates a new MyBatis {@code SqlSession} from the {@code SqlSessionFactory}
	 * provided as a parameter and using its {@code DataSource} and
	 * {@code ExecutorType}
	 *
	 * @param sessionFactory a MyBatis {@code SqlSessionFactory} to create new
	 *        sessions
	 * @return a MyBatis {@code SqlSession}
	 * @throws TransientDataAccessResourceException if a transaction is active and
	 *         the {@code SqlSessionFactory} is not using a
	 *         {@code SpringManagedTransactionFactory}
	 */
	public static SqlSession getSqlSession(SqlSessionFactory sessionFactory) {
		ExecutorType executorType = sessionFactory.getConfiguration()
				.getDefaultExecutorType();
		return getSqlSession(sessionFactory, executorType);
	}

	/**
	 * Gets an SqlSession from Spring Transaction Manager or creates a new one if
	 * needed. Tries to get a SqlSession out of current transaction. If there is not
	 * any, it creates a new one. Then, it synchronizes the SqlSession with the
	 * transaction if Spring TX is active and
	 * <code>SpringManagedTransactionFactory</code> is configured as a transaction
	 * manager.
	 *
	 * @param sessionFactory a MyBatis {@code SqlSessionFactory} to create new
	 *        sessions
	 * @param executorType The executor type of the SqlSession to create
	 * @param exceptionTranslator Optional. Translates SqlSession.commit()
	 *        exceptions to Spring exceptions.
	 * @return an SqlSession managed by Spring Transaction Manager
	 * @throws TransientDataAccessResourceException if a transaction is active and
	 *         the {@code SqlSessionFactory} is not using a
	 *         {@code SpringManagedTransactionFactory}
	 * @see MicronautManagedTransactionFactory
	 */
	public static SqlSession getSqlSession(SqlSessionFactory sessionFactory,
			ExecutorType executorType) {

		Validate.notNull(sessionFactory, NO_SQL_SESSION_FACTORY_SPECIFIED);
		Validate.notNull(executorType, NO_EXECUTOR_TYPE_SPECIFIED);

		SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

		SqlSession session = sessionHolder(executorType, holder);
		if (session != null) {
			return session;
		}

		LOGGER.debug(() -> "Creating a new SqlSession");
		session = sessionFactory.openSession(executorType);

		registerSessionHolder(sessionFactory, executorType, session);

		return session;
	}

	/**
	 * Register session holder if synchronization is active (i.e. a Spring TX is
	 * active).
	 *
	 * Note: The DataSource used by the Environment should be synchronized with the
	 * transaction either through DataSourceTxMgr or another tx synchronization.
	 * Further assume that if an exception is thrown, whatever started the
	 * transaction will handle closing / rolling back the Connection associated with
	 * the SqlSession.
	 * 
	 * @param sessionFactory sqlSessionFactory used for registration.
	 * @param executorType executorType used for registration.
	 * @param exceptionTranslator persistenceExceptionTranslator used for
	 *        registration.
	 * @param session sqlSession used for registration.
	 */
	private static void registerSessionHolder(SqlSessionFactory sessionFactory,
			ExecutorType executorType, SqlSession session) {
		SqlSessionHolder holder;
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			Environment environment = sessionFactory.getConfiguration()
					.getEnvironment();

			if (environment.getTransactionFactory() instanceof MicronautManagedTransactionFactory) {
				LOGGER.debug(() -> "Registering transaction synchronization for SqlSession [" + session + "]");

				holder = new SqlSessionHolder(session, executorType);
				String databaseId = null;
				try {
					databaseId = ID_PROVIDER.getDatabaseId(environment.getDataSource());
					DialectUtil.bind(databaseId);
				} catch (SQLException e) {
					LOGGER.error(() -> "Could not get a databaseId from dataSource", e);
				}

				TransactionSynchronizationManager.bindResource(sessionFactory, holder);
				TransactionSynchronizationManager
						.registerSynchronization(new SqlSessionSynchronization(databaseId, holder, sessionFactory));
				holder.setSynchronizedWithTransaction(true);
				holder.requested();
			} else {
				if (TransactionSynchronizationManager.getResource(environment.getDataSource()) == null) {
					LOGGER.debug(() -> "SqlSession [" + session
							+ "] was not registered for synchronization because DataSource is not transactional");
				} else {
					throw new TransactionUsageException(
							"SqlSessionFactory must be using a MicronautManagedTransactionFactory in order to use micronaut transaction synchronization");
				}
			}
		} else {
			LOGGER.debug(() -> "SqlSession [" + session
					+ "] was not registered for synchronization because synchronization is not active");
		}

	}

	private static SqlSession sessionHolder(ExecutorType executorType, SqlSessionHolder holder) {
		SqlSession session = null;
		if (holder != null && holder.isSynchronizedWithTransaction()) {
			if (holder.getExecutorType() != executorType) {
				throw new TransactionUsageException(
						"Cannot change the ExecutorType when there is an existing transaction");
			}

			holder.requested();

			LOGGER.debug(() -> "Fetched SqlSession [" + holder.getSqlSession() + "] from current transaction");
			session = holder.getSqlSession();
		}
		return session;
	}

	/**
	 * Checks if {@code SqlSession} passed as an argument is managed by Spring
	 * {@code TransactionSynchronizationManager} If it is not, it closes it,
	 * otherwise it just updates the reference counter and lets Spring call the
	 * close callback when the managed transaction ends
	 *
	 * @param session a target SqlSession
	 * @param sessionFactory a factory of SqlSession
	 */
	public static void closeSqlSession(SqlSession session, SqlSessionFactory sessionFactory) {
		Validate.notNull(session, NO_SQL_SESSION_SPECIFIED);
		Validate.notNull(sessionFactory, NO_SQL_SESSION_FACTORY_SPECIFIED);

		SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		if ((holder != null) && (holder.getSqlSession() == session)) {
			LOGGER.debug(() -> "Releasing transactional SqlSession [" + session + "]");
			holder.released();
		} else {
			LOGGER.debug(() -> "Closing non transactional SqlSession [" + session + "]");
			session.close();
		}
	}

	/**
	 * Returns if the {@code SqlSession} passed as an argument is being managed by
	 * Spring
	 *
	 * @param session a MyBatis SqlSession to check
	 * @param sessionFactory the SqlSessionFactory which the SqlSession was built
	 *        with
	 * @return true if session is transactional, otherwise false
	 */
	public static boolean isSqlSessionTransactional(SqlSession session,
			SqlSessionFactory sessionFactory) {
		Validate.notNull(session, NO_SQL_SESSION_SPECIFIED);
		Validate.notNull(sessionFactory, NO_SQL_SESSION_FACTORY_SPECIFIED);

		SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

		return (holder != null) && (holder.getSqlSession() == session);
	}

	/**
	 * Callback for cleaning up resources. It cleans
	 * TransactionSynchronizationManager and also commits and closes the
	 * {@code SqlSession}. It assumes that {@code Connection} life cycle will be
	 * managed by {@code DataSourceTransactionManager} or
	 * {@code JtaTransactionManager}
	 */
	private static final class SqlSessionSynchronization extends TransactionSynchronizationAdapter {

		private final SqlSessionHolder holder;

		private final SqlSessionFactory sessionFactory;

		private boolean holderActive = true;

		private final String databaseId;

		public SqlSessionSynchronization(String databaseId, SqlSessionHolder holder,
				SqlSessionFactory sessionFactory) {
			Validate.notNull(databaseId, "Parameter 'databaseId' must be not null");
			Validate.notNull(holder, "Parameter 'holder' must be not null");
			Validate.notNull(sessionFactory, "Parameter 'sessionFactory' must be not null");
			this.databaseId     = databaseId;
			this.holder         = holder;
			this.sessionFactory = sessionFactory;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getOrder() {
			// order right before any Connection synchronization
			return DataSourceUtils.CONNECTION_SYNCHRONIZATION_ORDER - 1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void suspend() {
			if (this.holderActive) {
				LOGGER.debug(() -> "Transaction synchronization suspending SqlSession ["
						+ this.holder.getSqlSession() + "]");
				TransactionSynchronizationManager.unbindResource(this.sessionFactory);
				DialectUtil.release();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void resume() {
			if (this.holderActive) {
				LOGGER.debug(() -> "Transaction synchronization resuming SqlSession ["
						+ this.holder.getSqlSession() + "]");
				TransactionSynchronizationManager.bindResource(this.sessionFactory, this.holder);
				DialectUtil.bind(this.databaseId);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beforeCommit(boolean readOnly) {
			// Connection commit or rollback will be handled by ConnectionSynchronization or
			// DataSourceTransactionManager.
			// But, do cleanup the SqlSession / Executor, including flushing BATCH statements so
			// they are actually executed.
			// SpringManagedTransaction will no-op the commit over the jdbc connection
			// TODO This updates 2nd level caches but the tx may be rolledback later on!
			if (TransactionSynchronizationManager.isActualTransactionActive()) {
				LOGGER.debug(() -> "Transaction synchronization committing SqlSession ["
						+ this.holder.getSqlSession() + "]");
				this.holder.getSqlSession()
						.commit();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beforeCompletion() {
			// Issue #18 Close SqlSession and deregister it now
			// because afterCompletion may be called from a different thread
			if (!this.holder.isOpen()) {
				LOGGER.debug(() -> "Transaction synchronization deregistering SqlSession ["
						+ this.holder.getSqlSession() + "]");
				TransactionSynchronizationManager.unbindResource(sessionFactory);
				this.holderActive = false;
				LOGGER.debug(() -> "Transaction synchronization closing SqlSession ["
						+ this.holder.getSqlSession() + "]");
				this.holder.getSqlSession()
						.close();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void afterCompletion(Status status) {
			if (this.holderActive) {
				// afterCompletion may have been called from a different thread
				// so avoid failing if there is nothing in this one
				LOGGER.debug(() -> "Transaction synchronization deregistering SqlSession ["
						+ this.holder.getSqlSession() + "]");
				TransactionSynchronizationManager.unbindResourceIfPossible(sessionFactory);
				this.holderActive = false;
				LOGGER.debug(() -> "Transaction synchronization closing SqlSession ["
						+ this.holder.getSqlSession() + "]");
				this.holder.getSqlSession()
						.close();
			}
			this.holder.reset();
		}
	}

}
