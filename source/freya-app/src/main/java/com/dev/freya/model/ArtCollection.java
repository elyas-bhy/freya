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

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.google.appengine.datanucleus.annotations.Unowned;

@Entity
public class ArtCollection implements Serializable {

	private static final long serialVersionUID = 6811444157451451808L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Unowned
	@ManyToMany(fetch = FetchType.EAGER, cascade = {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Artwork> artworks;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> comments;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> tags;
	
	private boolean isPublic;

	public ArtCollection() {
		artworks = new ArrayList<>();
		comments = new ArrayList<>();
		tags = new ArrayList<>();
	}
	
	public ArtCollection(List<Artwork> artworks) {
		this.artworks = artworks;
		this.comments = new ArrayList<>();
		this.tags = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Artwork> getArtworks() {
		if (artworks == null)
			artworks = new ArrayList<>();
		return artworks;
	}
	
	public void setArtworks(List<Artwork> artworks) {
		this.artworks = artworks;
	}

	public void addArtwork(Artwork artwork) {
		if (artworks == null)
			artworks = new ArrayList<>();
		artworks.add(artwork);
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("[ID: " + getId());
		sb.append(", isPublic: " + isPublic());
		sb.append(", artworks: " + getArtworks());
		sb.append(", tags: " + getTags());
		sb.append(", comments: " + getComments());
		sb.append("]");
		return sb.toString();
	}

}
