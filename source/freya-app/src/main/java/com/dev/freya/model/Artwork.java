/*
 * (C) Copyright 2013 Freya Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.freya.model;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class Artwork implements Serializable {
	
	private static final long serialVersionUID = -2251964248074083442L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
	
	@Unowned
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Artist artist;
	
	@Basic
	private String title;
	
	@Enumerated(EnumType.STRING)
	private ArtSupport support;
	
	@Enumerated(EnumType.STRING)
	private ArtTechnique technique;
	
	@Basic
	private String date;
	
	@Lob
	private String summary;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> comments;
	
	@ElementCollection(fetch = FetchType.EAGER)
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
	
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Reproduction> reproductions;
	
	public Artwork() {
		comments = new ArrayList<>();
		tags = new ArrayList<>();
		photos = new ArrayList<>();
		reproductions = new ArrayList<>();
	}
	
	public String getId() {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
	
	public void setPhotos(List<Photo> photos) {
		this.photos = photos ;
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
	
	public List<Reproduction> getReproductions() {
		return reproductions;
	}
	
	public void addReproduction(Reproduction r) {
		if(reproductions == null)
			reproductions = new ArrayList<Reproduction>();
		reproductions.add(r);
	}
	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("[ID: " + getId());
		sb.append(", artist: " + getArtist());
		sb.append(", support: " + getSupport());
		sb.append(", technique: " + getTechnique());
		sb.append(", date: " + getDate().toString());
		sb.append(", summary: " + getSummary());
		sb.append(", comments: " + getComments());
		sb.append(", tags: " + getTags());
		sb.append(", photos: " + getPhotos());
		sb.append(", dimension: " + getDimension());
		sb.append("]");
		return sb.toString();
	}
}
