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
		
		System.out.println("---------------- 提交事务 ---------------------");
		entityTransaction.commit();
		//entityTransaction.getRollbackOnly();
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
	
	public void testPersistence() {
		
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");
		
		entityManager.persist(customer);
		
		Customer customer2 = new Customer();
		customer2.setAge(12);
		customer2.setBirth(new Date());
		customer2.setCreateTime(new Date());
		customer2.setEmail("aa@163.com");
		customer2.setLastName("AA");
		entityManager.persist(customer2);
	}

	
	public void testRemove() {
		/*
		 *  select 
		 *  	... 
		 *  from 
		 *  	JPA_CUSTOMERS customer0_ 
		 *  where 
		 *  	customer0_.id=?
		 */
		Customer customer = entityManager.find(Customer.class, 5); 

		
		/*
		 *  delete 
		 *  from 
		 * 		JPA_CUSTOMERS
		 *  where 
		 *  	id=?
		 */
		entityManager.remove(customer); //remove只能remove持久态对象（entityManager维护的对象）
		
		System.out.println(customer); //customer对象属性中已有数据，调用其toString方法
	}
	
	
	public void testMerge1() {
		
		// 键值id属性为null，新new的customer对象位临时对象
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("cc@163.com");
		customer.setLastName("CC");
		
		/*
		 *  新建一个Customer对象，把‘临时对象的属性’复制到新的对象中
		 *  "对这个新建的对象进行持久化操作" - 把这个新建的对象添加到em缓存中
		 *  	（涉及到要分配ID键值，所以要立即与底层数据库进行交换 - 先预占一个ID数据先保存在数据库的缓存中，执行事务提交后，才最终归档写入到数据中）
		 *  因为是对新的对象执行持久化操作，所以新的对象中有id，但以前的临时对象中没有id
		 *  insert 
		    into
		        JPA_CUSTOMERS
		        (age, birth, createTime, email, LAST_NAME) 
		    values
		        (?, ?, ?, ?, ?)
		 */
		Customer customer2 = entityManager.merge(customer);
		System.out.println(entityManager.find(Customer.class, 23));	 //在本持久化上下文中可见
		
		
		EntityManager entityManager2 = entityManagerFactory.createEntityManager(); //在其他持久化上下文中不可见
		System.out.println(entityManager2.find(Customer.class, 23)); //null
		entityManager2.close();
	}
	
	@Test
	public void testMerge2() {
		
		// 把键值id为5的记录加载到entityManager缓存中
		Customer cust = entityManager.find(Customer.class, 15);
		
		// 新new的customer对象（不受entityManager托管，为游离态对象）
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("123456");
		customer.setId(15); //设置键值为15，缓存中存在键值为15的持久化对象
		
		/*
		 *  对键值id为5的游离态对象customer执行merge过程：
		 *  	1）如果在em缓存中维护了一个id值为5的Customer对象，则“在em中定位到键值相等的Customer对象并更新其属性” - 用游离态的属性值覆盖entityManager缓存中的持久态对象属性；
		 *  	      如果在em缓存中没有维护一个id值为5的Customer对象，则新建一个Customer对象（该对象的键值由生成规则自动生成）并添加到em缓存中，复制除键值id之外的值到新建的对象
		 *  	2）更新了em缓存中的持久态对象属性后，在最后提交该事务时会把修改更新回数据库 
		 *  	注：对em缓存中持久态对象的更新，直到执行commit时才会更新回数据库
		 */
		
		Customer cust2 = entityManager.merge(customer); 
		
		System.out.println(entityManager.find(Customer.class, 15));	 //在本持久化上下文中修改可见
		
		
		EntityManager entityManager2 = entityManagerFactory.createEntityManager(); 
		System.out.println(entityManager2.find(Customer.class, 15)); //在其他持久化上下文中修改不可见，为原值
		entityManager2.close();
	
	}
	
	/*
	 * refresh 用数据库实体记录的值，更新实体对象的状态
	 * clear	清除持久化上下文环境，断开所以关联的实体，如果这时还有未提交的更新将被撤销
	 * contains	判断一个实例是否属于当前持久上下文环境管理器中
	 * isOpen	判断当前的实体管理器是否打开状态
	 */
	public void testFlush() {
		
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		System.out.println(entityManager2); //org.hibernate.ejb.EntityManagerImpl@770440dd
		System.out.println(entityManager);	//org.hibernate.ejb.EntityManagerImpl@383eaa36
		System.out.println(entityManager.getFlushMode()); //默认自动提交模式
	}
}
