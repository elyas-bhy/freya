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

package com.dev.freya.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Artist implements Serializable {
	
	private static final long serialVersionUID = 8115292118258530592L;

	@Id
	private String id;
	
	@Basic(fetch = FetchType.EAGER)
	private String name;
	
	public Artist() {
		
	}
	
	public Artist(String name) {
		this();
		this.name = name;
		this.id = KeyFactory.createKeyString("Artist", name);
		this.id = id.substring(0, id.length() - 1);  // JPA workaround
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
