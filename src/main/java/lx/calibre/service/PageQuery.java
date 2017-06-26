package lx.calibre.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageQuery {

	public static final Pattern ALLOW_QUERY = Pattern.compile("[\\w\\s-&\u4e00-\u9fa5]*");

	private int p = Page.DEFAULT_PAGE;
	private int n = Page.DEFAULT_PAGE_SIZE;
	private String q;
	private String sort;
	private Boolean asc;
	private String view;

	public Pageable toPageable(Sort sort) {
		return new PageRequest(getP() - 1, getN(), sort);
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = StringUtils.trimToNull(q);
	}

	public Collection<String> getKeywords() {
		if (q == null)
			return Collections.emptyList();
		String[] qs = StringUtils.split(q);
		List<String> keywords = new ArrayList<String>(qs.length);
		for (String s : qs) {
			if (!ALLOW_QUERY.matcher(s).matches())
				continue;
			keywords.add(s);
		}
		return keywords;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = StringUtils.trimToNull(sort);
	}

	public Boolean getAsc() {
		return asc;
	}

	public boolean isAsc() {
		return asc != null && asc.booleanValue();
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = StringUtils.trimToNull(view);
	}

}
