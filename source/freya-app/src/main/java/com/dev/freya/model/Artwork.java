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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Artwork {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Unowned
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Artist artist;
	
	@Basic
	private String title;
	
	@Enumerated(EnumType.STRING)
	private ArtSupport support;
	
	@Enumerated(EnumType.STRING)
	private ArtTechnique technique;
	
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	@Lob
	private String summary;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> comments;
	
	//@OneToMany
	private List<String> tags;
	
	// Must use @Embedded instead of @ElementCollection as a workaround of issue 318
	// Eclipse IDE might signal an error here, but this works just fine.
	// See: https://code.google.com/p/datanucleus-appengine/issues/detail?id=318
	@Embedded
	@OneToMany(fetch = FetchType.EAGER)
	private List<Photo> photos;
	
	@Embedded
	@OneToOne(fetch = FetchType.EAGER)
	private Dimension dimension;
	
	public Artwork() {
		comments = new ArrayList<>();
		tags = new ArrayList<>();
		photos = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
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

	public List<Photo> getPhotos() {
		return photos;
	}

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
}
