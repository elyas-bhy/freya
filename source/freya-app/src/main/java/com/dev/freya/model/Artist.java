package com.dev.freya.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Artist {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String key;
	
	@Basic(fetch = FetchType.EAGER)
	private String name;
	
	@Unowned
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Artwork> artworks;
	
	public Artist() {
		artworks = new HashSet<>();
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

	public Set<Artwork> getArtworks() {
		return artworks;
	}

	public void addArtwork(Artwork artwork) {
		artworks.add(artwork);
	}
	
	
}
