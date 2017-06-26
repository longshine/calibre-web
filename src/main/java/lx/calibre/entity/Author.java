package lx.calibre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "authors")
public class Author {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Transient
	private long count;

	@Transient
	private Double rating;

	public Author() { }

	public Author(Long id, String name, Long count, Double rating) {
		this.id = id;
		this.name = name;
		this.count = count;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
