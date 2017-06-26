package lx.calibre.service;

import java.util.List;

public interface Page<T> extends Iterable<T> {

	static final String PAGE_PARAM = "p";
	static final String SIZE_PARAM = "n";
	static final int DEFAULT_PAGE = 1;
	static final String DEFAULT_PAGE_STRING = "1";
	static final int DEFAULT_PAGE_SIZE = 12;
	static final String DEFAULT_PAGE_SIZE_STRING = "12";

	int getTotalPages();

	long getTotalElements();

	int getNumber();

	int getNumberOfElements();

	List<T> getContent();

}
