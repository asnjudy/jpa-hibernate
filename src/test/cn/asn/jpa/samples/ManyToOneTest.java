package test.cn.asn.jpa.samples;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import cn.asn.jpa.samples.Customer;
import cn.asn.jpa.samples.Order;

public class ManyToOneTest {
	
	
	
	public void testManyToOnePersist() {
		
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("123456");
		
		Order order = new Order();
		order.setOrderName("o1");
		
		Order order2 = new Order();
		order2.setOrderName("o2");
		
		
		customer.getOrders().add(order); //update order_name, customer_id
		customer.getOrders().add(order2);
		
		order.setCustomer(customer); //update customer_id
		order2.setCustomer(customer);
		
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		
		entityManager.persist(customer);
		entityManager.persist(order);
		entityManager.persist(order2);
		
		
		
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
		
	}
	
	@Test
	public void testManyToOneFind() {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//��ȡn�˵Ķ���ʱ��Ĭ��ʹ�� left out join ��ȡ�������1�˶���
		Order order = entityManager.find(Order.class, 1);
		System.out.println(order.getOrderName());
		
//		 ������
//		 *  select
//		 *  	.....
//		 *  from
//		 *  	JPA_CUSTOMERS customer0_ 
//		 *  where
//		 *  	customer0_.id=? (ORDERS.CUSTOMER_ID)
		 
		System.out.println(order.getCustomer());
		
		
		//˫������ô������ǿ���˫�򵼺�
		//Customer customer = entityManager.find(Customer.class, 1);
		//System.out.println(customer.getOrders());
		
		
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	//�޸��ֶε�ֵ
	
	public void testManyToOneUpdate() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Order order = entityManager.find(Order.class, 1);
		order.getCustomer().setLastName("YYYYYY"); //��em����ά���ĳ־û�������޸Ļ��Զ����»����ݿ���
		
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	public void testManyToOneRemove() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//
	//	Customer customer = entityManager.find(Customer.class, 1);
	//	entityManager.remove(customer);
		
		Order order = entityManager.find(Order.class, 1);
		entityManager.remove(order);
		
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}

}
