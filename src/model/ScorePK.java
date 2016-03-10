package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the scores database table.
 * 
 */
@Embeddable
public class ScorePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private int stuId;
	private int courseId;

	public ScorePK() {
	}

	@Column(name="STU_ID", insertable=false, updatable=false)
	public int getStuId() {
		return this.stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	@Column(name="COURSE_ID", insertable=false, updatable=false)
	public int getCourseId() {
		return this.courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ScorePK)) {
			return false;
		}
		ScorePK castOther = (ScorePK)other;
		return 
			(this.stuId == castOther.stuId)
			&& (this.courseId == castOther.courseId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.stuId;
		hash = hash * prime + this.courseId;
		
		return hash;
	}
}