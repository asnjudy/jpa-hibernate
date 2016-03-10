package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the scores database table.
 * 
 */
@Entity
@Table(name="scores")
@NamedQuery(name="Score.findAll", query="SELECT s FROM Score s")
public class Score implements Serializable {
	private static final long serialVersionUID = 1L;
	private ScorePK id;
	private int score;
	private Course course;
	private Student student;

	public Score() {
	}


	@EmbeddedId
	public ScorePK getId() {
		return this.id;
	}

	public void setId(ScorePK id) {
		this.id = id;
	}


	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}


	//bi-directional many-to-one association to Course
	@ManyToOne
	@JoinColumn(name="COURSE_ID", insertable=false, updatable=false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="STU_ID", insertable=false, updatable=false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}