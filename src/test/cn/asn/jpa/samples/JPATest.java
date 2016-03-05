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
	
	
	//�൱�� Hibernate �� Session �� get ����
	public void testFind() {
		
		//���̾�ȥ���ݿ��в���˸ÿͻ���Ϣ
		Customer customer = entityManager.find(Customer.class, 1);
		
		System.out.println(customer.getClass().getName());
		System.out.println("-------------------------------------");
		System.out.println(customer);
		
	}
	
	
	//�൱�� Hibernate �� Session �� load ����

	public void testGetReference() {
		
		//�ȹ���һ���ͻ���Ϣ������󣬵�����Ҫ����������ʱ������������ݿ��ж�ȡ����
		Customer customer = entityManager.getReference(Customer.class, 1);
		
		System.out.println(customer.getClass().getName());
		System.out.println("-------------------------------------");
		System.out.println(customer);
	}
	
	
	//�൱�� Hibernate �� Session �� save ����, ˲ʱ̬ -> �־�̬
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
