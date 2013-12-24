package com.dev.freya.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Artwork {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//private ArtworkType type;
	private String artist;
	private String title;
	
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	@Lob
	private String summary;
	
	//@OneToMany
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
	
	public void setId(Long id) {
		this.id = id;
	}
	
	/*public ArtworkType getType() {
		return type;
	}
	
	public void setType(ArtworkType type) {
		this.type = type;
	}*/
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
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
