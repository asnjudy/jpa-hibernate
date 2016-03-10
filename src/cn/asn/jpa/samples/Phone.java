package cn.asn.jpa.samples;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="PHONES")
@Entity
public class Phone {
	
	private Integer id;
	private String number;
	private String provider;
	
	public Phone() {
		// TODO Auto-generated constructor stub
	}
	public Phone(String number, String provider) {
		super();
		this.number = number;
		this.provider = provider;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="P_NUMBER")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column(name="P_PROVIDER")
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
