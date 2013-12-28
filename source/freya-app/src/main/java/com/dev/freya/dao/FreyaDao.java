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

package com.dev.freya.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Photo;

public class FreyaDao {

	private EntityManager mEntityManager;

	public FreyaDao() {
		mEntityManager = EMFService.createEntityManager();
	}

	/****************************
	 * Artist Retrieval Methods *
	 ****************************/
	
	/**
	 * Returns a list of all stored artists
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Artist> listArtists() {
		Query query = mEntityManager.createQuery("select a from Artist a");
		List<Artist> artists = query.getResultList();
		return artists;
	}
	
	/*****************************
	 * Artwork Retrieval Methods *
	 *****************************/

	/**
	 * Retrieves an artwork with the specified ID
	 * @param artworkId the artwork's ID
	 * @return the matching artwork if found, or null
	 */
	@SuppressWarnings("unchecked")
	public Artwork getArtwork(String artworkId) {
		// The method: mEntityManager.find(Artwork.class, artworkId) fails to
		// properly load Artwork.photos field, so use standard query instead
		Query query = mEntityManager.createQuery("select a from Artwork a where a.id = :artworkId");
		query.setParameter("artworkId", artworkId);
		List<Artwork> artworks = query.getResultList();
		if (artworks.size() > 0) return artworks.get(0);
		return null;
	}

	/**
	 * Returns a list of all stored artworks matching the specified optional filters
	 * @param support optional filter: the type of support used by the artworks
	 * @param technique optional filter: the technique used by the artworks
	 * @return
	 */
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
	
	/**
	 * Returns a list of artworks made by the specified artist
	 * @param artistId the artist's ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Artwork> getArtworksByArtist(String artistId) {
		Query query = mEntityManager.createQuery(
				"select a from Artwork a join a.artist t where t.id = :artistId");
		query.setParameter("artistId", artistId);
		List<Artwork> artworks = query.getResultList();
		return artworks;
	}

	/**
	 * Returns a list of artworks contained in the specified artcollection
	 * @param artCollectionId the artcollection's ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Artwork> getArtworksByArtCollection(Long artCollectionId) {
		Query query = mEntityManager.createQuery(
				"select c.artworks from ArtCollection c where c.id = :artCollectionId");
		query.setParameter("artCollectionId", artCollectionId);
		List<List<Artwork>> result = query.getResultList();
		return flatten(result, Artwork.class);
	}

	/***************************
	 * Photo Retrieval Methods *
	 ***************************/
	
	/**
	 * Returns a list of all stored photos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getArtworkPhotos() {
		Query query = mEntityManager.createQuery("select a.photos from Artwork a");
		List<List<Photo>> result = query.getResultList();
		return flatten(result, Photo.class);
	}
	
	/**
	 * Returns a list of photos of the specified artist's artworks 
	 * @param artistId the artist's ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getPhotosByArtist(String artistId) {
		Query query = mEntityManager.createQuery(
				"select a.photos from Artwork a join a.artist t where t.id = :artistId");
		query.setParameter("artistId", artistId);
		List<Photo> photos = query.getResultList();
		return photos;
	}
	
	/**
	 * Returns a list of photos of the specified artwork
	 * @param artworkId the artwork's ID
	 * @return
	 */
	public List<Photo> getPhotosByArtwork(String artworkId) {
		Artwork artwork = getArtwork(artworkId);
		if (artwork != null) {
			return artwork.getPhotos();
		}
		return null;
	}

	/***********************************
	 * ArtCollection Retrieval Methods *
	 ***********************************/
	
	/**
	 * Retrieves an artcollection with the specified ID
	 * @param artCollectionId the artcollection's ID
	 * @return
	 */
	public ArtCollection getArtCollection(Long artCollectionId) {
		return mEntityManager.find(ArtCollection.class, artCollectionId);
	}
	
	/**
	 * Returns a list of all stored artcollections
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ArtCollection> listArtCollections() {
		Query query = mEntityManager.createQuery("select c from ArtCollection c");
		List<ArtCollection> artCollections = query.getResultList();
		return artCollections;
	}
	
	/*************
	 * Utilities *
	 *************/

	public void persist(Object o) {
		mEntityManager.persist(o);
	}
	
	public void persistTransactional(Object o) {
		mEntityManager.getTransaction().begin();
		mEntityManager.persist(o);
		mEntityManager.flush();
		mEntityManager.getTransaction().commit();
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
	
	/**
	 * Returns a list containing all the data stored as sub-elements 
	 * of the passed double-leveled list
	 * @param collection the double-leveld list
	 * @param clazz the class type of the sub-elements
	 * @return
	 */
	private <T> List<T> flatten(List<List<T>> collection, Class<T> clazz) {
		List<T> result = new ArrayList<>();
		for (List<T> list : collection) {
			result.addAll(list);
		}
		return result;
	}

}
