package test.cn.asn.jpa.samples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.asn.jpa.samples.Category;
import cn.asn.jpa.samples.Item;
import model.Course;
import model.Score;
import model.ScorePK;
import model.Student;

public class ManyToManyTest {

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
	
	
	
	public void testManyToManyPersistScore() {
		
		/*Phone phone = new Phone("192912233", "yidong");
		Phone phone2 = new Phone("192912233", "yidong");
		
		Student student = new Student();
		student.setStuName("aa");
		student.setEmail("aa@163.com");
		student.getPhones().add(phone);
		student.getPhones().add(phone2);
		
		Country country = new Country();
		country.setCountryName("China");
		country.getStudents().add(student);
		
		//先持久化主表记录，再持久化外表记录
		entityManager.persist(country);
		entityManager.persist(student);
		entityManager.persist(phone);
		entityManager.persist(phone2);*/
		
		Course course = entityManager.find(Course.class, 2);
		Student student = entityManager.find(Student.class, 1);
		
		Score score = new Score();
		score.setCourse(course);
		score.setStudent(student);
		score.setScore(70);
		
		
		ScorePK pk = new ScorePK(); //组合主键
		pk.setCourseId(course.getId());
		pk.setStuId(student.getId());
		score.setId(pk);
		course.getScores().add(score);
		
		entityManager.persist(score);
	}
	
	
	//多对多的保存
	@Test
	public void testManyToManyPersistItemCategory() {
		Item item = new Item();
		item.setItemName("i-1");
		Item item2 = new Item();
		item2.setItemName("i-2");
		
		Category category = new Category();
		category.setCategoryName("c-1");
		Category category2 = new Category();
		category2.setCategoryName("c-2");
		
		// 设置关联关系
		item.getCategories().add(category);
		item.getCategories().add(category2);
		
		item2.getCategories().add(category);
		item2.getCategories().add(category2);
		
		category.getItems().add(item);
		category.getItems().add(item2);
		
		category2.getItems().add(item);
		category2.getItems().add(item2);
		
		entityManager.persist(item);
		entityManager.persist(item2);
		entityManager.persist(category);
		entityManager.persist(category2);
	}
	
	@Test
	public void testManyToManyFind() {
		
//		Item item = entityManager.find(Item.class, 2);
//		System.out.println(item.getItemName());
//		System.out.println(item.getCategories());
		
		Category category = entityManager.find(Category.class, 1);
		System.out.println(category);
		System.out.println(category.getItems());
		
	}
}
