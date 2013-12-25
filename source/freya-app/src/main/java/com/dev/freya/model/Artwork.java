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
public class Artwork implements IArtwork {
	
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
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public ArtSupport getSupport() {
		return support;
	}

	@Override
	public void setSupport(ArtSupport support) {
		this.support = support;
	}

	@Override
	public ArtTechnique getTechnique() {
		return technique;
	}

	@Override
	public void setTechnique(ArtTechnique technique) {
		this.technique = technique;
	}

	@Override
	public Artist getArtist() {
		return artist;
	}

	@Override
	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getSummary() {
		return summary;
	}

	@Override
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public List<String> getComments() {
		return comments;
	}

	@Override
	public void addComment(String comment) {
		comments.add(comment);
	}

	@Override
	public List<String> getTags() {
		return tags;
	}

	@Override
	public void addTag(String tag) {
		tags.add(tag);
	}

	@Override
	public Dimension getDimension() {
		return dimension;
	}

	@Override
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
}
