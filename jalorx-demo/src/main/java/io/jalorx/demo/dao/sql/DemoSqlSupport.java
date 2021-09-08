package io.jalorx.demo.dao.sql;

import static io.jalorx.boot.sql.dsl.SqlBuilder.isEqualTo;
import static io.jalorx.boot.sql.dsl.SqlBuilder.isGreaterThan;
import static io.jalorx.boot.sql.dsl.SqlBuilder.select;

import java.sql.JDBCType;

import io.jalorx.boot.sql.SelectProvider;
import io.jalorx.boot.sql.dsl.BasicColumn;
import io.jalorx.boot.sql.dsl.SqlColumn;
import io.jalorx.boot.sql.dsl.SqlTable;

public final class DemoSqlSupport {
	static final DemoTable person = new DemoTable();
	
	static final BasicColumn all = person.all;
	static final SqlColumn<Long> id = person.id;
	static final SqlColumn<String> name = person.name;
	static final SqlColumn<Integer> age = person.age;

	static final class DemoTable extends SqlTable {
		final BasicColumn all = allColumns();
		final SqlColumn<Long> id = column("id", JDBCType.INTEGER);
		final SqlColumn<String> name = column("name", JDBCType.VARCHAR);
		final SqlColumn<Integer> age = column("age", JDBCType.INTEGER);

		public DemoTable() {
			super("tpl_demo_t");
		}
	}
	
	
	public static final SelectProvider queryPerson(Long userID) {
		SelectProvider selectStatement = select(all)
				.from(person)
				.where(id, isEqualTo(userID))
				.build().render();

		return selectStatement;
	}
	
	public static final SelectProvider queryAll(int rage) {
		SelectProvider selectStatement = select(all)
				.from(person)
				.where(age, isGreaterThan(rage))
				//.and(name, isEqualTo("ssssss"))
				.build().render();

		return selectStatement;
	}
	
	
}