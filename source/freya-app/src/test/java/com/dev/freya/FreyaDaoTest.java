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

package com.dev.freya;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.ArtSupport;
import com.dev.freya.model.ArtTechnique;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
import com.dev.freya.model.Photo;
import com.dev.freya.model.Reproduction;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FreyaDaoTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private ArtCollection artcollection1;
	private ArtCollection artcollection2;

	private String daliArtworkId;
	private String daliArtistId;
    
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
            .setApplyAllHighRepJobPolicy());
	
	@Before
	public void setUp() {
		helper.setUp();
		FreyaDao dao = new FreyaDao();
		Artwork artwork1 = new Artwork();
		artwork1.setArtist(new Artist("Dali"));
		artwork1.setTitle("Title 1");
		artwork1.setSupport(ArtSupport.PAINTING_CARDBOARD);
		artwork1.setTechnique(ArtTechnique.PAINTING_ACRYLIC);
		artwork1.setDate(sdf.format(new Date()));
		artwork1.setSummary("Summary 1");
		artwork1.addPhoto(new Photo("Desc 1", "URL 1"));
		artwork1.addPhoto(new Photo("Desc 2", "URL 2"));
		artwork1.setDimension(new Dimension(4, 5, 6));

		Artwork artwork2 = new Artwork();
		artwork2.setArtist(new Artist("Pablo Picasso"));
		artwork2.setTitle("Title 2");
		artwork2.setSupport(ArtSupport.PAINTING_LINEN_CANVAS);
		artwork2.setTechnique(ArtTechnique.PAINTING_GOUACHE);
		artwork2.setDate("1999-12-31");
		artwork2.setSummary("Summary 2");
		artwork2.addPhoto(new Photo("Desc 3", "URL 3"));
		artwork2.addPhoto(new Photo("Desc 4", "URL 4"));
		artwork2.setDimension(new Dimension(12, 10, 15));
		
		Artwork artwork3 = new Artwork();
		artwork3.setArtist(new Artist("Dali"));
		artwork3.setTitle("Title 3");
		artwork3.setSupport(ArtSupport.PAINTING_PAPER);
		artwork3.setTechnique(ArtTechnique.PAINTING_WATERCOLOR);
		artwork3.setDate("2010-11-29");
		artwork3.setSummary("Summary 3");
		artwork3.setDimension(new Dimension(20, 5, 35));
		artwork3.addTag("abstract");

		artcollection1 = new ArtCollection();
		artcollection1.addArtwork(artwork1);
		artcollection1.addArtwork(artwork2);
		
		artcollection2 = new ArtCollection();
		artcollection2.addArtwork(artwork1);
		artcollection2.addArtwork(artwork3);
		
		Reproduction repro = new Reproduction();
		repro.setPrice(1000.0);
		repro.setStock(4);
		repro.setSupport(ArtSupport.PAINTING_LINEN_CANVAS);
		artwork3.addReproduction(repro);
	
		dao.persistTransactional(artcollection1);
		dao.persistTransactional(artwork3);
		dao.persistTransactional(artcollection2);
		dao.close();
		
		daliArtworkId = artwork1.getId();
		daliArtistId = artwork1.getArtist().getId();
	}

	@After
	public void teardown() {
		helper.tearDown();
	}

	@Test
	public void testListArtworks() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworks(null, null, null, null);
		assertEquals(artworks.size(), 3);
		dao.close();
	}
	
	@Test
	public void testListArtworksByYear() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworks(null, null, "2010", null);
		assertEquals(artworks.size(), 1);
		dao.close();
	}

	@Test
	public void testGetArtworksByArtist() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtist(daliArtistId, null, null, null);
		assertEquals(artworks.size(), 2);
		dao.close();
	}

	@Test
	public void testGetArtworksByArtistAndSupport() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtist(daliArtistId, "PAINTING_PAPER", null, null);
		assertEquals(artworks.size(), 1);
		assertEquals(artworks.get(0).getSupport(), ArtSupport.PAINTING_PAPER);
		dao.close();
	}
	
	@Test
	public void testGetArtworksByArtistAndSupportAndTechnique() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtist(daliArtistId, "PAINTING_PAPER", "PAINTING_WATERCOLOR", null);
		assertEquals(artworks.size(), 1);
		assertEquals(artworks.get(0).getSupport(), ArtSupport.PAINTING_PAPER);
		dao.close();
	}
	
	@Test
	public void testGetArtworksByArtistAndYear() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtist(daliArtistId, null, null, "2010");
		assertEquals(artworks.size(), 1);
		assertEquals(artworks.get(0).getTitle(), "Title 3");
		dao.close();
	}

	@Test
	public void testGetPhotosByArtist() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtist(daliArtistId, null, null, null);
		assertEquals(photos.size(), 2);
		dao.close();
	}

	@Test
	public void testGetPhotosByArtistAndSupport() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtist(daliArtistId, "PAINTING_CARDBOARD", null, null);
		assertEquals(photos.size(), 2);
		assertEquals(photos.get(0).getDesc(), "Desc 1");
		assertEquals(photos.get(0).getUrl(), "URL 1");
		assertEquals(photos.get(1).getDesc(), "Desc 2");
		assertEquals(photos.get(1).getUrl(), "URL 2");
		dao.close();
	}
	
	@Test
	public void testGetPhotosByArtistAndYear() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtist(daliArtistId, null, null, "2010");
		assertEquals(photos.size(), 0);
		dao.close();
	}

	@Test
	public void testUniqueArtists() {
		FreyaDao dao = new FreyaDao();
		dao.beginTransaction();
		dao.persist(new Artist("Dali"));
		dao.persist(new Artist("Pablo Picasso"));
		dao.persist(new Artist("Dali"));
		dao.flush();
		dao.commitTransaction();
		
		List<Artist> artists = dao.listArtists();
		assertEquals(artists.size(), 2);
		dao.close();
	}

	@Test
	public void testGetArtwork() {
		FreyaDao dao = new FreyaDao();
		Artwork artwork = dao.getArtwork(daliArtworkId);
		assertEquals(artwork.getId(), daliArtworkId);
		assertEquals(artwork.getTitle(), "Title 1");
		assertEquals(artwork.getPhotos().size(), 2);
		dao.close();
	}

	@Test
	public void testGetPhotosByArtwork() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtwork(daliArtworkId);
		assertEquals(photos.size(), 2);
		dao.close();
	}

	@Test
	public void testGetArtworkPhotos() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos(null, null, null);
		assertEquals(photos.size(), 4);
		dao.close();
	}
	
	@Test
	public void testGetArtworkPhotosBySupport() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos("PAINTING_LINEN_CANVAS", null, null);
		assertEquals(photos.size(), 2);
		assertEquals(photos.get(0).getDesc(), "Desc 3");
		assertEquals(photos.get(0).getUrl(), "URL 3");
		assertEquals(photos.get(1).getDesc(), "Desc 4");
		assertEquals(photos.get(1).getUrl(), "URL 4");
		dao.close();
	}
	
	@Test
	public void testGetArtworkPhotosByYear() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos(null, null, "1999");
		assertEquals(photos.size(), 2);
		assertEquals(photos.get(0).getDesc(), "Desc 3");
		assertEquals(photos.get(0).getUrl(), "URL 3");
		assertEquals(photos.get(1).getDesc(), "Desc 4");
		assertEquals(photos.get(1).getUrl(), "URL 4");
		dao.close();
	}

	@Test
	public void testConsistentIDs() {
		assertEquals(artcollection1.getArtworks().size(), 2);
		assertEquals(artcollection2.getArtworks().size(), 2);
		
		assertEquals(artcollection1.getArtworks().get(0).getId(), 
				artcollection2.getArtworks().get(0).getId());
	}

	@Test
	public void testListArtCollections() {
		FreyaDao dao = new FreyaDao();
		List<ArtCollection> artcollections = dao.listArtCollections();
		assertEquals(artcollections.size(), 2);
		dao.close();
	}
	
	@Test
	public void testGetArtCollection() {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artcollection1.getId());
		assertEquals(artcollection.getArtworks().size(), 2);
		assertEquals(artcollection.getArtworks().get(0).getId(), daliArtworkId);
		dao.close();
	}
	
	@Test
	public void testGetArtworksByArtCollection() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtCollection(artcollection1.getId(), null);
		assertEquals(artworks.size(), 2);
		dao.close();
	}

}
