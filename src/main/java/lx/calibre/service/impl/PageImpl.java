package lx.calibre.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageImpl<T> implements lx.calibre.service.Page<T>, Iterable<T> {

	private final Page<T> page;

	public PageImpl(Page<T> page) {
		this.page = page;
	}

	@Override
	public int getTotalPages() {
		return page.getTotalPages();
	}

	@Override
	public long getTotalElements() {
		return page.getTotalElements();
	}

	@Override
	public int getNumber() {
		return page.getNumber() + 1;
	}

	@Override
	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}

	@Override
	public List<T> getContent() {
		return page.getContent();
	}

	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}

}
