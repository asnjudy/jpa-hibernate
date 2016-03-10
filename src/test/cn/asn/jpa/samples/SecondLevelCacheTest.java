package test.cn.asn.jpa.samples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import cn.asn.jpa.samples.Customer;

public class SecondLevelCacheTest {
	
	
	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		
		Customer customer = entityManager.find(Customer.class, 2);
		
		
		transaction.commit();
		entityManager.close();
		
		

		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		EntityTransaction transaction2 = entityManager2.getTransaction();
		transaction2.begin();
		
		
		Customer customer2 = entityManager2.find(Customer.class, 2);
		
		
		transaction2.commit();
		entityManager2.close();

		entityManagerFactory.close();
		
	}

}
