package lx.calibre.service.impl;

import static lx.calibre.util.ConvertUtils.toDouble;
import static lx.calibre.util.ConvertUtils.toLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lx.calibre.entity.Publisher;
import lx.calibre.repository.PublisherRepository;
import lx.calibre.service.PublisherService;
import lx.calibre.util.ConvertUtils;

@Service
public class PublisherServiceImpl implements PublisherService {

	@Inject
	private PublisherRepository publisherRepository;

	@Override
	public Collection<Publisher> findAll() {
		List<Object[]> result = publisherRepository.findAllData();
		List<Publisher> pubs = new ArrayList<>();
		for (Object[] objs : result) {
			pubs.add(new Publisher(toLong(objs[0]), ConvertUtils.toString(objs[1]), toLong(objs[2]), toDouble(objs[3])));
		}
		return pubs;
	}

	@Override
	public Publisher findById(Long id) {
		return publisherRepository.findOne(id);
	}

}
