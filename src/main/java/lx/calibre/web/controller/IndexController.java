package lx.calibre.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lx.calibre.entity.Author;
import lx.calibre.entity.Book;
import lx.calibre.entity.BookData;
import lx.calibre.entity.Publisher;
import lx.calibre.entity.Series;
import lx.calibre.entity.Tag;
import lx.calibre.service.AuthorService;
import lx.calibre.service.BookService;
import lx.calibre.service.Page;
import lx.calibre.service.PageQuery;
import lx.calibre.service.PublisherService;
import lx.calibre.service.SeriesService;
import lx.calibre.service.TagService;
import lx.calibre.web.security.KeyStore;
import net.coobird.thumbnailator.Thumbnails;

@Controller
public class IndexController {

	private static final Map<String, String> mimes = new CaseInsensitiveMap<>();

	static {
		mimes.put("mobi", "application/x-mobipocket-ebook");
		mimes.put("epub", "application/epub+zip");
		mimes.put("azw", "application/vnd.amazon.ebook");
		mimes.put("azw3", "application/vnd.amazon.ebook");
		mimes.put("pdf", "application/pdf");
		mimes.put("txt", "text/plain");
	}

	@Value("${calibre.library}")
	private String calibreBase;

	@Value("${calibre.thumb}")
	private String thumbDir;

	@Inject
	private BookService bookService;

	@Inject
	private TagService tagService;

	@Inject
	private AuthorService authorService;

	@Inject
	private PublisherService publisherService;

	@Inject
	private SeriesService seriesService;

	@Inject
	private KeyStore keyStore;

	@GetMapping("")
	public String index(Model model) {
		Collection<Book> books = bookService.findTop12Randoms();
		model.addAttribute("books", books);
		return "index";
	}

	@GetMapping("/login")
	public ModelAndView login(ModelAndView mv) {
		RSAPublicKey rsapk = (RSAPublicKey) keyStore.getKeyPair().getPublic();
		Map<String, String> map = new HashMap<>();
		map.put("m", rsapk.getModulus().toString(16));
		map.put("e", rsapk.getPublicExponent().toString(16));
		mv.setViewName("login");
		mv.addAllObjects(map);
		return mv;
	}

	@GetMapping("/explore/tags")
	public String exploreTags(Model model) {
		Collection<Tag> tags = tagService.findAll();
		model.addAttribute("tags", tags);
		return "explore/tags";
	}

	@GetMapping("/explore/tags/{id}")
	public String exploreTag(@PathVariable("id") Long tagId, PageQuery query, Model model) {
		Tag tag = tagService.findById(tagId);
		Page<Book> books = bookService.findByQuery(query, tag);
		model.addAttribute("tag", tag);
		model.addAttribute("books", books);
		return "explore/tag";
	}

	@GetMapping("/explore/authors")
	public String exploreAuthors(Model model) {
		Collection<Author> authors = authorService.findAll();
		model.addAttribute("authors", authors);
		return "explore/authors";
	}

	@GetMapping("/explore/authors/{id}")
	public String exploreAuthor(@PathVariable("id") Long authorId, PageQuery query, Model model) {
		Author author = authorService.findById(authorId);
		Page<Book> books = bookService.findByQuery(query, author);
		model.addAttribute("author", author);
		model.addAttribute("books", books);
		return "explore/author";
	}

	@GetMapping("/explore/series")
	public String exploreSeries(Model model) {
		Collection<Series> series = seriesService.findAll();
		model.addAttribute("series", series);
		return "explore/series_list";
	}

	@GetMapping("/explore/series/{id}")
	public String exploreSeries(@PathVariable("id") Long seriesId, PageQuery query, Model model) {
		Series series = seriesService.findById(seriesId);
		Page<Book> books = bookService.findByQuery(query, series);
		model.addAttribute("series", series);
		model.addAttribute("books", books);
		return "explore/series";
	}

	@GetMapping("/explore/publishers")
	public String explorePublishers(Model model) {
		Collection<Publisher> publishers = publisherService.findAll();
		model.addAttribute("publishers", publishers);
		return "explore/publishers";
	}

	@GetMapping("/explore/publishers/{id}")
	public String explorePublisher(@PathVariable("id") Long publisherId, PageQuery query, Model model) {
		Publisher publisher = publisherService.findById(publisherId);
		Page<Book> books = bookService.findByQuery(query, publisher);
		model.addAttribute("publisher", publisher);
		model.addAttribute("books", books);
		return "explore/publisher";
	}

	@GetMapping("/books")
	public String explore(PageQuery query, Model model) {
		Page<Book> books = bookService.findByQuery(query);
		model.addAttribute("books", books);
		return "books";
	}

	@GetMapping("/books/{id}")
	public String viewBook(@PathVariable("id") Long bookId, Model model) {
		Book book = bookService.findById(bookId);
		model.addAttribute("book", book);
		return "book";
	}

	@GetMapping("/books/{id}/{format}/{name}")
	public ResponseEntity<?> viewBook(@PathVariable("id") Long bookId,
			@PathVariable("format") String format,
			@PathVariable("name") String name) {
		Book book = bookService.findById(bookId);
		if (book == null)
			return null;
		BookData bookData = null;
		for (BookData d : book.getData()) {
			if (StringUtils.equalsIgnoreCase(d.getFormat(), format)) {
				bookData = d;
				break;
			}
		}
		if (bookData == null)
			return null;
		File file = new File(new File(calibreBase, book.getPath()), bookData.getName() + "." + format);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(file.length());

		String contentType = mimes.get(format);
		headers.setContentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM);
		String fileName = book.getTitle() + "." + format;
		try {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
	}

	@GetMapping("/books/{id}/cover")
	public String getCover(@PathVariable("id") Long bookId) {
		Book book = bookService.findById(bookId);
		if (book == null)
			return null;
		File src = new File(new File(calibreBase, book.getPath()), "cover.jpg");
		File dest = new File(thumbDir, book.getId() + ".jpg");
		if (!dest.exists()) {
			try {
				FileUtils.copyFile(src, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "forward:/static/thumb/" + dest.getName();
	}

	@GetMapping("/books/{id}/thumb/{width:[0-9]+}x{height:[0-9]+}")
	public String getThumb(@PathVariable("id") Long bookId, @PathVariable("width") int width,
			@PathVariable("height") int height) {
		Book book = bookService.findById(bookId);
		if (book == null)
			return null;
		File src = new File(new File(calibreBase, book.getPath()), "cover.jpg");
		File dest = new File(thumbDir, book.getId() + "_" + width + "_" + height + ".jpg");
		if (!dest.exists()) {
			try {
				Thumbnails.of(src).size(width, height).toFile(dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "forward:/static/thumb/" + dest.getName();
	}

}
