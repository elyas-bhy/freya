package com.dev.freya.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;

public class FreyaDao {

	private EntityManager mEntityManager;

	public FreyaDao() {
		mEntityManager = EMFService.createEntityManager();
	}
	
	public void persist(Object o) {
		mEntityManager.persist(o);
	}
	
	@SuppressWarnings("unchecked")
	public List<Artist> listArtists() {
		Query query = mEntityManager.createQuery("select a from Artist a");
		List<Artist> artists = query.getResultList();
		return artists;
	}

	@SuppressWarnings("unchecked")
	public Set<Artwork> listArtworksByArtist(String artist) {
		Query query = mEntityManager.createQuery("select a.artworks from Artist a where a.name = :artist");
		query.setParameter("artist", artist);
		List<Set<Artwork>> result = query.getResultList();
		if (!result.isEmpty()) {
			Set<Artwork> artworks = result.get(0);
			for (Artwork artwork : artworks) {
				artwork.getArtist(); // Force eager-load
			}
			return artworks;
		}
		return new HashSet<>();
	}
	
	@SuppressWarnings("unchecked")
	public List<Artwork> listArtworks() {
		Query query = mEntityManager.createQuery("select a from Artwork a");
		List<Artwork> artworks = query.getResultList();
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
