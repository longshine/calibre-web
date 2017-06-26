package lx.calibre.service;

import java.util.Collection;

import lx.calibre.entity.Publisher;

public interface PublisherService {

	Collection<Publisher> findAll();

	Publisher findById(Long id);

}
