package com.javacodegeeks.snippets.enterprise.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javacodegeeks.snippets.enterprise.hibernate.model.Course;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Student;

public class StudentDao implements StudentDaoInterface<Student, String> {

	private Session currentSession;

	public StudentDao(Session session) {
		this.currentSession = session;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public void persist(Student entity) {
		getCurrentSession().save(entity);
	}

	public void update(Student entity) {
		getCurrentSession().update(entity);
	}

	public Student findById(Integer id) {
		Student student = (Student) getCurrentSession().get(Student.class, id);
		return student;
	}

	public void registerCourse(Student student, Course course) {

		student.getCourses().add(course);
		this.persist(student);

	}

	public void delete(Integer id) {
		Student student = this.findById(id);
		this.delete(student);
	}

	public void delete(Student entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findAll() {
		List<Student> students = (List<Student>) getCurrentSession()
				.createQuery("from Student order by name").list();
		return students;
	}

	@SuppressWarnings("unchecked")
	public List<String> getStudentsByCourseName(List<String> courseNames) {
		String hql = "select  s.name from Student s " + "join s.courses c "
				+ "where c.name in (:courseNames)";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameterList("courseNames", courseNames);
		List<String> students = query.list();
		return students;
	}

	@SuppressWarnings("unchecked")
	public List<String> getStudentsNotRegistredForCourse(
			List<String> courseNames) {
		String hql = "select s1.name from Student s1 where s1.name not in( select distinct s.name from Student s "
				+ "join s.courses c " + "where c.name in (:courseNames) )";

		Query query = getCurrentSession().createQuery(hql);
		query.setParameterList("courseNames", courseNames);
		List<String> students = query.list();
		return students;
	}

	public void deleteAll() {
		List<Student> entityList = findAll();
		for (Student entity : entityList) {
			delete(entity);
		}
	}

}
