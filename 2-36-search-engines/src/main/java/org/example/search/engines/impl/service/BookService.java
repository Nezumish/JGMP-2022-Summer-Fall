package org.example.search.engines.impl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.example.search.engines.api.v1.dto.request.SearchBookRequestDto;
import org.example.search.engines.api.v1.dto.response.BookDto;
import org.example.search.engines.api.v1.dto.response.BooksDto;
import org.example.search.engines.api.v1.dto.response.FacetDto;
import org.example.search.engines.api.v1.dto.response.SuggestionResponseDto;
import org.example.search.engines.impl.model.SolrBook;
import org.example.search.engines.impl.repository.SolrBookRepository;
import org.example.search.engines.impl.utils.EpubBookUtils;
import org.example.search.engines.impl.utils.SolrBookQueryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Books Resource service
 */
@Service
public class BookService {

    private static final String SUGGESTIONS_HANDLER = "/" + SolrBookRepository.COLLECTION + "/suggest";
    private static final String DEFAULT_SUGGESTER_NAME = "default";

    public static final String EPUB = "epub";

    private final String booksPath;

    private final EpubReader epubReader;
    private final SolrTemplate solrTemplate;
    private final SolrBookRepository solrBookRepository;
    private final ObjectMapper objectConverter;

    public BookService(@Value("${books.path}") String booksPath,
                       EpubReader epubReader,
                       SolrTemplate solrTemplate,
                       SolrBookRepository solrBookRepository,
                       ObjectMapper objectConverter) {

        this.booksPath = booksPath;
        this.epubReader = epubReader;
        this.solrTemplate = solrTemplate;
        this.solrBookRepository = solrBookRepository;
        this.objectConverter = objectConverter;
    }

    /**
     * Maps all found .epub files into Solr Books and saves them
     */
    @SneakyThrows
    public void updateSolrLibraryFromEpubs() {
        Path booksDirectory = Paths.get(booksPath);
        Files.walk(booksDirectory)
                .filter(path -> hasExtension(path, EPUB))
                .map(this::readEpub)
                .map(this::toSolrBook)
                .forEach(solrBookRepository::save);
    }

    /**
     * Checks the extension of the file by the given path
     */
    private boolean hasExtension(Path path, String extensionName) {
        return extensionName.equals(FilenameUtils.getExtension(path.toString()));
    }

    /**
     * @return read .epub as {@link Book}
     */
    @SneakyThrows
    private Book readEpub(Path path) {
        return epubReader.readEpub(FileUtils.openInputStream(path.toFile()));
    }


    /**
     * Maps {@link Book} to {@link SolrBook}
     *
     * @param book - mapping book
     * @return mapped Solr Book
     */
    @SneakyThrows
    public SolrBook toSolrBook(Book book) {
        String authors = StringUtils.join(EpubBookUtils.getAuthors(book), ", ");
        String fullTitle = authors + ", " + book.getTitle();
        return SolrBook.builder()
                .title(book.getTitle())
                .fullTitle(fullTitle)
                .authors(EpubBookUtils.getAuthors(book))
                .content(EpubBookUtils.readContent(book))
                .language(book.getMetadata().getLanguage())
                .build();
    }

    /**
     * @param bookId - Solr book id
     * @return {@link BookDto} by its Solr id
     */
    public Optional<BookDto> findBookById(String bookId) {
        Optional<SolrBook> solrBookOpt = solrBookRepository.findById(bookId);

        Optional<BookDto> bookDtoOpt;
        if (solrBookOpt.isPresent()) {
            SolrBook solrBook = solrBookOpt.get();
            BookDto bookDto = objectConverter.convertValue(solrBook, BookDto.class);

            bookDtoOpt = Optional.of(bookDto);
        } else {
            bookDtoOpt = Optional.empty();
        }

        return bookDtoOpt;
    }

    /**
     * @inheritDoc
     */
    @SneakyThrows
    public List<SuggestionResponseDto> suggestNextBookTokens(String suggestionsQuery) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler(SUGGESTIONS_HANDLER);
        solrQuery.setQuery(suggestionsQuery);

        QueryResponse solrQueryResponse = solrTemplate.getSolrClient().query(solrQuery);
        SuggesterResponse suggesterResponse = solrQueryResponse.getSuggesterResponse();

        return suggesterResponse.getSuggestedTerms().get(DEFAULT_SUGGESTER_NAME).stream()
                .map(SuggestionResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Searches books by the given criteria
     *
     * @param searchRequestDto - the search criteria DTO
     * @return found matching books
     */
    public BooksDto searchBooks(SearchBookRequestDto searchRequestDto) {
        var booksDtoBuilder = BooksDto.builder();

        Criteria criteria = SolrBookQueryUtils.extractCriteria(searchRequestDto).orElseThrow(
                () -> new IllegalArgumentException("Request has no searching parameters")
        );
        Query query = SolrBookQueryUtils.qualifyBaseQuery(searchRequestDto);
        query.addCriteria(criteria);

        Page<SolrBook> solrResultPage;
        if (SolrBookQueryUtils.canBeFacetSearched(searchRequestDto)) {
            solrResultPage = solrTemplate.queryForFacetPage(SolrBookRepository.COLLECTION, (FacetQuery) query, SolrBook.class);
            booksDtoBuilder.facets(extractFacets((FacetPage<SolrBook>) solrResultPage));
        } else {
            solrResultPage = solrTemplate.queryForPage(SolrBookRepository.COLLECTION, query, SolrBook.class);
        }

        booksDtoBuilder
                .books(extractBooks(solrResultPage))
                .numFound(getCount(solrResultPage));

        return booksDtoBuilder.build();
    }

    /**
     * Extracts the found Facet Fields and maps them into the {@link FacetDto}
     *
     * @param solrResultPage - the result of the Facet Solr query
     * @return list of the mapped found Solr Facets
     */
    private List<FacetDto> extractFacets(FacetPage<SolrBook> solrResultPage) {
        return solrResultPage.getFacetResultPages().stream()
                .flatMap(Streamable::get)
                .map(facetField -> objectConverter.convertValue(facetField, FacetDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Extracts the found Solr Documents and maps them into the {@link BookDto}
     *
     * @param solrResultPage - the result of the Solr query
     * @return list of the mapped found Solr Book documents
     */
    private List<BookDto> extractBooks(Page<SolrBook> solrResultPage) {
        return solrResultPage.get()
                .map(solrBook -> objectConverter.convertValue(solrBook, BookDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Extracts count of the found documents from the result
     */
    private int getCount(Page<SolrBook> solrResultPage) {
        return solrResultPage.getNumberOfElements();
    }

    /**
     * Deletes all documents from the Solr books collection
     */
    public void clearBooks() {
        solrBookRepository.deleteAll();
    }

}
