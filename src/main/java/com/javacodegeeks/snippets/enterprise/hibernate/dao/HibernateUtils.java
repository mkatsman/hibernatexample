package com.javacodegeeks.snippets.enterprise.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtils {


	 private static  SessionFactory sessionFactory = createSessionFactory();
	  
	
	 private static ServiceRegistry serviceRegistry;

	 public static SessionFactory createSessionFactory() {
	     Configuration configuration = new Configuration();
	     configuration.configure();
	     serviceRegistry = new ServiceRegistryBuilder().applySettings(
	             configuration.getProperties()). buildServiceRegistry();
	     sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	     return sessionFactory;
	 }
	  
	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
}
