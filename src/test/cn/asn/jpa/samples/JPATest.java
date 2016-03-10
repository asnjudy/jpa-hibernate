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
		
		System.out.println("---------------- �ύ���� ---------------------");
		entityTransaction.commit();
		//entityTransaction.getRollbackOnly();
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
		entityManager.remove(customer); //removeֻ��remove�־�̬����entityManagerά���Ķ���
		
		System.out.println(customer); //customer�����������������ݣ�������toString����
	}
	
	
	public void testMerge1() {
		
		// ��ֵid����Ϊnull����new��customer����λ��ʱ����
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("cc@163.com");
		customer.setLastName("CC");
		
		/*
		 *  �½�һ��Customer���󣬰ѡ���ʱ��������ԡ����Ƶ��µĶ�����
		 *  "������½��Ķ�����г־û�����" - ������½��Ķ�����ӵ�em������
		 *  	���漰��Ҫ����ID��ֵ������Ҫ������ײ����ݿ���н��� - ��Ԥռһ��ID�����ȱ��������ݿ�Ļ����У�ִ�������ύ�󣬲����չ鵵д�뵽�����У�
		 *  ��Ϊ�Ƕ��µĶ���ִ�г־û������������µĶ�������id������ǰ����ʱ������û��id
		 *  insert 
		    into
		        JPA_CUSTOMERS
		        (age, birth, createTime, email, LAST_NAME) 
		    values
		        (?, ?, ?, ?, ?)
		 */
		Customer customer2 = entityManager.merge(customer);
		System.out.println(entityManager.find(Customer.class, 23));	 //�ڱ��־û��������пɼ�
		
		
		EntityManager entityManager2 = entityManagerFactory.createEntityManager(); //�������־û��������в��ɼ�
		System.out.println(entityManager2.find(Customer.class, 23)); //null
		entityManager2.close();
	}
	
	@Test
	public void testMerge2() {
		
		// �Ѽ�ֵidΪ5�ļ�¼���ص�entityManager������
		Customer cust = entityManager.find(Customer.class, 15);
		
		// ��new��customer���󣨲���entityManager�йܣ�Ϊ����̬����
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("123456");
		customer.setId(15); //���ü�ֵΪ15�������д��ڼ�ֵΪ15�ĳ־û�����
		
		/*
		 *  �Լ�ֵidΪ5������̬����customerִ��merge���̣�
		 *  	1�������em������ά����һ��idֵΪ5��Customer��������em�ж�λ����ֵ��ȵ�Customer���󲢸��������ԡ� - ������̬������ֵ����entityManager�����еĳ־�̬�������ԣ�
		 *  	      �����em������û��ά��һ��idֵΪ5��Customer�������½�һ��Customer���󣨸ö���ļ�ֵ�����ɹ����Զ����ɣ�����ӵ�em�����У����Ƴ���ֵid֮���ֵ���½��Ķ���
		 *  	2��������em�����еĳ־�̬�������Ժ�������ύ������ʱ����޸ĸ��»����ݿ� 
		 *  	ע����em�����г־�̬����ĸ��£�ֱ��ִ��commitʱ�Ż���»����ݿ�
		 */
		
		Customer cust2 = entityManager.merge(customer); 
		
		System.out.println(entityManager.find(Customer.class, 15));	 //�ڱ��־û����������޸Ŀɼ�
		
		
		EntityManager entityManager2 = entityManagerFactory.createEntityManager(); 
		System.out.println(entityManager2.find(Customer.class, 15)); //�������־û����������޸Ĳ��ɼ���Ϊԭֵ
		entityManager2.close();
	
	}
	
	/*
	 * refresh �����ݿ�ʵ���¼��ֵ������ʵ������״̬
	 * clear	����־û������Ļ������Ͽ����Թ�����ʵ�壬�����ʱ����δ�ύ�ĸ��½�������
	 * contains	�ж�һ��ʵ���Ƿ����ڵ�ǰ�־������Ļ�����������
	 * isOpen	�жϵ�ǰ��ʵ��������Ƿ��״̬
	 */
	public void testFlush() {
		
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		System.out.println(entityManager2); //org.hibernate.ejb.EntityManagerImpl@770440dd
		System.out.println(entityManager);	//org.hibernate.ejb.EntityManagerImpl@383eaa36
		System.out.println(entityManager.getFlushMode()); //Ĭ���Զ��ύģʽ
	}
}
