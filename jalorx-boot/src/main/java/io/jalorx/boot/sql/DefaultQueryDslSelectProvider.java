package io.jalorx.boot.sql;

import static io.jalorx.boot.sql.dsl.SqlBuilder.select;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.sql.dsl.BasicColumn;
import io.jalorx.boot.sql.dsl.SqlColumn;
import io.jalorx.boot.sql.dsl.SqlTable;
import io.jalorx.boot.sql.dsl.select.QueryExpressionDSL;
import io.jalorx.boot.sql.dsl.select.SelectModel;
import io.micronaut.core.util.ArgumentUtils;
import io.micronaut.data.model.PersistentEntity;
import io.micronaut.data.model.runtime.RuntimeEntityRegistry;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class DefaultQueryDslSelectProvider implements QueryDslSelectProvider {
	private final Map<Class<?>, QueryDslTable> sqlTables = new ConcurrentHashMap<>(10);
	
	
	@Inject
	RuntimeEntityRegistry registry;

	@Override
	public SelectProvider from(QueryDsl queryDsl,Class<?> clazz) {
		
		QueryDslTable table = getQueryDslTable(clazz);
		QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder = 
				select(table.all)
				.from(table)
				.where();
		
		queryDsl.getCommands().forEach(cs->{
			cs.and(builder,table.Column(cs.field));
		});
		
		return builder.build().render();
	}
	
	
	private QueryDslTable getQueryDslTable(Class<?> clazz) {
		ArgumentUtils.requireNonNull("type", clazz);
		QueryDslTable table = sqlTables.get(clazz);
        if (table == null) {
        	table = new QueryDslTable(registry.getEntity(clazz));
        	sqlTables.put(clazz, table);
        }
        return table;
	}
	
	static final class QueryDslTable extends SqlTable {
		final BasicColumn all = allColumns();
		final Map<String, SqlColumn<Object>> sqlColumns = new HashMap<>(10); 
		public QueryDslTable(PersistentEntity entity) {
			super(entity.getPersistedName());
			sqlColumns.put("id", column("id"));
			entity.getPersistentProperties().forEach(e -> {
				sqlColumns.put(e.getName(), column(e.getPersistedName()));
			});
		}
		
		SqlColumn<Object> Column(String id){
			SqlColumn<Object> sc = sqlColumns.get(id);
			if(sc == null) {
				throw ErrCode.A_DSL_NOEXIST.wrap(id);
			}
			return sc;
		}
	}

}
