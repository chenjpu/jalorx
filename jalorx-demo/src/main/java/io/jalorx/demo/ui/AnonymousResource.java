package io.jalorx.demo.ui;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.demo.client.I18NClient;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.service.DemoService;
import io.jalorx.demo.service.PGDemoService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.types.files.SystemFile;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/demo")
@Operation.Anonymous
@Validated
@Tag(name = "demo")
public class AnonymousResource {

	@Inject
	I18NClient client;

	@Inject
	DemoService demoService;

	@Inject
	PGDemoService pgDemoService;

	//  @Inject
	//  ExportFile exportFile;

	@Get("/i18n")
	@ApiResponse()
	public String login(HttpRequest<?> request) {
		return client.test();
	}

	@Get("/tranz")
	public String login() {
		demoService.tranz();
		return "ok";
	}

	@Get("/pg")
	public Demo pg() {
		return pgDemoService.get(11L);
	}

	@Get("/mysql")
	public Demo mysql() {
		return demoService.get(11L);
	}

	@Get("/validate")
	public Demo validate(@NotEmpty @QueryValue("id") String id,
			@NotEmpty @QueryValue("name") String name) {
		return demoService.get(11L);
	}

	@Post("/validate")
	public Demo validate(@Valid @Body Demo demo) {
		return demo;
	}

	@Post("/validates")
	public List<Demo> validates(@NotEmpty @Valid @Body List<Demo> demo) {
		return demo;
	}

	//导出excel表格demo
	@Get("/export")
	@io.swagger.v3.oas.annotations.Operation(summary = "导出excel表格demo")
	public SystemFile export() {

		return demoService.export();
	}

	//导出export Word demo
	@Get("/exportWord")
	@io.swagger.v3.oas.annotations.Operation(summary = "导出export Word demo")
	public SystemFile exportWord() {

		return demoService.exportWord();
	}

	//导入excel表格demo
	@Post(value = "/import", consumes = {"multipart/form-data"})
	@io.swagger.v3.oas.annotations.Operation(summary = "导入excel表格demo")
	public void upload(@Body CompletedFileUpload file) throws Exception {

		demoService.importExcel(file);
	}

}
