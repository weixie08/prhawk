package com.servicenow.prhawk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepoPullDetail {
	@JsonProperty("title")
	public String title;
	@JsonProperty("html_url")
	public String url;
}