package io.jalorx.apt.data.visitors.finders;

import java.util.regex.Pattern;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.inject.ast.MethodElement;
import io.micronaut.inject.ast.ParameterElement;

public class SqlTypeUtils {

	public static boolean isFirstParameterSqlProvider(@NonNull MethodElement methodElement) {
		final ParameterElement[] parameters = methodElement.getParameters();
		return parameters.length > 0 && parameters[0].getType().isAssignable("io.jalorx.boot.sql.SelectProvider");

	}
	
	public static Pattern computePattern(String[] prefixes) {
        String prefixPattern = String.join("|", prefixes);
        return Pattern.compile("^((" + prefixPattern + ")(\\S*?))$");
    }
}
