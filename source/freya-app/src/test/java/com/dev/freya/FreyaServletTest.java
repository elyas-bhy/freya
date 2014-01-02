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
