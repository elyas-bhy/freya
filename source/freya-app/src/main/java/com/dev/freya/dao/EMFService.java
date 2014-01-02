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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFService {

	private static final EntityManagerFactory mFactory = Persistence.createEntityManagerFactory("freya");

	private EMFService() {

	}

	/**
	 * Returns a singleton instance of EntityManagerFactory
	 * @return
	 */
	public static EntityManagerFactory getInstance() {
		return mFactory;
	}

	/**
	 * Returns a new EntityManager
	 * @return
	 */
	public static EntityManager createEntityManager() {
		return mFactory.createEntityManager();
	}

}