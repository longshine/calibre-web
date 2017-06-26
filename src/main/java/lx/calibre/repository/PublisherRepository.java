package lx.calibre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lx.calibre.entity.Publisher;

@Repository
public interface PublisherRepository extends PagingAndSortingRepository<Publisher, Long> {

	@Query(value = "select * from tag_browser_publishers", nativeQuery = true)
	List<Object[]> findAllData();

}
