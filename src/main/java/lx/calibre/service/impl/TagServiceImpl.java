package lx.calibre.service.impl;

import static lx.calibre.util.ConvertUtils.toDouble;
import static lx.calibre.util.ConvertUtils.toLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lx.calibre.entity.Tag;
import lx.calibre.repository.TagRepository;
import lx.calibre.service.TagService;
import lx.calibre.util.ConvertUtils;

@Service
public class TagServiceImpl implements TagService {

	@Inject
	private TagRepository tagRepository;

	@Override
	public Collection<Tag> findAll() {
		List<Object[]> result = tagRepository.findAllData();
		List<Tag> tags = new ArrayList<>();
		for (Object[] objs : result) {
			tags.add(new Tag(toLong(objs[0]), ConvertUtils.toString(objs[1]), toLong(objs[2]), toDouble(objs[3])));
		}
		return tags;
	}

	@Override
	public Tag findById(Long id) {
		return tagRepository.findOne(id);
	}

}
