package lx.calibre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lx.calibre.entity.Tag;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

	@Query(value = "select * from tag_browser_tags", nativeQuery = true)
	List<Object[]> findAllData();

}
