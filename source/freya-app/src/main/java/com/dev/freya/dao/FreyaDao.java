package com.dev.freya.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dev.freya.model.IArtwork;

public class FreyaDao {

	private EntityManager mEntityManager;

	public FreyaDao() {
		mEntityManager = EMFService.createEntityManager();
	}
	
	public void persist(Object o) {
		mEntityManager.persist(o);
	}
	
	@SuppressWarnings("unchecked")
	public List<IArtwork> listArtworks() {
		Query query = mEntityManager.createQuery("select a from Artwork a");
		List<IArtwork> artworks = query.getResultList();
		return artworks;
	}
	
	@SuppressWarnings("unchecked")
	public List<IArtwork> getArtworksByArtist(String artist) {
		Query query = mEntityManager.createQuery("select a from Artwork a where a.artist = :artist");
		query.setParameter("artist", artist);
		List<IArtwork> artworks = query.getResultList();
		return artworks;
	}

	public void close() {
		mEntityManager.close();
	}

	public void beginTransaction() {
		mEntityManager.getTransaction().begin();
	}

	public void commitTransaction() {
		mEntityManager.getTransaction().commit();
	}

}
