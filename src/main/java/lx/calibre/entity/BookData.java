package lx.calibre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "data")
public class BookData {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "book")
	private Book book;

	@Column
	private String format;

	@Column
	private long uncompressedSize;

	@Column
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public long getUncompressedSize() {
		return uncompressedSize;
	}

	public void setUncompressedSize(long uncompressedSize) {
		this.uncompressedSize = uncompressedSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
