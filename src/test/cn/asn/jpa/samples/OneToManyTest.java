package test.cn.asn.jpa.samples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.asn.jpa.samples.Country;
import cn.asn.jpa.samples.Course;
import cn.asn.jpa.samples.Phone;
import cn.asn.jpa.samples.Student;

public class OneToManyTest {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;
	
	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-2");
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destory() {
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	@Test
	public void testOneToManyPersist() {

		
		Phone phone = new Phone("192912233", "yidong");
		Phone phone2 = new Phone("192912233", "yidong");
		
		/*
		 *  Student���Country�����Student��һ�����Լ����ָ��Country������id��
		 *  ����Student������û��ά����Country��������ԣ�����Student������Country
		 *  ������ִ�� entityManager.persist(student);
		 *      insert 
			    into
			        STUDENTS
			        (EMAIL, STU_NAME) 
			    values
			        (?, ?)
			δ�������Լ���е�ֵ���踽��һ��update:
			update
		        STUDENTS 
		    set
		        COUNTRY_ID=? 
		    where
		        id=?	
		 */
		Student student = new Student();
		student.setStuName("aa");
		student.setEmail("aa@163.com");
		student.getPhones().add(phone);
		student.getPhones().add(phone2);
		
		Country country = new Country();
		country.setName("China");
		country.getStudents().add(student);
		
		//�ȳ־û������¼���ٳ־û�����¼
		entityManager.persist(country);
		entityManager.persist(student);
		entityManager.persist(phone);
		entityManager.persist(phone2);
	}

}
