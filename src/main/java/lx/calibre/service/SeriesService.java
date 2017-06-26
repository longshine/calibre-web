package lx.calibre.service;

import java.util.Collection;

import lx.calibre.entity.Series;

public interface SeriesService {

	Collection<Series> findAll();

	Series findById(Long id);

}
