package com.dev.freya.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class Artist {
	
	@Id
	@Basic(fetch = FetchType.EAGER)
	private String name;
	
	public Artist() {
		
	}
	
	public Artist(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
