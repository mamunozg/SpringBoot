package com.marco.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;



@JsonSubTypes({@Type(value= PlaceType.class)})
public class Trend {

	@JsonProperty("name")
	private String name;
	@JsonProperty("placeType")
	private PlaceType placeType;
	@JsonProperty("url")
	private String url;
	@JsonProperty("countryCode")
	private String countryCode;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PlaceType getPlaceType() {
		return placeType;
	}
	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Trend(String name, String url, String countryCode, PlaceType placeType) {
		super();
		this.name = name;
		this.placeType = placeType;
		this.url = url;
		this.countryCode = countryCode;
	}
	public Trend() {
		super();
	}
	
	
}
