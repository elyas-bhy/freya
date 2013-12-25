package com.dev.freya.model;

import java.util.Date;
import java.util.List;

public interface IArtwork {
	
	public Long getId();
	public ArtSupport getSupport();
	public void setSupport(ArtSupport support);
	public ArtTechnique getTechnique();
	public void setTechnique(ArtTechnique technique);
	public Artist getArtist();
	public void setArtist(Artist artist);
	public String getTitle();
	public void setTitle(String title);
	public Date getDate();
	public void setDate(Date date);
	public String getSummary();
	public void setSummary(String summary);
	public List<String> getComments();
	public void addComment(String comment);
	public List<String> getTags();
	public void addTag(String tag);
	public Dimension getDimension();	
	public void setDimension(Dimension dimension);
}
