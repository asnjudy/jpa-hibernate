package cn.asn.jpa.samples;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name="ITEMS")
@Entity
public class Item {
	
	
	private Integer id;
	private String itemName;
	
	private Set<Category> categories = new HashSet<>();

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ITEM_NAME")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@JoinTable(
			name="ITEM_CATEGORY",
			joinColumns={@JoinColumn(name="ITEM_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID")})
	@ManyToMany
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
}
