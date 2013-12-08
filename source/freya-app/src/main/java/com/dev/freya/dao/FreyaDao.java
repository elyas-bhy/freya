package com.dev.freya.dao;

import javax.persistence.EntityManager;

public class FreyaDao {
	
	private EntityManager mEntityManager;
	
	public FreyaDao() {
		mEntityManager = EMFService.createEntityManager();
	}
	
	public void close() {
		mEntityManager.close();
	}
	
	public void beginTransaction() {
		mEntityManager.getTransaction().begin();
	}
	
	public void commitTransaction() {
		mEntityManager.getTransaction().commit();
	}

}
