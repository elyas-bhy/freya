package com.dev.freya.spi.v1;

import java.util.Date;
import java.util.List;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtSupport;
import com.dev.freya.model.ArtTechnique;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Dimension;
import com.dev.freya.model.IArtwork;
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
	public List<IArtwork> listArtworks() {
		FreyaDao dao = new FreyaDao();
		List<IArtwork> artworks = dao.listArtworks();
		dao.close();
		return artworks;
	}
	
	@ApiMethod(
			name = "artworks.test",
			path = "artworks",
			httpMethod = HttpMethod.POST
	)
	public void test() {
		IArtwork artwork = new Artwork();
		artwork.setArtist("dali");
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
