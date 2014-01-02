package com.dev.freya.spi.v1;

import java.util.List;

import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Photo;
import com.dev.freya.model.Reproduction;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(
		name = "freya",
		version = "v1"
)
public class ArtworkEndpoints {

	@ApiMethod(
			name = "artworks.list",
			path = "artworks",
			httpMethod = HttpMethod.GET
			
	)
	// TODO add support for year, tag query filters
	public List<Artwork> listArtworks(
			@Named("support") String support, @Named("technique") String technique, @Named("count") int count) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworks(support, technique, count);
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
	
	@ApiMethod(
			name = "artworks.artwork.reproductions",
			path = "artworks/{artwork_id}/reproductions",
			httpMethod = HttpMethod.GET
	)
	public List<Reproduction> getReproductionsByArtwork(@Named("artwork_id") String artworkId) {
		FreyaDao dao = new FreyaDao();
		List<Reproduction> reproductions = dao.getReproductionsByArtwork(artworkId);
		dao.close();
		return reproductions;
	}
}
