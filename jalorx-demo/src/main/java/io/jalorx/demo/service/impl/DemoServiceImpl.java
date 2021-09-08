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

import io.jalorx.boot.dao.DynamicDao;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.demo.dao.DemoDao;
import io.jalorx.demo.dao.sql.DemoSqlSupport;
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
	DynamicDao dynamicDao;

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
		return dynamicDao.findOne(DemoSqlSupport.queryPerson(id)).get();
		//return super.get(id);
	}

	@Override
	public Demo getMysql(Long id) {
		return dynamicDao.findOne(DemoSqlSupport.queryPerson(id)).get();
		//return super.get(id);
	}
	
	@Override
	public Iterable<Demo> getGradAge(int age) {
		return dynamicDao.findAll(DemoSqlSupport.queryAll(age));
	}

	@Override
	public void tranz() {
		// Demo test = dao.getById(11L);

		Demo test = new Demo();
		test.setName("ssssss");
		test.setAge(10L);
		test.createInit();
		dao.insert(test);

		if (test.getId() % 2 == 0) {
			throw new NullPointerException();
		}
	}

	@Override
	public void importExcel(CompletedFileUpload file) throws FileNotFoundException, IOException {
		Map<String, List<List<String>>> res = importFile.importFile(file);
		// 遍历导入数据的结果
		for (String key : res.keySet()) {
			LOG.info("sheetName:" + key);
			List<List<String>> sheetData = res.get(key);
			LOG.info("sheetName:" + key + "包含" + sheetData.size() + "行数据");
			for (int i = 0; i < sheetData.size(); i++) {
				LOG.info("第:" + (i + 1) + "行数据分别为");
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

		// 造数据
		for (int i = 1; i <= 6; i++) {
			builder.addColumn("filed" + i, "字段" + i);
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
		String                            author  = "苏轼";
		String                            sex     = "男";
		String                            workor  = "东坡肉";

		List<ExportList> els = new ArrayList<ExportList>();
		for (int i = 0; i < 8; i++) {
			ExportList el = new ExportList();
			el.setI((i + 1) + "");
			el.setLname("东坡居士");
			el.setName("苏轼");
			el.setWorks("水调歌头");
			el.setYear("宋代");
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
		 * 调用框架导出方法: 
		 * 1.制作好doc模板，将其中需要替换的位置，用占位符标识,eg:${name} 
		 * 2.将doc文档另存为xml格式，在编辑器中打开文档，检查文档中占位符是否正确，如果有被拆开可手动调整 
		 * 3.文档中如果有表格，将表格中占位符部分用list标签包起来，并将占位符中加入list标签定义的而变量，详细可参考demo.ftl,如果没有表格listMap不传即可
		 * 4.将xml格式修改为ftl格式，放在resources下的config文件夹下 
		 * 5.配置数据，将参数传入基础方法
		 * </pre>
		 */
		return baseExport.export(builder.build());
	}
}
