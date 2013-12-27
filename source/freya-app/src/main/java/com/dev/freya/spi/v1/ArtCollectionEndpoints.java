package com.dev.freya.spi.v1;

import java.util.List;

import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.Artwork;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@Api(
		name = "freya",
		version = "v1"
)
public class ArtCollectionEndpoints {

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
			name = "artcollections.artcollection.artworks",
			path = "artcollections/{artcollection_id}/artworks",
			httpMethod = HttpMethod.GET
			
	)
	// TODO add support for reproduced_count query filter
	public List<Artwork> getArtworksByArtCollection(@Named("artcollection_id") Long artCollectionId) {
		FreyaDao dao = new FreyaDao();
		List<Artwork> artworks = dao.getArtworksByArtCollection(artCollectionId);
		dao.close();
		return artworks;
	}

	@ApiMethod(
			name = "artcollections.add",
			path = "artcollections",
			httpMethod = HttpMethod.POST
	)
	public void addArtCollection(List<Artwork> artworks) {
		ArtCollection artcollection = new ArtCollection(artworks);
		FreyaDao dao = new FreyaDao();
		dao.persistTransactional(artcollection);
		dao.close();
	}

	@ApiMethod(
			name = "artcollections.addartwork",
			path = "artcollections/{artcollection_id}",
			httpMethod = HttpMethod.POST
	)
	public void addArtworkToArtCollection(Long artCollectionId, Artwork artwork) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artCollectionId);
		if (artcollection != null)
			artcollection.addArtwork(artwork);
		dao.close();
	}

	@ApiMethod(
			name = "artcollections.addcomment",
			path = "artcollections/{artcollection_id}",
			httpMethod = HttpMethod.POST
	)
	public void addCommentToArtCollection(Long artCollectionId, String comment) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artCollectionId);
		if (artcollection != null)
			artcollection.addComment(comment);
		dao.close();
	}
	
	@ApiMethod(
			name = "artcollections.addtag",
			path = "artcollections/{artcollection_id}",
			httpMethod = HttpMethod.POST
	)
	public void addTagToArtCollection(Long artCollectionId, String tag) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artCollectionId);
		if (artcollection != null)
			artcollection.addTag(tag);
		dao.close();
	}
}
