package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the students database table.
 * 
 */
@Entity
@Table(name="students")
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String email;
	private String stuName;
	private List<Score> scores;
	private Country county;
	private List<Phone> phones = new ArrayList<>();

	public Student() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="STU_NAME")
	public String getStuName() {
		return this.stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}


	//bi-directional many-to-one association to Score
	@OneToMany(mappedBy="student")
	public List<Score> getScores() {
		return this.scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public Score addScore(Score score) {
		getScores().add(score);
		score.setStudent(this);

		return score;
	}

	public Score removeScore(Score score) {
		getScores().remove(score);
		score.setStudent(null);

		return score;
	}


	//bi-directional many-to-one association to County
	@ManyToOne
	@JoinColumn(name="COUNTRY_ID")
	public Country getCounty() {
		return this.county;
	}

	public void setCounty(Country county) {
		this.county = county;
	}


	//bi-directional many-to-one association to Phone
	@OneToMany(mappedBy="student")
	public List<Phone> getPhones() {
		return this.phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Phone addPhone(Phone phone) {
		getPhones().add(phone);
		phone.setStudent(this);

		return phone;
	}

	public Phone removePhone(Phone phone) {
		getPhones().remove(phone);
		phone.setStudent(null);

		return phone;
	}

}