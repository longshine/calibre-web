package lx.calibre.service.impl;

import static lx.calibre.util.ConvertUtils.toDouble;
import static lx.calibre.util.ConvertUtils.toLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lx.calibre.entity.Author;
import lx.calibre.repository.AuthorRepository;
import lx.calibre.service.AuthorService;
import lx.calibre.util.ConvertUtils;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Inject
	private AuthorRepository authorRepository;

	@Override
	public Collection<Author> findAll() {
		List<Object[]> result = authorRepository.findAllData();
		List<Author> authors = new ArrayList<>();
		for (Object[] objs : result) {
			authors.add(new Author(toLong(objs[0]), ConvertUtils.toString(objs[1]), toLong(objs[2]), toDouble(objs[3])));
		}
		return authors;
	}

	@Override
	public Author findById(Long id) {
		return authorRepository.findOne(id);
	}

}
