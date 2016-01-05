package com.javacodegeeks.snippets.enterprise.hibernate.dao;

import java.io.Serializable;
import java.util.List;

public interface StudentDaoInterface<T, Id extends Serializable> {

	public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Integer id1);
	
	public void delete(Integer id);
	
	public List<T> findAll();
	
	public void deleteAll();
	
}
