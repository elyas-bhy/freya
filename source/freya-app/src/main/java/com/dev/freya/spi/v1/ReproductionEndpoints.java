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

	/****************
	 * GET Requests *
	 ****************/
	
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

	/*****************
	 * POST Requests *
	 *****************/

	@ApiMethod(
			name = "reproductions.addStockToReproduction",
			path = "reproductions/{reproduction_id}/stock/add",
			httpMethod = HttpMethod.POST
	)
	public void addStockToReproduction(@Named("reproduction_id") String reproductionId, @Named("stock") String stock) {
		FreyaDao dao = new FreyaDao();
		Reproduction repro = dao.getReproduction(reproductionId);
		if (repro != null) {
			repro.setStock(Integer.valueOf(stock));
		}
		dao.close();
	}
	
	@ApiMethod(
			name = "reproductions.addPriceToReproduction",
			path = "reproductions/{reproduction_id}/price/add",
			httpMethod = HttpMethod.POST
	)
	public void addPriceToReproduction(@Named("reproduction_id") String reproductionId, @Named("stock") String price) {
		FreyaDao dao = new FreyaDao();
		Reproduction repro = dao.getReproduction(reproductionId);
		if (repro != null) {
			repro.setPrice(Double.valueOf(price));
		}
		dao.close();
	}
}
