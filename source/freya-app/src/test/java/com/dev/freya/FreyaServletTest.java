package com.dev.freya;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

public class FreyaServletTest {

	private FreyaServlet freyaServlet;

	private final LocalServiceTestHelper helper =
			new LocalServiceTestHelper(new LocalUserServiceTestConfig())
	.setEnvIsLoggedIn(true)
	.setEnvAuthDomain("localhost")
	.setEnvEmail("test@localhost");

	@Before
	public void setupGuestBookServlet() {
		helper.setUp();
		freyaServlet = new FreyaServlet();
	}

	@After
	public void tearDownHelper() {
		helper.tearDown();
	}

	@Test
	public void testDoGet() throws IOException {
		assertTrue(true);
	}

}
