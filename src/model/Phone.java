package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the phones database table.
 * 
 */
@Entity
@Table(name="phones")
@NamedQuery(name="Phone.findAll", query="SELECT p FROM Phone p")
public class Phone implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String pNumber;
	private String pProvider;
	private Student student;

	public Phone() {
	}
	
	public Phone(String pNumber, String pProvider) {
		this.pNumber = pNumber;
		this.pProvider = pProvider;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="P_NUMBER")
	public String getPNumber() {
		return this.pNumber;
	}

	public void setPNumber(String pNumber) {
		this.pNumber = pNumber;
	}


	@Column(name="P_PROVIDER")
	public String getPProvider() {
		return this.pProvider;
	}

	public void setPProvider(String pProvider) {
		this.pProvider = pProvider;
	}


	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="STU_ID")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}