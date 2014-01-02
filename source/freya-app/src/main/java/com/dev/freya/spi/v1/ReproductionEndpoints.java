package com.dev.freya.spi.v1;

import java.util.List;

import com.dev.freya.dao.FreyaDao;
import com.dev.freya.model.Reproduction;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;

@Api(
		name = "freya",
		version = "v1"
)
public class ReproductionEndpoints {
	
	@ApiMethod(
			name = "reproductions.list",
			path = "reproductions",
			httpMethod = HttpMethod.GET
			
	)
	public List<Reproduction> listReproductions() {
		FreyaDao dao = new FreyaDao();
		List<Reproduction> reproductions = dao.listReproductions();
		dao.close();
		return reproductions;
	}
	
	@ApiMethod(
			name = "reproductions.get",
			path = "reproductions/{reproduction_id}",
			httpMethod = HttpMethod.GET
			
	)
	public Reproduction getReproduction(@Named("reproduction_id") String reproductionId) {
		FreyaDao dao = new FreyaDao();
		Reproduction reproduction = dao.getReproduction(reproductionId);
		dao.close();
		return reproduction;
	}

	@ApiMethod(
			name = "reproductions.set.stock",
			path = "reproductions/{reproduction_id}/stock",
			httpMethod = HttpMethod.POST
	)
	public void setReproductionStock(@Named("reproduction_id") String reproductionId, @Named("stock") String stock) {
		FreyaDao dao = new FreyaDao();
		Reproduction repro = dao.getReproduction(reproductionId);
		if (repro != null)
			repro.setStock(Integer.valueOf(stock));
		dao.commitTransaction();
		dao.close();
	}
	
	@ApiMethod(
			name = "reproductions.set.price",
			path = "reproductions/{reproduction_id}/price",
			httpMethod = HttpMethod.POST
	)
	public void setReproductionPrice(@Named("reproduction_id") String reproductionId, @Named("stock") String price) {
		FreyaDao dao = new FreyaDao();
		Reproduction repro = dao.getReproduction(reproductionId);
		if (repro != null)
			repro.setPrice(Double.valueOf(price));
		dao.persistTransactional(repro);
		dao.close();
	}
}
