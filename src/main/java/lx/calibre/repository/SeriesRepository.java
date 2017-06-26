package lx.calibre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lx.calibre.entity.Series;

@Repository
public interface SeriesRepository extends PagingAndSortingRepository<Series, Long> {

	@Query(value = "SELECT id, name,"
			+ " (SELECT COUNT(id) FROM books_series_link WHERE series=series.id) count,"
			+ " (SELECT AVG(ratings.rating)"
			+ " FROM books_series_link AS tl, books_ratings_link AS bl, ratings"
			+ " WHERE tl.series=series.id AND bl.book=tl.book"
			+ " AND ratings.id = bl.rating AND ratings.rating <> 0) avg_rating,"
			+ " name AS sort"
			+ " FROM series",
			nativeQuery = true)
	List<Object[]> findAllData();

}
