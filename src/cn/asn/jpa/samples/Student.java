package cn.asn.jpa.samples;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the student database table.
 * 
 */

@Table(name="STUDENTS")
@Entity
public class Student {

	private int id;
	private String stuName;	
	private String email;
	
	private Set<Phone> phones = new HashSet<>();

	
	public Student() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="STU_NAME")
	public String getStuName() {
		return this.stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	@Column(name="EMAIL")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	@JoinColumn(name="STU_ID")
	@OneToMany(cascade={CascadeType.REMOVE})
	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}
	
}