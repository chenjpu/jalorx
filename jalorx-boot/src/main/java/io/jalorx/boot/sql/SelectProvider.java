package io.jalorx.boot.sql;
import java.util.Map;

public interface SelectProvider<E> {
	Class<E> getRootEntity();
    Map<String, Object> getParameters();
    String getSql();
}