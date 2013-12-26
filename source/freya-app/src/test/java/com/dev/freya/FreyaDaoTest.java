package com.dev.freya;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtSupport;
import com.dev.freya.model.ArtTechnique;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
import com.dev.freya.model.Photo;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FreyaDaoTest {

	private Long daliArtworkId;
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
		artwork1.setDate(new Date());
		artwork1.setSummary("Summary 1");
		artwork1.addPhoto(new Photo("Desc 1", "URL 1"));
		artwork1.addPhoto(new Photo("Desc 2", "URL 2"));
		artwork1.setDimension(new Dimension(4, 5, 6));

		Artwork artwork2 = new Artwork();
		artwork2.setArtist(new Artist("Pablo Picasso"));
		artwork2.setTitle("Title 2");
		artwork2.setSupport(ArtSupport.PAINTING_LINEN_CANVAS);
		artwork2.setTechnique(ArtTechnique.PAINTING_GOUACHE);
		artwork2.setDate(new Date());
		artwork2.setSummary("Summary 2");
		artwork2.setDimension(new Dimension(12, 10, 15));
		
		Artwork artwork3 = new Artwork();
		artwork3.setArtist(new Artist("Dali"));
		artwork3.setTitle("Title 3");
		artwork3.setSupport(ArtSupport.PAINTING_PAPER);
		artwork3.setTechnique(ArtTechnique.PAINTING_WATERCOLOR);
		artwork3.setDate(new Date());
		artwork3.setSummary("Summary 3");
		artwork3.setDimension(new Dimension(20, 5, 35));

		dao.beginTransaction();
		dao.persist(artwork1);
		dao.persist(artwork2);
		dao.persist(artwork3);
		dao.flush();
		dao.commitTransaction();
		dao.close();
		
		daliArtworkId = artwork1.getId();
		daliArtistId = artwork1.getArtist().getId();
	}

	@After
	public void teardown() {
		helper.tearDown();
	}
	
	@Test
	public void testGetArtworksByArtist() {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworksByArtist(daliArtistId);
		assertEquals(artworks.size(), 2);
		dao.close();
	}
	
	@Test
	public void testGetPhotosByArtist() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.listPhotosByArtist(daliArtistId);
		assertEquals(photos.size(), 2);
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
	public void testGetArtworkPhotos() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos(daliArtworkId);
		assertEquals(photos.size(), 2);
		dao.close();
	}

}
