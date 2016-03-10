package test.cn.asn.jpa.samples;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.asn.jpa.samples.Customer;

public class JPQLTest {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	
	
	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
	}
	
	
	@After
	public void destory() {
		
		System.out.println("---------------- 提交事务 ---------------------");
		entityTransaction.commit();
		//entityTransaction.getRollbackOnly();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	@Test
	public void testJPQL() {
		String jpql = "FROM Customer c WHERE c.age > ?";
		Query query = entityManager.createQuery(jpql);
		
		query.setParameter(1, 1);
		List<Customer> customers = query.getResultList();
		System.out.println(customers.size());
	}
}
