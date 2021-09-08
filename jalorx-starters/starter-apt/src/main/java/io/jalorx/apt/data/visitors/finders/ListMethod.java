/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jalorx.apt.data.visitors.finders;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.processor.visitors.MatchContext;
import io.micronaut.data.processor.visitors.finders.TypeUtils;
import io.micronaut.inject.ast.ClassElement;
import io.micronaut.inject.ast.MethodElement;

/**
 * Simple list method support.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public class ListMethod extends AbstractListMethod {

	/**
	 * The prefixes used.
	 */
	public static final String[] PREFIXES = { "list", "find", "search", "query" };

	/**
	 * Default constructor.
	 */
	public ListMethod() {
		super(PREFIXES);
	}

	@Override
	public int getOrder() {
		return DEFAULT_POSITION - 250;
	}

	@Override
	public boolean isMethodMatch(@NonNull MethodElement methodElement, @NonNull MatchContext matchContext) {
		return SqlTypeUtils.isFirstParameterSqlProvider(methodElement)
				&& (super.isMethodMatch(methodElement, matchContext) || methodElement.hasAnnotation(Query.class))
				&& isValidReturnType(matchContext.getReturnType(), matchContext);

	}

	/**
	 * Dictates whether this is a valid return type.
	 * 
	 * @param returnType   The return type.
	 * @param matchContext The match context
	 * @return True if it is
	 */
	protected boolean isValidReturnType(@NonNull ClassElement returnType, MatchContext matchContext) {
		return TypeUtils.isContainerType(returnType);
	}
}
