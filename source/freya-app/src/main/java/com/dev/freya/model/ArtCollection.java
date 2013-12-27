package com.dev.freya.model;

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
public class ArtCollection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Unowned
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Artwork> artworks;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> comments;
	
	private boolean isPublic;
	
	public ArtCollection() {
		artworks = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Artwork> getArtworks() {
		return artworks;
	}

	public void addArtwork(Artwork artwork) {
		artworks.add(artwork);
	}

	public List<String> getComments() {
		return comments;
	}

	public void addComment(String comment) {
		comments.add(comment);
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
