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

package com.dev.freya.spi.v1;

import java.util.List;

import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Photo;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(
		name = "freya",
		version = "v1"
)
public class ArtworkEndpoints {

	/****************
	 * GET Requests *
	 ****************/
	
	@ApiMethod(
			name = "artworks.list",
			path = "artworks",
			httpMethod = HttpMethod.GET
			
	)
	// TODO add support for year, tag and reproduced_count query filters
	public List<Artwork> listArtworks(
			@Named("support") String support, @Named("technique") String technique) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworks(support, technique);
		dao.close();
		return artworks;
	}

	@ApiMethod(
			name = "artworks.photos",
			path = "artworks/photos",
			httpMethod = HttpMethod.GET
			
	)
	// TODO add support for query filters
	public List<Photo> getArtworkPhotos() {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos();
		dao.close();
		return photos;
	}
	
	@ApiMethod(
			name = "artworks.get",
			path = "artworks/{artwork_id}",
			httpMethod = HttpMethod.GET
			
	)
	public Artwork getArtwork(@Named("artwork_id") String artworkId) {
		FreyaDao dao = new FreyaDao();
		Artwork artwork = dao.getArtwork(artworkId);
		dao.close();
		return artwork;
	}
	
	@ApiMethod(
			name = "artworks.artwork.photos",
			path = "artworks/{artwork_id}/photos",
			httpMethod = HttpMethod.GET
			
	)
	public List<Photo> getPhotosByArtwork(@Named("artwork_id") String artworkId) {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtwork(artworkId);
		dao.close();
		return photos;
	}

	/*****************
	 * POST Requests *
	 *****************/
	// TODO
}
