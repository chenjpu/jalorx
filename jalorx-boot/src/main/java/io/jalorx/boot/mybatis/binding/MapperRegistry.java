package io.jalorx.boot.mybatis.binding;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.SqlSession;

public class MapperRegistry {

	private final Map<Class<?>, MyBatisRepositoryProxy> knownMappers = new ConcurrentHashMap<>(20);

	public boolean hasMapper(Class<?> type) {
		return knownMappers.containsKey(type);
	}

	public MyBatisRepositoryProxy addMapper(Class<?> type, SqlSession sqlSession) {
		if (!hasMapper(type)) {
			boolean loadCompleted = false;
			try {
				knownMappers.put(type, new MyBatisRepositoryProxy(type, sqlSession));
				// It's important that the type is added before the parser is run
				// otherwise the binding may automatically be attempted by the
				// mapper parser. If the type is already known, it won't try.
				MapperAnnotationBuilder parser = new MapperAnnotationBuilder(sqlSession.getConfiguration(), type);
				parser.parse();
				loadCompleted = true;
			} finally {
				if (!loadCompleted) {
					knownMappers.remove(type);
				}
			}
		}
		return knownMappers.get(type);
	}

	/**
	 * Gets the mappers.
	 *
	 * @return the mappers
	 * @since 3.2.2
	 */
	public Collection<Class<?>> getMappers() {
		return Collections.unmodifiableCollection(knownMappers.keySet());
	}
}
