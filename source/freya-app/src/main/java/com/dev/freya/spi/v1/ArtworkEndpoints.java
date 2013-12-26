package com.dev.freya.spi.v1;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtSupport;
import com.dev.freya.model.ArtTechnique;
import com.dev.freya.model.Artist;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
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
			name = "artworks.test",
			path = "artworks",
			httpMethod = HttpMethod.POST
	)
	public void test() {
		Artwork artwork = new Artwork();
		artwork.setArtist(new Artist("Dali"));
		artwork.setDate(new Date());
		artwork.setDimension(new Dimension(4, 5, 6));
		artwork.setSummary("summary");
		artwork.setTechnique(ArtTechnique.PAINTING_ACRYLIC);
		artwork.setSupport(ArtSupport.PAINTING_CARDBOARD);
		
		FreyaDao dao = new FreyaDao();
		dao.beginTransaction();
		dao.persist(artwork);
		dao.commitTransaction();
		dao.close();
	}
}
