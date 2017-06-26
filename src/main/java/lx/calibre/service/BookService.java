package lx.calibre.service;

import java.util.Collection;

import lx.calibre.entity.Author;
import lx.calibre.entity.Book;
import lx.calibre.entity.Publisher;
import lx.calibre.entity.Series;
import lx.calibre.entity.Tag;

public interface BookService {

	Collection<Book> findTop12Randoms();

	Page<Book> findByQuery(PageQuery query);

	Page<Book> findByQuery(PageQuery query, Tag tag);

	Page<Book> findByQuery(PageQuery query, Author author);

	Page<Book> findByQuery(PageQuery query, Publisher publisher);

	Page<Book> findByQuery(PageQuery query, Series series);

	Book findById(Long id);

}
