package lx.calibre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lx.calibre.entity.Author;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

	@Query(value = "select * from tag_browser_authors", nativeQuery = true)
	List<Object[]> findAllData();

}
