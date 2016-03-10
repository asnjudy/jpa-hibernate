package cn.asn.jpa.samples;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Table(name="ORDERS")
@Entity
public class Order {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="ORDER_NAME")
	private String orderName;
	
	@JoinColumn(name="CUSTOMER_ID")
	@ManyToOne
	private Customer customer;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
