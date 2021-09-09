package io.jalorx.demo;

import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {

		String sql = "select * from tpl_demo_t where age > ? select * from tt wheres";
		
		Pattern S= Pattern.compile("(select) (\\S+) (from)");
		
		System.out.println(S.matcher(sql).replaceFirst("$1 count(1) $3"));
	}

}
