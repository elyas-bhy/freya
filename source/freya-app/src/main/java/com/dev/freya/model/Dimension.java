package com.dev.freya.model;

import javax.persistence.Embeddable;

@Embeddable
public class Dimension {

	private float x;
	private float y;
	private float z;
	
	public Dimension() {
		
	}
}
