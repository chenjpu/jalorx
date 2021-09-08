/*
 * Copyright 2017-2021 original authors
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
import io.micronaut.core.annotation.Nullable;
import io.jalorx.data.intercept.FindAllInterceptor;
import io.jalorx.data.intercept.FindOneInterceptor;
import io.jalorx.data.intercept.FindOptionalInterceptor;
import io.jalorx.data.intercept.FindPageInterceptor;
import io.jalorx.data.intercept.FindSliceInterceptor;
import io.jalorx.data.intercept.FindStreamInterceptor;
import io.jalorx.data.intercept.reactive.FindAllReactiveInterceptor;
import io.jalorx.data.intercept.reactive.FindOneReactiveInterceptor;
import io.jalorx.data.intercept.reactive.FindPageReactiveInterceptor;
import io.jalorx.data.intercept.reactive.FindSliceReactiveInterceptor;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.core.reflect.ClassUtils;
import io.micronaut.data.annotation.TypeRole;
import io.micronaut.data.intercept.DataInterceptor;
import io.micronaut.data.model.Slice;
import io.micronaut.data.processor.visitors.MethodMatchContext;
import io.micronaut.data.processor.visitors.finders.MethodMatchInfo;
import io.micronaut.inject.ast.ClassElement;
import org.reactivestreams.Publisher;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Finders utils.
 */
@Internal
interface FindersUtils {

    static Map.Entry<ClassElement, Class<? extends DataInterceptor>> resolveInterceptorTypeByOperationType(boolean hasEntityParameter,
                                                                                                           boolean hasMultipleEntityParameter,
                                                                                                           MethodMatchInfo.OperationType operationType,
                                                                                                           MethodMatchContext matchContext) {
        ClassElement returnType = matchContext.getReturnType();
        switch (operationType) {
            case QUERY:
                return resolveFindInterceptor(matchContext, returnType);
            default:
                throw new IllegalStateException("Cannot pick interceptor for an operation type: " + operationType + " and a return type: " + returnType);
        }
    }

   


    static Map.Entry<ClassElement, Class<? extends DataInterceptor>> resolveFindInterceptor(MethodMatchContext matchContext, ClassElement returnType) {
        ClassElement firstTypeArgument = returnType.getFirstTypeArgument().orElse(null);
        Map.Entry<ClassElement, Class<? extends DataInterceptor>> entry = null;
        if (isReactiveType(matchContext, returnType)) {
            entry = resolveReactiveFindInterceptor(matchContext, returnType, firstTypeArgument);
        } else {
            entry = resolveSyncFindInterceptor(matchContext, returnType);
        }
        return entry;
    }

  

    static Map.Entry<ClassElement, Class<? extends DataInterceptor>> resolveReactiveFindInterceptor(
            @NonNull MethodMatchContext matchContext, @NonNull ClassElement returnType, @NonNull ClassElement reactiveType) {
        ClassElement firstTypeArgument = reactiveType.getFirstTypeArgument().orElse(null);
        if (isPage(matchContext, reactiveType)) {
            return typeAndInterceptorEntry(firstTypeArgument, FindPageReactiveInterceptor.class);
        } else if (isSlice(matchContext, reactiveType)) {
            return typeAndInterceptorEntry(firstTypeArgument, FindSliceReactiveInterceptor.class);
        } else if (isReactiveSingleResult(matchContext, returnType)) {
            return typeAndInterceptorEntry(reactiveType, FindOneReactiveInterceptor.class);
        } else {
            return typeAndInterceptorEntry(reactiveType, FindAllReactiveInterceptor.class);
        }
    }
    
	static Map.Entry<ClassElement, Class<? extends DataInterceptor>> resolveSyncFindInterceptor(@NonNull MethodMatchContext matchContext,
            @NotNull ClassElement returnType) {
		ClassElement firstTypeArgument = returnType.getFirstTypeArgument().orElse(null);
		if (isPage(matchContext, returnType)) {
			return typeAndInterceptorEntry(firstTypeArgument, FindPageInterceptor.class);
		} else if (isSlice(matchContext, returnType)) {
			return typeAndInterceptorEntry(firstTypeArgument, FindSliceInterceptor.class);
		} else if (isContainer(matchContext, returnType, Iterable.class)) {
			return typeAndInterceptorEntry(firstTypeArgument, FindAllInterceptor.class);
		} else if (isContainer(matchContext, returnType, Stream.class)) {
			return typeAndInterceptorEntry(firstTypeArgument, FindStreamInterceptor.class);
		} else if (isContainer(matchContext, returnType, Optional.class)) {
			return typeAndInterceptorEntry(firstTypeArgument, FindOptionalInterceptor.class);
		} else if (isContainer(matchContext, returnType, Publisher.class)) {
			return typeAndInterceptorEntry(firstTypeArgument, FindAllReactiveInterceptor.class);
		} else {
			return typeAndInterceptorEntry(returnType, FindOneInterceptor.class);
		}
}
    

    static Map.Entry<ClassElement, Class<? extends DataInterceptor>> typeAndInterceptorEntry(ClassElement type, Class<? extends DataInterceptor> interceptor) {
        return new AbstractMap.SimpleEntry<>(type, interceptor);
    }

