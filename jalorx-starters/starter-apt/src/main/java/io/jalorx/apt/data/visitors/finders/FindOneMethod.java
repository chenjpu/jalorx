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

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.reflect.ClassUtils;
import io.micronaut.data.annotation.Where;
import io.micronaut.data.model.query.QueryModel;
import io.micronaut.data.processor.model.SourcePersistentEntity;
import io.micronaut.data.processor.visitors.AnnotationMetadataHierarchy;
import io.micronaut.data.processor.visitors.MatchContext;
import io.micronaut.data.processor.visitors.MethodMatchContext;
import io.micronaut.data.processor.visitors.finders.MethodMatchInfo;
import io.micronaut.data.processor.visitors.finders.TypeUtils;
import io.micronaut.inject.ast.ClassElement;
import io.micronaut.inject.ast.MethodElement;

/**
 * Simple list method support.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public class FindOneMethod extends AbstractPatternBasedMethod {

	/**
	 * The prefixes used.
	 */
	public static final String[] PREFIXES = { "get", "find", "search", "query" };

	/**
	 * Find one method.
	 */
	public FindOneMethod() {
		super(SqlTypeUtils.computePattern(PREFIXES));
	}

	@Override
	public int getOrder() {
		// lower priority than dynamic finder
		return DEFAULT_POSITION - 200;
	}

	@Override
	public boolean isMethodMatch(@NonNull MethodElement methodElement, @NonNull MatchContext matchContext) {
		return super.isMethodMatch(methodElement, matchContext)
				&& SqlTypeUtils.isFirstParameterSqlProvider(methodElement)
				&& isCompatibleReturnType(methodElement, matchContext);
	}

	protected boolean isCompatibleReturnType(@NonNull MethodElement methodElement, @NonNull MatchContext matchContext) {
		ClassElement returnType = methodElement.getGenericReturnType();

		if (!returnType.getName().equals("void")) {
			return returnType.hasStereotype(Introspected.class) || returnType.isPrimitive()
					|| ClassUtils.isJavaBasicType(returnType.getName()) || TypeUtils.isContainerType(returnType);
		}
		return false;
	}

	@Override
	public MethodMatchInfo buildMatchInfo(MethodMatchContext matchContext) {
		SourcePersistentEntity rootEntity = matchContext.getRootEntity();
		ClassElement queryResultType = rootEntity.getClassElement();
		if (matchContext.supportsImplicitQueries() && hasNoWhereDeclaration(matchContext)) {
			return buildInfo(matchContext, queryResultType, null);
		} else {
			return buildInfo(matchContext, queryResultType, QueryModel.from(rootEntity));
		}
	}

	private boolean hasNoWhereDeclaration(@NonNull MethodMatchContext matchContext) {
		final boolean repositoryHasWhere = new AnnotationMetadataHierarchy(matchContext.getRepositoryClass(),
				matchContext.getMethodElement()).hasAnnotation(Where.class);
		final boolean entityHasWhere = matchContext.getRootEntity().hasAnnotation(Where.class);
		return !repositoryHasWhere && !entityHasWhere;
	}

}
