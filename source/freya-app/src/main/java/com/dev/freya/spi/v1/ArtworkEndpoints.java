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

	@ApiMethod(
			name = "artworks.list",
			path = "artworks",
			httpMethod = HttpMethod.GET
			
	)
	public List<Artwork> listArtworks(
			@Named("support") String support, @Named("technique") String technique) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.listArtworks(support, technique);
		dao.close();
		return artworks;
	}

	@ApiMethod(
			name = "artworks.get",
			path = "artworks/{artwork_id}",
			httpMethod = HttpMethod.GET
			
	)
	public Artwork getArtwork(@Named("artwork_id") Long artworkId) {
		FreyaDao dao = new FreyaDao();
		Artwork artwork = dao.getArtwork(artworkId);
		dao.close();
		return artwork;
	}
	
	@ApiMethod(
			name = "artworks.getphotos",
			path = "artworks/{artwork_id}/photos",
			httpMethod = HttpMethod.GET
			
	)
	public List<Photo> getArtworkPhotos(@Named("artwork_id") Long artworkId) {
		FreyaDao dao = new FreyaDao();
		List<Photo> photos = dao.getArtworkPhotos(artworkId);
		dao.close();
		return photos;
	}
}
