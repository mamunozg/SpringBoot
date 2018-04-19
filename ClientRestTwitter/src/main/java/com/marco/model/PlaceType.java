package com.marco.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceType {

	@JsonProperty("code")
	private String code;
	@JsonProperty("name")
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "PlaceType [code=" + code + ", name=" + name + "]";
	}
	public PlaceType() {
		super();
	}
	
	
}
