package com.dev.freya;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
import com.dev.freya.model.IArtwork;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FreyaDaoTest {
    
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
            .setApplyAllHighRepJobPolicy());
	
	@Before
	public void setUp() {
		helper.setUp();
		FreyaDao dao = new FreyaDao();
		IArtwork a = new Artwork();
		a.setSummary("summary");
		a.setArtist(new Artist("Dali"));
		a.setTitle("title");
		a.setDate(new Date());
		a.setDimension(new Dimension(2, 3, 4));
		a.setSupport(ArtSupport.PAINTING_LINEN_CANVAS);
		a.setTechnique(ArtTechnique.PAINTING_OIL);
		dao.persist(a);
		dao.close();
	}

	@After
	public void teardown() {
		helper.tearDown();
	}

	@Test
	public void testDoGet() throws IOException {
		/*FreyaDao dao = new FreyaDao();
		List<IArtwork> artworks = dao.listArtworksByArtist("Dali");
		assertEquals(artworks.size(), 1);*/
	}

	@Test
	public void testUniqueArtists() throws IOException {
		FreyaDao dao = new FreyaDao();
		dao.persist(new Artist("Dali"));
		dao.persist(new Artist("Pablo Picasso"));
		dao.persist(new Artist("Dali"));
		dao.close();
		
		dao = new FreyaDao();
		List<Artist> artists = dao.listArtists();
		assertEquals(artists.size(), 2);
	}

}
