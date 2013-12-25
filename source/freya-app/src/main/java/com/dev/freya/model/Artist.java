package com.dev.freya.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Artist {
	
	@Id
	private String id;
	
	@Basic(fetch = FetchType.EAGER)
	private String name;
	
	public Artist() {
		
	}
	
	public Artist(String name) {
		this();
		this.name = name;
		this.id = KeyFactory.createKeyString("Artist", name);
		this.id = id.substring(0, id.length() - 1);  // JPA workaround
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
