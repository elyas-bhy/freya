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
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Photo;
import com.dev.freya.model.Reproduction;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheService.IdentifiableValue;
import com.google.appengine.api.memcache.MemcacheService.SetPolicy;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class FreyaDao {

	/**
	 * Cache duration for memcache (in seconds).
	 */
	private static final int CACHE_PERIOD = 60;

	/**
	 * Memcache service object for Memcache access
	 */
	private final MemcacheService mCache = MemcacheServiceFactory.getMemcacheService();

	/**
	 * App logger
	 */
	private static final Logger LOGGER = Logger.getLogger(FreyaDao.class.getName());

	/**
	 * The entity manager used by this instance
	 */
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
		Artwork artwork = (Artwork) mCache.get(artworkId);
		if (artwork != null) {
			return artwork;
		}
		// The method: mEntityManager.find(Artwork.class, artworkId) fails to
		// properly load Artwork.photos field, so use standard query instead
		Query query = mEntityManager
				.createQuery("select a from Artwork a where a.id = :artworkId");
		query.setParameter("artworkId", artworkId);
		List<Artwork> artworks = query.getResultList();
		if (artworks.size() > 0) {
			artwork = artworks.get(0);
			mCache.put(artworkId, artwork, Expiration.byDeltaSeconds(CACHE_PERIOD), 
					SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
		}
		return artwork;
	}

	/**
	 * Returns a list of all stored artworks matching the specified optional filters
	 * @param support optional filter: the type of support used by the artworks
	 * @param technique optional filter: the technique used by the artworks
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Artwork> listArtworks(String support, String technique, Integer count) {
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
		if (count != null) {
			List<Artwork> result = new ArrayList<Artwork>();
			// Size function and subqueries are not supported in the datastore
			// Therefore we are forced to treat the query as follows
			for (Artwork art : artworks) {
				if (art.getReproductions() != null) {
					if (art.getReproductions().size() <= count.intValue())
						result.add(art);
				} else {
					result.add(art);
				}
			}
			return result;
		}
		return artworks;
	}

	/**
	 * Returns a list of artworks made by the specified artist
	 * @param artistId the artist's ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Artwork> getArtworksByArtist(String artistId) {
		Query query = mEntityManager
				.createQuery("select a from Artwork a join a.artist t where t.id = :artistId");
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
	public List<Artwork> getArtworksByArtCollection(Long artCollectionId, Integer count) {
		Query query = mEntityManager.createQuery(
				"select c.artworks from ArtCollection c where c.id = :artCollectionId");
		query.setParameter("artCollectionId", artCollectionId);
		List<List<Artwork>> result = query.getResultList();
		List<Artwork> artworks = flatten(result, Artwork.class);
		List<Artwork> finalResult = new ArrayList<Artwork>();
		if (count != null) {
			for (Artwork artwork : artworks) {
				if (artwork.getReproductions() != null) {
					if (artwork.getReproductions().size() <= count.intValue()) {
						finalResult.add(artwork);
					}
				} else {
					finalResult.add(artwork);
				}
			}
			return finalResult;
		}
		return artworks;
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
		// FIXME should not return empty lists
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
		ArtCollection artCollection = (ArtCollection) mCache.get(artCollectionId);
		if (artCollection != null) {
			return artCollection;
		}
		artCollection = mEntityManager.find(ArtCollection.class, artCollectionId);
		mCache.put(artCollectionId, artCollection, Expiration.byDeltaSeconds(CACHE_PERIOD), 
				SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
		return artCollection;
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

	/**********************************
	 * Reproduction Retrieval Methods *
	 **********************************/
	
	public Reproduction getReproduction(String reproductionId) {
		return mEntityManager.find(Reproduction.class, reproductionId);
	}

	@SuppressWarnings("unchecked")
	public List<Reproduction> listReproductions() {
		Query query = mEntityManager.createQuery("select r from Reproduction r");
		List<Reproduction> reproductions = query.getResultList();
		return reproductions;
	}

	public List<Reproduction> getReproductionsByArtwork(String artworkId) {
		Artwork artwork = getArtwork(artworkId);
		return artwork.getReproductions();
	}

	public List<Reproduction> getReproductionsByArtCollection(Long artCollectionId) {
		List<Artwork> artworks = getArtworksByArtCollection(artCollectionId, null);
		List<Reproduction> results = new ArrayList<Reproduction>();
		for (Artwork artwork : artworks) {
			results.addAll(artwork.getReproductions());
		}
		return results;
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
	 * Updates the value of the cached entity
	 * @param key the key of the entity
	 * @param value the new value of the entity
	 */
	public void refresh(Object key, Object value) {
		IdentifiableValue cachedValue = mCache.getIdentifiable(key);
		if (cachedValue != null) {
			boolean success = mCache.putIfUntouched(key, cachedValue, value);
			if (!success) {
				LOGGER.info("Race condition on cached entity " + key);
				LOGGER.info("Clearing entry from memcache");
				mCache.delete(key);
			}
		}
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
