package lx.calibre.service;

import java.util.Collection;

import lx.calibre.entity.Tag;

public interface TagService {

	Collection<Tag> findAll();

	Tag findById(Long id);

}
