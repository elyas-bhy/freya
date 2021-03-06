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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.ArtSupport;
import com.dev.freya.model.ArtTechnique;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
import com.dev.freya.model.Photo;
import com.dev.freya.model.Reproduction;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(
		name = "freya",
		version = "v1"
)
public class ArtistEndpoints {


	/****************
	 * GET Requests *
	 ****************/
	
	@ApiMethod(
			name = "artists.get",
			path = "artists/{artist_id}",
			httpMethod = HttpMethod.GET
			
	)
	public Artist getArtist(@Named("artist_id") String artistId) {
		FreyaDao dao = new FreyaDao();
		Artist artist = dao.getArtist(artistId);
		dao.close();
		return artist;
	}
	
	@ApiMethod(
			name = "artists.list",
			path = "artists",
			httpMethod = HttpMethod.GET
			
	)
	public List<Artist> listArtists() {
		FreyaDao dao = new FreyaDao();
		List<Artist> artists = dao.listArtists();
		dao.close();
		return artists;
	}
	
	@ApiMethod(
			name = "artists.getArtworksByArtist",
			path = "artists/{artist_id}/artworks",
			httpMethod = HttpMethod.GET
			
	)
	public List<Artwork> getArtworksByArtist(
			@Named("artist_id") String artistId, 
			@Nullable @Named("support") String support, 
			@Nullable @Named("technique") String technique,
			@Nullable @Named("year") String year,
			@Nullable @Named("tag") String tag) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtist(artistId, support, technique, year, tag);
		dao.close();
		return artworks;
	}
	
	@ApiMethod(
			name = "artists.getPhotosByArtist",
			path = "artists/{artist_id}/photos",
			httpMethod = HttpMethod.GET
			
	)
	public List<Photo> getPhotosByArtist(
			@Named("artist_id") String artistId,
			@Nullable @Named("support") String support,
			@Nullable @Named("technique") String technique,
			@Nullable @Named("year") String year,
			@Nullable @Named("tag") String tag) {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getPhotosByArtist(artistId, support, technique, year, tag);
		dao.close();
		return photos;
	}
	
	/*****************
	 * POST Requests *
	 *****************/
	
	@ApiMethod(
			name = "test.populate",
			path = "populate",
			httpMethod = HttpMethod.POST
	)
	public void populate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Artwork artwork1 = new Artwork();
		artwork1.setArtist(new Artist("Dali"));
		artwork1.setTitle("Title 1");
		artwork1.setSupport(ArtSupport.PAINTING_CARDBOARD);
		artwork1.setTechnique(ArtTechnique.PAINTING_ACRYLIC);
		artwork1.setDate(sdf.format(new Date()));
		artwork1.setSummary("Summary 1");
		artwork1.addPhoto(new Photo("Desc 1", "URL 1"));
		artwork1.addPhoto(new Photo("Desc 2", "URL 2"));
		artwork1.setDimension(new Dimension(4, 5, 6));

		Artwork artwork2 = new Artwork();
		artwork2.setArtist(new Artist("Pablo Picasso"));
		artwork2.setTitle("Title 2");
		artwork2.setSupport(ArtSupport.PAINTING_LINEN_CANVAS);
		artwork2.setTechnique(ArtTechnique.PAINTING_GOUACHE);
		artwork2.setDate("1999-12-31");
		artwork2.setSummary("Summary 2");
		artwork2.addPhoto(new Photo("Desc 3", "URL 3"));
		artwork2.addPhoto(new Photo("Desc 4", "URL 4"));
		artwork2.setDimension(new Dimension(12, 10, 15));
		artwork2.addTag("modern");
		
		Artwork artwork3 = new Artwork();
		artwork3.setArtist(new Artist("Dali"));
		artwork3.setTitle("Title 3");
		artwork3.setSupport(ArtSupport.PAINTING_PAPER);
		artwork3.setTechnique(ArtTechnique.PAINTING_WATERCOLOR);
		artwork3.setDate("2010-11-29");
		artwork3.setSummary("Summary 3");
		artwork3.setDimension(new Dimension(20, 5, 35));
		artwork3.addTag("abstract");
		
		Reproduction repro = new Reproduction();
		repro.setPrice(1000.0);
		repro.setStock(4);
		repro.setSupport(ArtSupport.PAINTING_COTTON_CANVAS);
		repro.setTechnique(ArtTechnique.PAINTING_OIL);
		artwork3.addReproduction(repro);
		
		ArtCollection collection1 = new ArtCollection();
		collection1.addArtwork(artwork1);
		collection1.addArtwork(artwork2);
		
		ArtCollection collection2 = new ArtCollection();
		collection2.addArtwork(artwork1);
		collection2.addArtwork(artwork3);
		
		FreyaDao dao = new FreyaDao();
		dao.persistTransactional(collection1);
		dao.persistTransactional(artwork3);
		dao.persistTransactional(collection2);
		repro.setArtworkId(artwork3.getId());
		dao.persist(repro);
		dao.close();
	}
	
}
