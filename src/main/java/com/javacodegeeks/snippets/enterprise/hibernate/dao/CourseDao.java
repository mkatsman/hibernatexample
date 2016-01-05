package com.javacodegeeks.snippets.enterprise.hibernate.dao;

import java.util.List;

import org.hibernate.Session;

import com.javacodegeeks.snippets.enterprise.hibernate.model.Course;

public class CourseDao implements CourseDaoInterface<Course, String> {

	private Session currentSession;
	
	
	public CourseDao(Session session) {
	 this.currentSession = session;
	}

		public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	
	public void persist(Course entity) {
		getCurrentSession().save(entity);
	}

	public void update(Course entity) {
		getCurrentSession().update(entity);
	}

	public Course findById(Integer id1) {
		Course student = (Course) getCurrentSession().get(Course.class, id1);
		return student; 
	}

	public void delete(Course entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Course> findAll() {
		List<Course> students = (List<Course>) getCurrentSession().createQuery("from Course").list();
		return students;
	}


	public void deleteAll() {
		List<Course> entityList = findAll();
		for (Course entity : entityList) {
			delete(entity);
		}
	}

	

	

	

}
