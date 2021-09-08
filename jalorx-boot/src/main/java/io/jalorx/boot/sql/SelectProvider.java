package io.jalorx.boot.sql;
import java.util.Map;
import java.util.regex.Pattern;

public interface SelectProvider {
	Pattern S= Pattern.compile("(select) (\\S+) (from)");
	
    Map<String, Object> getParameters();
    String getSql();
    
    default String getCountSql() {
    	return S.matcher(getSql()).replaceFirst("$1 count(1) $3");
    }
}