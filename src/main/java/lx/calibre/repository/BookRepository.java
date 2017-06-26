package lx.calibre.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lx.calibre.entity.Author;
import lx.calibre.entity.Book;
import lx.calibre.entity.Publisher;
import lx.calibre.entity.Series;
import lx.calibre.entity.Tag;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	Page<Book> findByTags(Tag tag, Pageable pageable);

	Page<Book> findByAuthors(Author author, Pageable pageable);

	Page<Book> findByPublishers(Publisher publisher, Pageable pageable);

	Page<Book> findBySeries(Series series, Pageable pageable);

	@Query(value = "select * from books order by random() limit 12", nativeQuery = true)
	Collection<Book> findTop12Randoms();

}
