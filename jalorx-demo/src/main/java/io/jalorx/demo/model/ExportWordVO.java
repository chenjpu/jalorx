package io.jalorx.demo.model;

import java.util.List;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ExportWordVO {

	private String           author;
	private String           sex;
	private String           workor;
	private List<ExportList> list;

	public String getAuthor() {
		return author;
	}

	public String getSex() {
		return sex;
	}

	public String getWorkor() {
		return workor;
	}

	public List<ExportList> getList() {
		return list;
	}

	public void setList(List<ExportList> list) {
		this.list = list;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setWorkor(String workor) {
		this.workor = workor;
	}

}
