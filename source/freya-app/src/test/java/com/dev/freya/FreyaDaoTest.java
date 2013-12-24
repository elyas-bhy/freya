package com.dev.freya;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
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
		Artwork a = new Artwork();
		a.setSummary("summary");
		a.setArtist("dali");
		a.setTitle("title");
		a.setDate(new Date());
		a.setDimension(new Dimension(2, 3, 4));
		dao.persist(a);
		dao.close();
	}

	@After
	public void teardown() {
		helper.tearDown();
	}

	@Test
	public void testDoGet() throws IOException {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtist("dali");
		assertEquals(artworks.size(), 1);
	}

}
