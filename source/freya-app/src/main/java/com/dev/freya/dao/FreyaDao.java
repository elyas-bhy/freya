package com.dev.freya.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
	public List<Artwork> listArtworksByArtist(String artistId) {
		Query query = mEntityManager.createQuery(
				"select a from Artwork a join a.artist t where t.id = :artistId");
		query.setParameter("artistId", artistId);
		List<Artwork> artworks = query.getResultList();
		return artworks;
	}
	
	@SuppressWarnings("unchecked")
	public List<Artwork> listArtworks() {
		Query query = mEntityManager.createQuery("select a from Artwork a");
		List<Artwork> artworks = query.getResultList();
		return artworks;
	}
	
	public void flush() {
		mEntityManager.flush();
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
