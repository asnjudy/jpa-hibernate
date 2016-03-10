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
		
		//获取n端的对象时，默认使用 left out join 获取其关联的1端对象
		Order order = entityManager.find(Order.class, 1);
		System.out.println(order.getOrderName());
		
//		 懒加载
//		 *  select
//		 *  	.....
//		 *  from
//		 *  	JPA_CUSTOMERS customer0_ 
//		 *  where
//		 *  	customer0_.id=? (ORDERS.CUSTOMER_ID)
		 
		System.out.println(order.getCustomer());
		
		
		//双向关联好处，就是可以双向导航
		//Customer customer = entityManager.find(Customer.class, 1);
		//System.out.println(customer.getOrders());
		
		
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	//修改字段的值
	
	public void testManyToOneUpdate() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Order order = entityManager.find(Order.class, 1);
		order.getCustomer().setLastName("YYYYYY"); //对em缓存维护的持久化对象的修改会自动更新回数据库中
		
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
