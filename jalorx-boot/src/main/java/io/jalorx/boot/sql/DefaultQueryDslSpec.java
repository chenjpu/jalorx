package io.jalorx.boot.sql;

import io.micronaut.data.repository.jpa.criteria.PredicateSpecification;
import jakarta.inject.Singleton;

@Singleton
public class DefaultQueryDslSpec implements QueryDslSpec {

	@Override
	public <T> PredicateSpecification<T> from(QueryDsl queryDsl) {
		
		return (root, criteriaBuilder) -> {
			queryDsl.getCommands().forEach(cs->{
				cs.and(criteriaBuilder,root);
			});
			return null;
		};
	}
}
