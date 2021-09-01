package io.jalorx.demo.inner;

import io.jalorx.demo.model.ExportVO;
import io.jalorx.export.excel.BaseExport;
import jakarta.inject.Singleton;
//import io.micronaut.http.

@Singleton
public class ExportFile extends BaseExport<ExportVO> {

	//此处可重写export方法
}