    static boolean isFutureType(MethodMatchContext methodMatchContext, @Nullable ClassElement type) {
        return isOneOfContainers(methodMatchContext, type, CompletionStage.class, Future.class);
    }

    static boolean isReactiveType(MethodMatchContext methodMatchContext, @Nullable ClassElement type) {
        return isContainer(methodMatchContext, type, Publisher.class)
                || type != null && type.getPackageName().equals("io.reactivex")
                && (type.getTypeArguments().isEmpty() || isContainer(methodMatchContext, type, type.getName())); // Validate container argument
    }

    static boolean isPage(MethodMatchContext methodMatchContext, ClassElement typeArgument) {
        boolean matches = methodMatchContext.isTypeInRole(typeArgument, TypeRole.PAGE);
        if (matches && !methodMatchContext.hasParameterInRole(TypeRole.PAGEABLE)) {
            methodMatchContext.fail("Method must accept an argument that is a Pageable");
        }
        return matches;
    }

    static boolean isSlice(MethodMatchContext methodMatchContext, ClassElement typeArgument) {
        boolean matches = methodMatchContext.isTypeInRole(typeArgument, TypeRole.SLICE);
        if (matches && !methodMatchContext.hasParameterInRole(TypeRole.PAGEABLE)) {
            methodMatchContext.fail("Method must accept an argument that is a Pageable");
        }
        return isContainer(methodMatchContext, typeArgument, Slice.class);
    }

    static boolean isContainer(MethodMatchContext methodMatchContext, ClassElement typeArgument, Class<?> containerType) {
        if (typeArgument == null) {
            return false;
        }
        if (typeArgument.isAssignable(containerType)) {
            ClassElement type = typeArgument.getFirstTypeArgument().orElse(null);
            if (type == null) {
                methodMatchContext.failAndThrow("'" + containerType + "' return type missing type argument");
            }
            return true;
        }
        return false;
    }

    static boolean isOneOfContainers(MethodMatchContext methodMatchContext, ClassElement typeArgument, Class<?>... containers) {
        if (typeArgument == null) {
            return false;
        }
        for (Class<?> containerType : containers) {
            if (isContainer(methodMatchContext, typeArgument, containerType)) {
                return true;
            }
        }
        return false;
    }

    static boolean isContainer(MethodMatchContext methodMatchContext, ClassElement typeArgument, String containerType) {
        if (typeArgument.isAssignable(containerType)) {
            ClassElement type = typeArgument.getFirstTypeArgument().orElse(null);
            if (type == null) {
                methodMatchContext.failAndThrow("'" + containerType + "' return type missing type argument");
            }
            return true;
        }
        return false;
    }

    static boolean isValidResultType(ClassElement returnType) {
        return returnType.hasStereotype(Introspected.class) || ClassUtils.isJavaBasicType(returnType.getName()) || returnType.isPrimitive();
    }

    static boolean isReactiveSingleResult(MethodMatchContext matchContext, ClassElement returnType) {
        return returnType.hasStereotype(SingleResult.class)
                || isContainer(matchContext, returnType, "io.reactivex.Single")
                || isContainer(matchContext, returnType, "reactor.core.publisher.Mono");
    }

    /**
     * Obtain the interceptor element for the given class.
     *
     * @param matchContext The match context
     * @param type         The type
     * @return The element
     */
    static ClassElement getInterceptorElement(@NonNull MethodMatchContext matchContext, Class<? extends DataInterceptor> type) {
        return matchContext.getVisitorContext().getClassElement(type).orElseGet(() -> new FindersUtils.DynamicClassElement(type));
    }

    /**
     * Obtain the interceptor element for the given class name.
     *
     * @param matchContext The match context
     * @param type         The type
     * @return The element
     */
    static ClassElement getInterceptorElement(@NonNull MethodMatchContext matchContext, String type) {
        return matchContext.getVisitorContext().getClassElement(type).orElseThrow(() -> new IllegalStateException("Unable to apply interceptor of type: " + type + ". The interceptor was not found on the classpath. Check your annotation processor configuration and try again."));
    }

    /**
     * Internally used for dynamically defining a class element.
     */
    class DynamicClassElement implements ClassElement {
        private final Class<? extends DataInterceptor> type;

        DynamicClassElement(Class<? extends DataInterceptor> type) {
            this.type = type;
        }

        @Override
        public boolean isAssignable(String type) {
            return false;
        }

        @Override
        public ClassElement toArray() {
            return new DynamicClassElement((Class<? extends DataInterceptor>) Array.newInstance(type, 0).getClass());
        }

        @Override
        public ClassElement fromArray() {
            return new DynamicClassElement((Class<? extends DataInterceptor>) type.getComponentType());
        }

        @NonNull
        @Override
        public String getName() {
            return type.getName();
        }

        @Override
        public boolean isProtected() {
            return Modifier.isProtected(type.getModifiers());
        }

        @Override
        public boolean isPublic() {
            return Modifier.isPublic(type.getModifiers());
        }

        @NonNull
        @Override
        public Object getNativeType() {
            return type;
        }
    }
}
