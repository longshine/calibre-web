package lx.calibre.service;

import java.util.Collection;

import lx.calibre.entity.Author;

public interface AuthorService {

	Collection<Author> findAll();

	Author findById(Long id);

}
