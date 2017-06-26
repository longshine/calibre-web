package lx.calibre.service.impl;

import static lx.calibre.repository.Specifications.like;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import lx.calibre.entity.Author;
import lx.calibre.entity.Author_;
import lx.calibre.entity.Book;
import lx.calibre.entity.Book_;
import lx.calibre.entity.Publisher;
import lx.calibre.entity.Publisher_;
import lx.calibre.entity.Series;
import lx.calibre.entity.Series_;
import lx.calibre.entity.Tag;
import lx.calibre.entity.Tag_;
import lx.calibre.repository.BookRepository;
import lx.calibre.service.BookService;
import lx.calibre.service.Page;
import lx.calibre.service.PageQuery;

@Service
public class BookServiceImpl implements BookService {

	@Inject
	private BookRepository bookRepository;

	@Override
	public Collection<Book> findTop12Randoms() {
		return bookRepository.findTop12Randoms();
	}

	@Override
	public Page<Book> findByQuery(PageQuery query) {
		Collection<String> keywords = query.getKeywords();
		if (keywords == null || keywords.isEmpty()) {
			return new PageImpl<>(bookRepository.findAll(toPageable(query)));
		} else {
			Specification<Book> spec = like(Book_.title, keywords);
			return new PageImpl<>(bookRepository.findAll(spec, toPageable(query)));
		}
	}

	@Override
	public Page<Book> findByQuery(PageQuery query, Tag tag) {
		Collection<String> keywords = query.getKeywords();
		if (keywords == null || keywords.isEmpty()) {
			return new PageImpl<>(bookRepository.findByTags(tag, toPageable(query)));
		} else {
			Specifications<Book> spec = where(joinIs(Book_.tags, Tag_.id, tag.getId()));
			spec = spec.and(like(Book_.title, keywords));
			return new PageImpl<>(bookRepository.findAll(spec, toPageable(query)));
		}
	}

	@Override
	public Page<Book> findByQuery(PageQuery query, Author author) {
		Collection<String> keywords = query.getKeywords();
		if (keywords == null || keywords.isEmpty()) {
			return new PageImpl<>(bookRepository.findByAuthors(author, toPageable(query)));
		} else {
			Specifications<Book> spec = where(joinIs(Book_.authors, Author_.id, author.getId()));
			spec = spec.and(like(Book_.title, keywords));
			return new PageImpl<>(bookRepository.findAll(spec, toPageable(query)));
		}
	}

	@Override
	public Page<Book> findByQuery(PageQuery query, Publisher publisher) {
		Collection<String> keywords = query.getKeywords();
		if (keywords == null || keywords.isEmpty()) {
			return new PageImpl<>(bookRepository.findByPublishers(publisher, toPageable(query)));
		} else {
			Specifications<Book> spec = where(joinIs(Book_.publishers, Publisher_.id, publisher.getId()));
			spec = spec.and(like(Book_.title, keywords));
			return new PageImpl<>(bookRepository.findAll(spec, toPageable(query)));
		}
	}

	@Override
	public Page<Book> findByQuery(PageQuery query, Series series) {
		Collection<String> keywords = query.getKeywords();
		if (keywords == null || keywords.isEmpty()) {
			return new PageImpl<>(bookRepository.findBySeries(series, toPageable(query)));
		} else {
			Specifications<Book> spec = where(joinIs(Book_.series, Series_.id, series.getId()));
			spec = spec.and(like(Book_.title, keywords));
			return new PageImpl<>(bookRepository.findAll(spec, toPageable(query)));
		}
	}

	@Override
	public Book findById(Long id) {
		return bookRepository.findOne(id);
	}

	private static Pageable toPageable(PageQuery query) {
		String sort = query.getSort();
		if (sort != null) {
			if ("pubdate".equalsIgnoreCase(sort))
				sort = "pubdate";
			else if ("title".equalsIgnoreCase(sort))
				sort = "title";
			else
				sort = null;
		}
		if (sort == null)
			sort = "id";
		return query.toPageable(new Sort(query.isAsc() ? Direction.ASC : Direction.DESC, sort));
	}

	private static <TSrc, TDest, TField> Specification<TSrc> joinIs(
			final CollectionAttribute<? super TSrc, TDest> joinAtribute,
			final SingularAttribute<? super TDest, TField> attribute,
			final TField value) {
		return new Specification<TSrc>() {

			@Override
			public Predicate toPredicate(Root<TSrc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.join(joinAtribute).get(attribute), value);
			}

		};
	}

}
