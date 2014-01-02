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
import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Reproduction;
import com.dev.freya.model.Response;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(
		name = "freya",
		version = "v1"
)
public class ArtCollectionEndpoints {
	

	/****************
	 * GET Requests *
	 ****************/

	@ApiMethod(
			name = "artcollections.list",
			path = "artcollections",
			httpMethod = HttpMethod.GET
			
	)
	public List<ArtCollection> listArtCollections() {
		FreyaDao dao = new FreyaDao();
		List<ArtCollection> artCollections = dao.listArtCollections();
		dao.close();
		return artCollections;
	}

	@ApiMethod(
			name = "artcollections.get",
			path = "artcollections/{artcollection_id}",
			httpMethod = HttpMethod.GET
			
	)
	public ArtCollection getArtCollection(@Named("artcollection_id") Long artCollectionId) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artCollection = dao.getArtCollection(artCollectionId);
		dao.close();
		return artCollection;
	}

	@ApiMethod(
			name = "artcollections.getArtworksByArtCollection",
			path = "artcollections/{artcollection_id}/artworks",
			httpMethod = HttpMethod.GET
			
	)
	public List<Artwork> getArtworksByArtCollection(@Named("artcollection_id") Long artCollectionId, @Named("count") Integer count) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtCollection(artCollectionId, count);
		dao.close();
		return artworks;
	}
	
	@ApiMethod(
			name = "artcollections.getReproductionsByArtCollection",
			path = "artcollections/{artcollection_id}/reproductions",
			httpMethod = HttpMethod.GET
	)
	public List<Reproduction> getReproductionsByArtCollection(@Named("artcollection_id") Long artCollectionId) {
		FreyaDao dao = new FreyaDao();
		List<Reproduction> reproductions = dao.getReproductionsByArtCollection(artCollectionId);
		dao.close();
		return reproductions;
	}

	/*****************
	 * POST Requests *
	 *****************/

	@ApiMethod(
			name = "artcollections.add",
			path = "artcollections/add",
			httpMethod = HttpMethod.POST
	)
	public Response addArtCollection(ArtCollection artCollection) {
		Response response = new Response();
		if (artCollection != null) {
			FreyaDao dao = new FreyaDao();
			dao.persistTransactional(artCollection);
			dao.close();
			response.setValue(artCollection.getId().toString());
		}
		return response;
	}

	@ApiMethod(
			name = "artcollections.addArtworkToArtCollection",
			path = "artcollections/{artcollection_id}/artworks/add",
			httpMethod = HttpMethod.POST
	)
	public Response addArtworkToArtCollection(
			@Named("artcollection_id") Long artCollectionId, Artwork artwork) {
		Response response = new Response();
		if (artwork != null) {
			FreyaDao dao = new FreyaDao();
			ArtCollection artcollection = dao.getArtCollection(artCollectionId);
			if (artcollection != null) {
				artcollection.addArtwork(artwork);
				response.setValue(artCollectionId.toString());
			}
			dao.close();
		}
		return response;
	}

	@ApiMethod(
			name = "artcollections.addCommentToArtCollection",
			path = "artcollections/{artcollection_id}/comments/add",
			httpMethod = HttpMethod.POST
	)
	public Response addCommentToArtCollection(
			@Named("artcollection_id") Long artCollectionId, @Named("comment") String comment) {
		Response response = new Response();
		if (comment != null) {
			FreyaDao dao = new FreyaDao();
			ArtCollection artcollection = dao.getArtCollection(artCollectionId);
			if (artcollection != null) {
				artcollection.addComment(comment);
				response.setValue(artCollectionId.toString());
			}
			dao.close();
		}
		return response;
	}
	
	@ApiMethod(
			name = "artcollections.addTagToArtCollection",
			path = "artcollections/{artcollection_id}/tags/add",
			httpMethod = HttpMethod.POST
	)
	public Response addTagToArtCollection(
			@Named("artcollection_id") Long artCollectionId, @Named("tag") String tag) {
		Response response = new Response();
		if (tag != null) {
			FreyaDao dao = new FreyaDao();
			ArtCollection artcollection = dao.getArtCollection(artCollectionId);
			if (artcollection != null) {
				artcollection.addTag(tag);
				response.setValue(artCollectionId.toString());
			}
			dao.close();
		}
		return response;
	}
}
