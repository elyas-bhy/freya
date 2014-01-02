package com.dev.freya.spi.v1;

import java.util.List;

import javax.inject.Named;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.ArtCollection;
import com.dev.freya.model.Artwork;
import com.dev.freya.model.Reproduction;
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
			name = "artcollections.artcollection.reproductions",
			path = "artcollections/{artcollection_id}/reproductions",
			httpMethod = HttpMethod.GET
	)
	public List<Reproduction> getReproductionsByArtCollection(@Named("artcollection_id") Long artCollectionId) {
		FreyaDao dao = new FreyaDao();
		List<Reproduction> reproductions = dao.getReproductionsByArtCollection(artCollectionId);
		dao.close();
		return reproductions;
	}

//	@ApiMethod(
//			name = "artcollections.add",
//			path = "artcollections",
//			httpMethod = HttpMethod.POST
//	)
//	public void addArtCollection(List<Artwork> artworks) {
//		ArtCollection artcollection = new ArtCollection(artworks);
//		FreyaDao dao = new FreyaDao();
//		dao.persistTransactional(artcollection);
//		dao.close();
//	}

	@ApiMethod(
			name = "artcollections.addartwork",
			path = "artcollections/{artcollection_id}/add",
			httpMethod = HttpMethod.POST
	)
	public void addArtworkToArtCollection(@Named("artcollection_id") Long artCollectionId, Artwork artwork) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artCollectionId);
		if (artcollection != null)
			artcollection.addArtwork(artwork);
		dao.close();
	}

	@ApiMethod(
			name = "artcollections.addcomment",
			path = "artcollections/{artcollection_id}/comment",
			httpMethod = HttpMethod.POST
	)
	public void addCommentToArtCollection(@Named("artcollection_id") Long artCollectionId, @Named("comment") String comment) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artCollectionId);
		if (artcollection != null)
			artcollection.addComment(comment);
		dao.close();
	}
	
	@ApiMethod(
			name = "artcollections.addtag",
			path = "artcollections/{artcollection_id}/tag",
			httpMethod = HttpMethod.POST
	)
	public void addTagToArtCollection(@Named("artcollection_id") Long artCollectionId, @Named("tag") String tag) {
		FreyaDao dao = new FreyaDao();
		ArtCollection artcollection = dao.getArtCollection(artCollectionId);
		if (artcollection != null)
			artcollection.addTag(tag);
		dao.close();
	}
}
