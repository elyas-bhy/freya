package com.dev.freya.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Photo;

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
	public List<Photo> listPhotosByArtist(String artistId) {
		Query query = mEntityManager.createQuery(
				"select a.photos from Artwork a join a.artist t where t.id = :artistId");
		query.setParameter("artistId", artistId);
		List<Photo> photos = query.getResultList();
		return photos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Artwork> listArtworks(String support, String technique) {
		String keyword = " where ";
		StringBuffer sb = new StringBuffer("select a from Artwork a");
		if (support != null) {
			sb.append(keyword);
			sb.append("a.support = ");
			sb.append(support);
			keyword = " and ";
		}
		if (technique != null) {
			sb.append(keyword);
			sb.append("a.technique = ");
			sb.append(technique);
		}
		Query query = mEntityManager.createQuery(sb.toString());
		List<Artwork> artworks = query.getResultList();
		return artworks;
	}

	@SuppressWarnings("unchecked")
	public Artwork getArtwork(Long artworkId) {
		// The method: mEntityManager.find(Artwork.class, artworkId) fails to
		// properly load Artwork.photos field, so use standard query instead
		Query query = mEntityManager.createQuery("select a from Artwork a where a.id = :artworkId");
		query.setParameter("artworkId", artworkId);
		List<Artwork> artworks = query.getResultList();
		if (artworks.size() > 0) return artworks.get(0);
		return null;
	}
	
	public List<Photo> getPhotosByArtwork(Long artworkId) {
		Artwork artwork = getArtwork(artworkId);
		if (artwork != null) {
			return artwork.getPhotos();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Photo> getArtworkPhotos() {
		Query query = mEntityManager.createQuery("select a.photos from Artwork a");
		List<Photo> photos = query.getResultList();
		return photos;
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
