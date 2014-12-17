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

import javax.annotation.Nullable;
import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtSupport;
import com.dev.freya.model.ArtTechnique;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
import com.dev.freya.model.Photo;
import com.dev.freya.model.Reproduction;
import com.dev.freya.model.Response;
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
	public List<Artwork> listArtworks(
			@Nullable @Named("support") String support, 
			@Nullable @Named("technique") String technique, 
			@Nullable @Named("year") String year,
			@Nullable @Named("tag") String tag,
			@Nullable @Named("reproduction_count") Integer count) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworks(support, technique, year, tag, count);
		dao.close();
		return artworks;
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
			name = "artworks.getPhotos",
			path = "artworks/photos",
			httpMethod = HttpMethod.GET
	)
	public List<Photo> getArtworkPhotos(
			@Nullable @Named("support") String support,
			@Nullable @Named("technique") String technique,
			@Nullable @Named("year") String year,
			@Nullable @Named("tag") String tag) {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos(support, technique, year, tag);
		dao.close();
		return photos;
	}

	@ApiMethod(
			name = "artworks.getPhotosByArtwork",
			path = "artworks/{artwork_id}/photos",
			httpMethod = HttpMethod.GET
	)
	public List<Photo> getPhotosByArtwork(@Named("artwork_id") String artworkId) {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtwork(artworkId);
		dao.close();
		return photos;
	}

	@ApiMethod(
			name = "artworks.getReproductionsByArtwork",
			path = "artworks/{artwork_id}/reproductions",
			httpMethod = HttpMethod.GET
	)
	public List<Reproduction> getReproductionsByArtwork(@Named("artwork_id") String artworkId) {
		FreyaDao dao = new FreyaDao();
		List<Reproduction> reproductions = dao.getReproductionsByArtwork(artworkId);
		dao.close();
		return reproductions;
	}

	/*****************
	 * POST Requests *
	 *****************/
	
	@ApiMethod(
			name = "artworks.add",
			path = "artworks/add",
			httpMethod = HttpMethod.POST
	)
	public Response addArtwork(Artwork artwork) {
		Response response = new Response();
		if (artwork != null) {
			FreyaDao dao = new FreyaDao();
			dao.persistTransactional(artwork);
			dao.close();
			response.setValue(artwork.getId());
		}
		return response;
	}

	@ApiMethod(
			name = "artworks.addArtistToArtwork",
			path = "artworks/{artwork_id}/artist/add",
			httpMethod = HttpMethod.POST
	)
	public Response addArtistToArtwork(@Named("artwork_id") String artworkId, Artist artist) {
		Response response = new Response();
		if (artist != null) {
			FreyaDao dao = new FreyaDao();
			Artwork artwork = dao.getArtwork(artworkId);
			if (artwork != null) {
				artwork.setArtist(artist);
				dao.refresh(artworkId, artwork);
				response.setValue(artwork.getId());
			}
			dao.close();
		}
		return response;
	}

	@ApiMethod(
			name = "artworks.addPhotoToArtwork",
			path = "artworks/{artwork_id}/photo/add",
			httpMethod = HttpMethod.POST
	)
	public Response addPhotoToArtwork(@Named("artwork_id") String artworkId, Photo photo) {
		Response response = new Response();
		if (photo != null) {
			FreyaDao dao = new FreyaDao();
			Artwork artwork = dao.getArtwork(artworkId);
			if (artwork != null) {
				artwork.addPhoto(photo);
				dao.refresh(artworkId, artwork);
				response.setValue(artwork.getId());
			}
			dao.close();
		}
		return response;
	}
	
	@ApiMethod(
			name = "artworks.addDimensionsToArtwork",
			path = "artworks/{artwork_id}/dimensions/add",
			httpMethod = HttpMethod.POST
	)
	public Response addDimensionsToArtwork(@Named("artwork_id") String artworkId, Dimension dimension) {
		Response response = new Response();
		if (dimension != null) {
			FreyaDao dao = new FreyaDao();
			Artwork artwork = dao.getArtwork(artworkId);
			if (artwork != null) {
				artwork.setDimension(dimension);
				dao.refresh(artworkId, artwork);
				response.setValue(artwork.getId());
			}
			dao.close();
		}
		return response;
	}

	@ApiMethod(
			name = "artworks.addReproductionToArtwork",
			path = "artworks/{artwork_id}/reproductions/add",
			httpMethod = HttpMethod.POST
	)
	public Response addReproductionToArtwork(@Named("artwork_id") String artworkId, Reproduction repro) {
		Response response = new Response();
		if (repro != null) {
			FreyaDao dao = new FreyaDao();
			Artwork artwork = dao.getArtwork(artworkId);
			if (artwork != null) {
				artwork.addReproduction(repro);
				dao.refresh(artworkId, artwork);
				response.setValue(artworkId);
			}
			dao.close();
		}
		return response;
	}
	
	@ApiMethod(
			name = "artworks.addMetadataToArtwork",
			path = "artworks/{artwork_id}/metadata/add",
			httpMethod = HttpMethod.POST
	)
	public Response addMetadataToArtwork(
			@Named("artwork_id") String artworkId,
			@Nullable @Named("support") ArtSupport support, 
			@Nullable @Named("technique") ArtTechnique technique,
			@Nullable @Named("title") String title, @Nullable @Named("summary") String summary,
			@Nullable @Named("tag") String tag, @Nullable @Named("comment") String comment) {
		Response response = new Response();
		FreyaDao dao = new FreyaDao();
		Artwork artwork = dao.getArtwork(artworkId);
		if (artwork != null) {
			if (support != null) artwork.setSupport(support);
			if (technique != null) artwork.setTechnique(technique);
			if (title != null) artwork.setTitle(title);
			if (summary != null) artwork.setSummary(summary);
			if (tag != null) artwork.addTag(tag);
			if (comment != null) artwork.addComment(comment);
			dao.refresh(artworkId, artwork);
			response.setValue(artworkId);
		}
		dao.close();
		return response;
	}
	
}
