package test.cn.asn.jpa.samples;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.asn.jpa.samples.Customer;

public class JPATest {
	
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
		entityTransaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	//相当于 Hibernate 中 Session 的 get 方法
	public void testFind() {
		
		//立刻就去数据库中查出了该客户信息
		Customer customer = entityManager.find(Customer.class, 1);
		
		System.out.println(customer.getClass().getName());
		System.out.println("-------------------------------------");
		System.out.println(customer);
		
	}
	
	
	//相当于 Hibernate 中 Session 的 load 方法

	public void testGetReference() {
		
		//先构造一个客户信息代理对象，当具体要用这个对象的时候才真正到数据库中读取数据
		Customer customer = entityManager.getReference(Customer.class, 1);
		
		System.out.println(customer.getClass().getName());
		System.out.println("-------------------------------------");
		System.out.println(customer);
	}
	
	
	//相当于 Hibernate 中 Session 的 save 方法, 瞬时态 -> 持久态
	@Test
	public void testPersistence() {
		
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");
		
		entityManager.persist(customer);
		
	}

}
