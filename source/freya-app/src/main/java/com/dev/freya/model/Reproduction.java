package com.dev.freya.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;


@Entity
public class Reproduction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
	
	@Basic
	private Integer stock;
	
	@Basic
	private Double price;
	
	@Enumerated(EnumType.STRING)
	private ArtSupport support;
	
	public Reproduction() {
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getId() {
		return id;
	}

	public void setSupport(ArtSupport paintingLinenCanvas) {
		support = paintingLinenCanvas;
	}
}
