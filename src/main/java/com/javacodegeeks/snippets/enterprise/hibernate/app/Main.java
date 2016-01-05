package com.javacodegeeks.snippets.enterprise.hibernate.app;



import java.util.Arrays;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;

import com.javacodegeeks.snippets.enterprise.hibernate.dao.CourseDao;
import com.javacodegeeks.snippets.enterprise.hibernate.dao.HibernateUtils;
import com.javacodegeeks.snippets.enterprise.hibernate.dao.StudentDao;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Course;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Student;

public class Main {

	public static void main(String[] args) {

		SessionFactory sf = HibernateUtils.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		// preparation create students and courses

		StudentDao studentDao = new StudentDao(session);
		CourseDao courseDao = new CourseDao(session);
		Student student1 = new Student("John Smith");
		Student student2 = new Student("Kevin Jordan");
		
		Course course1 = new Course("Math");
		Course course2 = new Course("English");
		Course course3 = new Course("Science");

		try {
			studentDao.persist(student1);
			courseDao.persist(course1);
			courseDao.persist(course2);
			courseDao.persist(course3);

			// add course to students
			// course "English" added to two students
			//course "Math added to one student
			student1.getCourses().add(course1);
			student1.getCourses().add(course2);

			student2.getCourses().add(course2);

			studentDao.persist(student1);
			studentDao.persist(student2);
	       // get all the students for the course
			String courseName  = course2.getName();
		    List<String> students = studentDao.getStudentsByCourseName(Arrays.asList(courseName));
		 	System.out.println("Students  who took the the course: "+ courseName);
		 	        
		    for (String student:students){
		    	System.out.println(student);
	        }
		    
            Assert.assertEquals(2, students.size());
             //check for the course "Math"   
		    students = studentDao.getStudentsByCourseName(Arrays.asList(course1.getName()));
		    
            Assert.assertEquals(1, students.size());
            //Both students are not registered for Science.
           students = studentDao.getStudentsNotRegistredForCourse(Arrays.asList(course3.getName()));
   		  
		   Assert.assertEquals(2, students.size());
        
		    //One students is not registered for "Math".
	       students = studentDao.getStudentsNotRegistredForCourse(Arrays.asList(course1.getName()));
			 Assert.assertEquals(1, students.size());
	        
		    // clear the refs
			student1.getCourses().clear();
			studentDao.persist(student1);
	
		       
			student2.getCourses().clear();
			studentDao.persist(student2);
            courseDao.deleteAll();

			studentDao.deleteAll();
			session.getTransaction().commit();
			System.out.println("Passed");
		
		} catch (AssertionError aer) {
			aer.printStackTrace();
			System.out.println("Failed");
			session.getTransaction().rollback();
		
	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Failed");
			session.getTransaction().rollback();
		}
		session.close();
		// commit and close the transaction
		System.out.println("Done");
        System.exit(1);
	}
}
