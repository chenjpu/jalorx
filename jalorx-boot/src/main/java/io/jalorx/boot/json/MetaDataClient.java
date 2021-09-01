package io.jalorx.boot.json;

import java.util.List;
import java.util.Set;

import io.jalorx.boot.model.Id;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;

/**
 * 获取扩展属性的详细信息
 * 
 * @author chenb
 *
 */
public interface MetaDataClient<U extends Id, T> {

	@Post("/ext/attrs")
	List<U> getDetails(@Body Set<T> ids);
}
