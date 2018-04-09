package com.marco.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Employee", description="Descriptin for employees")
public class Employee implements Serializable {

	private static final long serialVersionUID = -2504402120974817906L;

	@ApiModelProperty(notes="Employee number")
	private String empNo;
	@ApiModelProperty(notes="Employee name")
	private String empName;
	@ApiModelProperty(notes="Employee position")
	private String position;

	public Employee() {
		super();
	}

	public Employee(String empNo, String empName, String position) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.position = position;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
