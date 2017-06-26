package lx.calibre.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String title;

	@Column
	private int seriesIndex;

	@Column
	private String sort;

	@Column
	private String authorSort;

	@Column
	private String path;

	@Column
	private boolean hasCover;

	@Column
	private String pubdate;

	@Column(name ="timestamp")
	private String time;

	@OneToOne(mappedBy = "book")
	private Comment comment;

	@ManyToMany
	@JoinTable(name = "books_authors_link", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns = @JoinColumn(name = "author"))
	private Collection<Author> authors;

	@ManyToMany
	@JoinTable(name = "books_tags_link", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns = @JoinColumn(name = "tag"))
	private Collection<Tag> tags;

	@ManyToMany
	@JoinTable(name = "books_publishers_link", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns = @JoinColumn(name = "publisher"))
	private Collection<Publisher> publishers;

	@ManyToMany
	@JoinTable(name = "books_series_link", joinColumns = @JoinColumn(name = "book"), inverseJoinColumns = @JoinColumn(name = "series"))
	private Collection<Series> series;

	@OneToMany(mappedBy = "book")
	private Collection<BookData> data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<Author> authors) {
		this.authors = authors;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

	public int getSeriesIndex() {
		return seriesIndex;
	}

	public void setSeriesIndex(int seriesIndex) {
		this.seriesIndex = seriesIndex;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAuthorSort() {
		return authorSort;
	}

	public void setAuthorSort(String authorSort) {
		this.authorSort = authorSort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isHasCover() {
		return hasCover;
	}

	public void setHasCover(boolean hasCover) {
		this.hasCover = hasCover;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Collection<BookData> getData() {
		return data;
	}

	public void setData(Collection<BookData> data) {
		this.data = data;
	}

	public Collection<Publisher> getPublishers() {
		return publishers;
	}

	public void setPublishers(Collection<Publisher> publishers) {
		this.publishers = publishers;
	}

	public Collection<Series> getSeries() {
		return series;
	}

	public void setSeries(Collection<Series> series) {
		this.series = series;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
