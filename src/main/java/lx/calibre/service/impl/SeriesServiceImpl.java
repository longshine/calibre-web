package lx.calibre.service.impl;

import static lx.calibre.util.ConvertUtils.toDouble;
import static lx.calibre.util.ConvertUtils.toLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lx.calibre.entity.Series;
import lx.calibre.repository.SeriesRepository;
import lx.calibre.service.SeriesService;
import lx.calibre.util.ConvertUtils;

@Service
public class SeriesServiceImpl implements SeriesService {

	@Inject
	private SeriesRepository seriesRepository;

	@Override
	public Collection<Series> findAll() {
		List<Object[]> result = seriesRepository.findAllData();
		List<Series> series = new ArrayList<>();
		for (Object[] objs : result) {
			series.add(new Series(toLong(objs[0]), ConvertUtils.toString(objs[1]), toLong(objs[2]), toDouble(objs[3])));
		}
		return series;
	}

	@Override
	public Series findById(Long id) {
		return seriesRepository.findOne(id);
	}

}
