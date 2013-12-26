package com.dev.freya.model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;

@Embeddable
public class Photo {
	
	@Basic(fetch = FetchType.EAGER)
	private String desc;
	
	@Basic(fetch = FetchType.EAGER)
	private String url;

	public Photo() {
		
	}
	
	public Photo(String desc, String url) {
		this.desc = desc;
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
