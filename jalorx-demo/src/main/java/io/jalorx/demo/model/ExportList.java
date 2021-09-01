package io.jalorx.demo.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ExportList {

	private String i;
	private String year;
	private String name;
	private String lname;
	private String works;

	public String getI() {
		return i;
	}

	public String getYear() {
		return year;
	}

	public String getName() {
		return name;
	}

	public String getLname() {
		return lname;
	}

	public String getWorks() {
		return works;
	}

	public void setI(String i) {
		this.i = i;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setWorks(String works) {
		this.works = works;
	}

}
