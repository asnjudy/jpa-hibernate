package cn.asn.jpa.samples;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
	
	public static void main(String[] args) {
		
		//1. ����EntityManagerFactory
		String persistenceUnitName = "jpa-2";
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		//2. ����EntityManager
		EntityManager entityManager = factory.createEntityManager();
		
		//3. ��ʼ����
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//4. ���г־û�����
//		Customer customer = new Customer();
//		customer.setAge(12);
//		customer.setEmail("tom@qq.com");
//		customer.setLastName("Tom");
//		
//		entityManager.persist(customer);
		
		Course course = new Course();
		course.setCourseName("C����");
		entityManager.persist(course);
		
		
		//5. �ύ����
		transaction.commit();
		
		//6. �ر�EntityManager
		entityManager.close();
		
		//7. �ر�EntityManagerFactory
		factory.close();
	}

}
