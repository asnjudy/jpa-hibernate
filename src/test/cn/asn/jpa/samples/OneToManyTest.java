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
		 *  Student相对Country是外表（Student有一个外键约束列指向Country的主键id）
		 *  而在Student对象中没有维护到Country对象的属性，即从Student到不了Country
		 *  所以在执行 entityManager.persist(student);
		 *      insert 
			    into
			        STUDENTS
			        (EMAIL, STU_NAME) 
			    values
			        (?, ?)
			未设置外键约束列的值，需附加一个update:
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
		
		//先持久化主表记录，再持久化外表记录
		entityManager.persist(country);
		entityManager.persist(student);
		entityManager.persist(phone);
		entityManager.persist(phone2);
	}

}
