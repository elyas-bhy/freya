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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

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
	
	/**
	 * Retrieves an artist with the specified ID
	 * @param artistId the artist's ID
	 * @return the matching artist if found, or null
	 */
	public Artist getArtist(String artistId) {
		Artist artist = (Artist) mCache.get(artistId);
		if (artist != null) {
			return artist;
		}
		artist = mEntityManager.find(Artist.class, artistId);
		if (artist != null) {
			mCache.put(artistId, artist, Expiration.byDeltaSeconds(CACHE_PERIOD), 
					SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
		}
		return artist;
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
	 * @param year optional filter: the year of fabrication of the artworks
	 * @param tag optional filter: the tag used by the artworks
	 * @param count optional filter: the maximal number of reproductions that an artwork can have
	 * @return
	 */
	public List<Artwork> listArtworks(String support, String technique, 
			String year, String tag, Integer count) {
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaQuery<Artwork> q = cb.createQuery(Artwork.class);
		Root<Artwork> a = q.from(Artwork.class);
		
		List<Predicate> predicates = new ArrayList<>();
		if (support != null)   predicates.add(cb.equal(a.get("support"), support));
		if (technique != null) predicates.add(cb.equal(a.get("technique"), technique));
		if (year != null)      predicates.add(cb.like(a.<String>get("date"), year + "%"));
		if (tag != null)       predicates.add(cb.isMember(tag, a.<List<String>>get("tags")));
		q.select(a).where(predicates.toArray(new Predicate[]{}));
		
		TypedQuery<Artwork> query = mEntityManager.createQuery(q);
		List<Artwork> artworks = query.getResultList();
		
		// Size function and subqueries are not supported in the datastore
		// Therefore we are forced to treat the query as follows
		if (count != null) {
			List<Artwork> result = new ArrayList<>();
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
	 * @param support optional filter: the type of support used by the artworks
	 * @param technique optional filter: the technique used by the artworks
	 * @param year optional filter: the year of fabrication of the artworks
	 * @param tag optional filter: the tag used by the artworks
	 * @return
	 */
	public List<Artwork> getArtworksByArtist(String artistId, 
			String support, String technique, String year, String tag) {
		// Since joins are only supported when all filters are 'equals' filters,
		// we need to perform further filtering on the returned query result
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaQuery<Artwork> q = cb.createQuery(Artwork.class);
		Root<Artwork> a = q.from(Artwork.class);
		List<Predicate> predicates = new ArrayList<>();
		
		// Workaround of JPA implementation that uses sub-object referencing,
		// which is unsupported on Google App Engine
		a.join("artist").alias("t");
		a.alias("t");
		predicates.add(cb.equal(a.get("id"), artistId));
		a.alias("a");
		
		if (support != null)   predicates.add(cb.equal(a.get("support"), support));
		if (technique != null) predicates.add(cb.equal(a.get("technique"), technique));
		if (tag != null)       predicates.add(cb.isMember(tag, a.<List<String>>get("tags")));
		q.select(a).where(predicates.toArray(new Predicate[]{}));
		
		TypedQuery<Artwork> query = mEntityManager.createQuery(q);
		List<Artwork> qresult = query.getResultList();
		List<Artwork> artworks = new ArrayList<>();
		for (Artwork artwork : qresult) {
			if (year != null && !artwork.getDate().startsWith(year)) continue;
			artworks.add(artwork);
		}
		return artworks;
	}

	/**
	 * Returns a list of artworks contained in the specified artcollection
	 * @param artCollectionId the artcollection's ID
	 * @param count optional filter: the maximal number of reproductions that an artwork can have
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Artwork> getArtworksByArtCollection(Long artCollectionId, Integer count) {
		Query query = mEntityManager.createQuery(
				"select c.artworks from ArtCollection c where c.id = :artCollectionId");
		query.setParameter("artCollectionId", artCollectionId);
		List<List<Artwork>> qresult = query.getResultList();
		List<Artwork> artworks = flatten(qresult, Artwork.class);
		if (count != null) {
			List<Artwork> result = new ArrayList<Artwork>();
			for (Artwork artwork : artworks) {
				if (artwork.getReproductions() != null) {
					if (artwork.getReproductions().size() <= count.intValue()) {
						result.add(artwork);
					}
				} else {
					result.add(artwork);
				}
			}
			return result;
		}
		return artworks;
	}

	/**
	 * Returns the parent artwork of the specified reproduction
	 * @param reproductionId the reproduction's ID
	 * @return
	 */
	public Artwork getArtworkByReproduction(String reproductionId) {
		Reproduction reproduction = getReproduction(reproductionId);
		if (reproduction != null)
			return getArtwork(reproduction.getArtworkId());
		return null;
	}

	/***************************
	 * Photo Retrieval Methods *
	 ***************************/

	/**
	 * Returns a list of all stored photos
	 * @param support optional filter: the type of support used by the artworks
	 * @param technique optional filter: the technique used by the artworks
	 * @param year optional filter: the year of fabrication of the artworks
	 * @param tag optional filter: the tag used by the artworks
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Photo> getArtworkPhotos(String support, String technique, String year, String tag) {
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaQuery<List<Photo>> q = cb.createQuery((Class<List<Photo>>)(Class<?>)List.class);
		Root<Artwork> a = q.from(Artwork.class);
		Selection<List<Photo>> p = a.get("photos");
		
		List<Predicate> predicates = new ArrayList<>();
		if (support != null)   predicates.add(cb.equal(a.get("support"), support));
		if (technique != null) predicates.add(cb.equal(a.get("technique"), technique));
		if (year != null)      predicates.add(cb.like(a.<String>get("date"), year + "%"));
		if (tag != null)       predicates.add(cb.isMember(tag, a.<List<String>>get("tags")));
		q.select(p).where(predicates.toArray(new Predicate[]{}));
		
		TypedQuery<List<Photo>> query = mEntityManager.createQuery(q);
		List<List<Photo>> result = query.getResultList();
		return flatten(result, Photo.class);
	}

	/**
	 * Returns a list of photos of the specified artist's artworks 
	 * @param artistId the artist's ID
	 * @param support optional filter: the type of support used by the artworks
	 * @param technique optional filter: the technique used by the artworks
	 * @param year optional filter: the year of fabrication of the artworks
	 * @param tag optional filter: the tag used by the artworks
	 * @return
	 */
	public List<Photo> getPhotosByArtist(String artistId, 
			String support, String technique, String year, String tag) {
		// Since joins are only supported when all filters are 'equals' filters,
		// we need to retrieve the artworks (instead of their photos) and perform
		// further filtering on the returned query result
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaQuery<Artwork> q = cb.createQuery(Artwork.class);
		Root<Artwork> a = q.from(Artwork.class);
		List<Predicate> predicates = new ArrayList<>();
		
		// Workaround of JPA implementation that uses sub-object referencing,
		// which is unsupported on Google App Engine
		a.join("artist").alias("t");
		a.alias("t");
		predicates.add(cb.equal(a.get("id"), artistId));
		a.alias("a");
		
		if (support != null)   predicates.add(cb.equal(a.get("support"), support));
		if (technique != null) predicates.add(cb.equal(a.get("technique"), technique));
		if (tag != null)       predicates.add(cb.isMember(tag, a.<List<String>>get("tags")));
		q.select(a).where(predicates.toArray(new Predicate[]{}));
		
		TypedQuery<Artwork> query = mEntityManager.createQuery(q);
		List<Artwork> qresult = query.getResultList();
		List<Photo> photos = new ArrayList<>();
		for (Artwork artwork : qresult) {
			if (year != null && !artwork.getDate().startsWith(year)) continue;
			photos.addAll(artwork.getPhotos());
		}
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
		if (artCollection != null) {
			mCache.put(artCollectionId, artCollection, Expiration.byDeltaSeconds(CACHE_PERIOD), 
					SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
		}
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

	/**
	 * Deletes the specified artcollection
	 * @param artCollectionId
	 */
	public void deleteArtCollection(Long artCollectionId) {
		ArtCollection artCollection = mEntityManager.find(ArtCollection.class, artCollectionId);
		if (artCollection != null) {
			beginTransaction();
			remove(artCollection);
			commitTransaction();
			mCache.clearAll();
		}
	}
	
	/**
	 * Removes the specified artwork from the specified artcollection
	 * @param artCollectionId
	 */
	public void removeArtworkFromArtCollection(Long artCollectionId, String artworkId) {
		ArtCollection artCollection = getArtCollection(artCollectionId);
		if (artCollection != null) {
			Artwork artwork = getArtwork(artworkId);
			if (artwork != null) {
				// Workaround of GAE's unowned dependencies management. For more info:
				// http://bit.do/appengine-keep-the-original-entity-when-deleting-from-unowned
				List<Artwork> dup = new ArrayList<Artwork>(artCollection.getArtworks());
				dup.remove(artwork);
				artCollection.getArtworks().clear();
				artCollection.setArtworks(dup);
				mCache.clearAll();
			}
		}
	}

	/**********************************
	 * Reproduction Retrieval Methods *
	 **********************************/
	
	/**
	 * Retrieves a reproduction with the specified ID
	 * @param reproductionId
	 * @return
	 */
	public Reproduction getReproduction(String reproductionId) {
		return mEntityManager.find(Reproduction.class, reproductionId);
	}

	/**
	 * Returns a list of all stored reproductions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reproduction> listReproductions() {
		Query query = mEntityManager.createQuery("select r from Reproduction r");
		List<Reproduction> reproductions = query.getResultList();
		return reproductions;
	}

	/**
	 * Returns the reproductions of the specified artwork
	 * @param artworkId the artwork's ID
	 * @return
	 */
	public List<Reproduction> getReproductionsByArtwork(String artworkId) {
		Artwork artwork = getArtwork(artworkId);
		return artwork.getReproductions();
	}

	/**
	 * Returns the reproductions contained in the specified artcollection
	 * @param artCollectionId the artcollection's ID
	 * @return
	 */
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
	
	public void remove(Object o) {
		mEntityManager.remove(o);
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