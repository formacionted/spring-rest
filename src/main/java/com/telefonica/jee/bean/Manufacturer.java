package com.telefonica.jee.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // It ignores those uknown properties in order to avoid problems

public class Manufacturer {
	private Long id;
	private String name;
	private String department;
	
	
	public Manufacturer() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "Manufacturer [id=" + id + ", name=" + name + ", department=" + department + "]";
	}
	
}
