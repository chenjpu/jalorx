package io.jalorx.demo.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.demo.dao.DemoDao;
import io.jalorx.demo.dao.DemoRepository2;
import io.jalorx.demo.inner.ExportFile;
import io.jalorx.demo.inner.ImportFile;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.model.ExportList;
import io.jalorx.demo.model.ExportVO;
import io.jalorx.demo.model.ExportWordVO;
import io.jalorx.demo.service.DemoService;
import io.jalorx.export.DataSource;
import io.jalorx.export.excel.Definition;
import io.jalorx.export.word.BaseExport;
import io.jalorx.export.word.WDefinition;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.SystemFile;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author ftl
 *
 */
@Named("demoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements DemoService {

	@Inject
	DemoDao dao;
	
	@Inject
	DemoRepository2 dynamicDao;

	@Inject
	ImportFile importFile;

	@Inject
	ExportFile exportFile;

	@Inject
	BaseExport<ExportWordVO> baseExport;

	private static Logger LOG = LoggerFactory.getLogger(DemoService.class);

	@Override
	protected DemoDao getDao() {
		return dao;
	}

	@Override
	public List<Serializable> getMultUsers(String id, String id2) {
		return Arrays.asList("3", "4", "5", "6");
	}

	@Override
	public List<Serializable> getMultUsers() {
		return Arrays.asList("1", "2", "3", "4");
	}

	////
	@Override
	public Demo get(Long id) {
		//return dynamicDao.findOne(DemoSqlSupport.queryPerson(id)).get();
		return super.get(id);
	}

	@Override
	public Demo getMysql(Long id) {
		//return dynamicDao.findOne(DemoSqlSupport.queryPerson(id)).get();
		return super.get(id);
	}
	
	@Override
	public Iterable<Demo> getGradAge(int age) {
		return dynamicDao.findByAgeGreaterThan(age);
		//return dynamicDao.findAll(DemoSqlSupport.queryAll(age),Pageable.from(1, 3));
	}

	@Override
	public void tranz() {
		// Demo test = dao.getById(11L);

		Demo test = new Demo();
		test.setName("ssssss");
		test.setAge(10L);
		test.createInit();
		dao.save(test);

		if (test.getId() % 2 == 0) {
			throw new NullPointerException();
		}
	}

	@Override
	public void importExcel(CompletedFileUpload file) throws FileNotFoundException, IOException {
		Map<String, List<List<String>>> res = importFile.importFile(file);
		// ???????????????????????????
		for (String key : res.keySet()) {
			LOG.info("sheetName:" + key);
			List<List<String>> sheetData = res.get(key);
			LOG.info("sheetName:" + key + "??????" + sheetData.size() + "?????????");
			for (int i = 0; i < sheetData.size(); i++) {
				LOG.info("???:" + (i + 1) + "??????????????????");
				for (int j = 0; j < sheetData.get(i)
						.size(); j++) {
					LOG.info(sheetData.get(i)
							.get(j));
				}
			}
		}
	}

	@Override
	public SystemFile export() {

		Definition.Builder<ExportVO> builder = new Definition.Builder<>();

		// ?????????
		for (int i = 1; i <= 6; i++) {
			builder.addColumn("filed" + i, "??????" + i);
		}

		builder.fileName("exportTest.xlsx");
		builder.tableTitle("exportTest");

		List<ExportVO> data = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			ExportVO vo = new ExportVO();
			vo.setFiled1("demo-" + 1);
			vo.setFiled2("demo-" + 2);
			vo.setFiled3("demo-" + 3);
			vo.setFiled4("demo-" + 4);
			vo.setFiled5("demo-" + 5);
			vo.setFiled6("demo-" + 6);
			data.add(vo);
		}

		builder.dataSource(DataSource.from(data));
		return exportFile.export(builder.build());
	}

	@Override
	public SystemFile exportWord() {
		String fileName = "demo";

		WDefinition.Builder<ExportWordVO> builder = new WDefinition.Builder<>();
		String                            author  = "??????";
		String                            sex     = "???";
		String                            workor  = "?????????";

		List<ExportList> els = new ArrayList<ExportList>();
		for (int i = 0; i < 8; i++) {
			ExportList el = new ExportList();
			el.setI((i + 1) + "");
			el.setLname("????????????");
			el.setName("??????");
			el.setWorks("????????????");
			el.setYear("??????");
			els.add(el);
		}

		ExportWordVO ewo = new ExportWordVO();
		ewo.setAuthor(author);
		ewo.setSex(sex);
		ewo.setWorkor(workor);
		ewo.setList(els);

		builder.data(ewo);
		builder.fileName(fileName);

		/**
		 * <pre>
		 * ????????????????????????: 
		 * 1.?????????doc????????????????????????????????????????????????????????????,eg:${name} 
		 * 2.???doc???????????????xml??????????????????????????????????????????????????????????????????????????????????????????????????????????????? 
		 * 3.?????????????????????????????????????????????????????????list??????????????????????????????????????????list??????????????????????????????????????????demo.ftl,??????????????????listMap????????????
		 * 4.???xml???????????????ftl???????????????resources??????config???????????? 
		 * 5.??????????????????????????????????????????
		 * </pre>
		 */
		return baseExport.export(builder.build());
	}
}
