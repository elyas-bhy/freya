package com.dev.freya.model;

public class Response {

	private String key;
	
	public Response() {
		
	}
	
	public Response(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("[key: " + getKey());
		sb.append("]");
		return sb.toString();
	}
}
