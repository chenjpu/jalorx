/**
 * 
 */
package io.jalorx.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import io.jalorx.boot.datasource.annotation.DS;
import io.jalorx.boot.service.BaseService;
import io.jalorx.demo.model.Demo;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.SystemFile;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * @author ftl
 *
 */
@Transactional
@DS
public interface DemoService extends BaseService<Demo> {

	@ReadOnly
	List<Serializable> getMultUsers(String id, String id2);

	@ReadOnly
	List<Serializable> getMultUsers();

	@ReadOnly
	Demo get(Long id);

	@DS
	@ReadOnly
	Demo getMysql(Long id);

	@DS
	@Transactional
	void tranz();

	void importExcel(CompletedFileUpload file) throws FileNotFoundException, IOException;

	SystemFile export();

	SystemFile exportWord();

	Iterable<Demo> getGradAge(int age);
}
