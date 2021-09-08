package io.jalorx.boot.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.jalorx.boot.dao.DynamicDao;
import io.jalorx.boot.sql.SelectProvider;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.runtime.JdbcOperations;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

@Singleton
public class DynamicDaoImpl implements DynamicDao {

	private final JdbcOperations jdbcOperations;

	public DynamicDaoImpl(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public <E> Optional<E> findOne(SelectProvider<E> sqlProvider) {

		return jdbcOperations.prepareStatement(sqlProvider.getSql(), (ps) -> {

			for (Map.Entry<String, Object> param : sqlProvider.getParameters().entrySet()) {
				ps.setObject(Integer.parseInt(param.getKey().substring(1)), param.getValue());
			}

			try (ResultSet openedRs = ps.executeQuery()) {
				if(openedRs.next()) {
					return Optional.of(jdbcOperations.readEntity(openedRs, sqlProvider.getRootEntity()));
				}
				return Optional.<E>empty();
			} catch (SQLException e) {
				throw new DataAccessException("SQL Error executing Query: " + e.getMessage(), e);
			}
		});
	}

	@Override
	public <E> Iterable<E> findAll(SelectProvider<E> sqlProvider) {
		return jdbcOperations.prepareStatement(sqlProvider.getSql(), (ps) -> {
			for (Map.Entry<String, Object> param : sqlProvider.getParameters().entrySet()) {
				ps.setObject(Integer.parseInt(param.getKey().substring(1)), param.getValue());
			}
			try (ResultSet openedRs = ps.executeQuery()) {
				return jdbcOperations.entityStream(openedRs, sqlProvider.getRootEntity()).collect(Collectors.toList());
			} catch (SQLException e) {
				throw new DataAccessException("SQL Error executing Query: " + e.getMessage(), e);
			}
		});
	}

	@Override
	public <E> Page<E> findAll(SelectProvider<E> sqlProvider, Pageable pageable) {
		return null;
	}

	@Override
	public boolean exists(SelectProvider<?> sqlProvider) {
		return jdbcOperations.prepareStatement(sqlProvider.getSql(), (ps) -> {
			for (Map.Entry<String, Object> param : sqlProvider.getParameters().entrySet()) {
				ps.setObject(Integer.parseInt(param.getKey().substring(1)), param.getValue());
			}
			try (ResultSet openedRs = ps.executeQuery()) {
				return openedRs.next();
			} catch (SQLException e) {
				throw new DataAccessException("SQL Error executing Query: " + e.getMessage(), e);
			}
		});
	}

	@Override
	public long count(SelectProvider<?> sqlProvider) {
		return jdbcOperations.prepareStatement(sqlProvider.getSql(), (ps) -> {
			for (Map.Entry<String, Object> param : sqlProvider.getParameters().entrySet()) {
				ps.setObject(Integer.parseInt(param.getKey().substring(1)), param.getValue());
			}
			try (ResultSet openedRs = ps.executeQuery()) {
				return jdbcOperations.readDTO(openedRs, sqlProvider.getRootEntity(), Long.class);
			} catch (SQLException e) {
				throw new DataAccessException("SQL Error executing Query: " + e.getMessage(), e);
			}
		});
	}

}
