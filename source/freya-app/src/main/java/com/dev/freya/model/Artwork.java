package com.dev.freya.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Artwork {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ArtSupport support;
	
	@Enumerated(EnumType.STRING)
	private ArtTechnique technique;
	
	@Unowned
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Artist artist;
	
	@Basic
	private String title;
	
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	@Lob
	private String summary;
	
	@ElementCollection(fetch = FetchType.LAZY)
	private List<String> comments;
	
	//@OneToMany
	private List<String> tags;

	// Photos
	
	@Embedded
	private Dimension dimension;
	
	public Artwork() {
		comments = new ArrayList<>();
		tags = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public ArtSupport getSupport() {
		return support;
	}

	public void setSupport(ArtSupport support) {
		this.support = support;
	}

	public ArtTechnique getTechnique() {
		return technique;
	}

	public void setTechnique(ArtTechnique technique) {
		this.technique = technique;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getComments() {
		return comments;
	}

	public void addComment(String comment) {
		comments.add(comment);
	}

	public List<String> getTags() {
		return tags;
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
}
