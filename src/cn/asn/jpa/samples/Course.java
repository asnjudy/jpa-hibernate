package cn.asn.jpa.samples;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import sun.text.resources.BreakIteratorInfo_th;

/**
 * Entity implementation class for Entity: Course
 *
 */



@Table(name="JPA_COURSES")
@Entity
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String courseName;
	

	public Course() {
		super();
	}  
		
	@Column(name="COURSE_ID") //Ó³ÉäÁÐÃû
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Id //Ó³ÉäÖ÷¼ü
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="COURSE_NAME")
	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	
	
	@Transient
	public String getInfo() {
		return "id: " + id + ", courseName: " + courseName;
	}
   
}
