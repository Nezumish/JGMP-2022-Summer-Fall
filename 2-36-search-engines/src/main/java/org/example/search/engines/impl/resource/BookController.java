package org.example.search.engines.impl.resource;

import lombok.RequiredArgsConstructor;

import org.example.search.engines.api.v1.BookResourceApi;
import org.example.search.engines.api.v1.dto.request.SearchBookRequestDto;
import org.example.search.engines.api.v1.dto.response.BookDto;
import org.example.search.engines.api.v1.dto.response.BooksDto;
import org.example.search.engines.api.v1.dto.response.SuggestionResponseDto;
import org.example.search.engines.impl.exception.NoBookException;
import org.example.search.engines.impl.service.BookService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The implementation of {@link BookResourceApi}
 */
@RestController
@RequiredArgsConstructor
public class BookController implements BookResourceApi {

    private final BookService bookService;

    @Override
    public BookDto getBookById(String bookId) {
        return bookService.findBookById(bookId)
                .orElseThrow(() -> new NoBookException(bookId));
    }

    @Override
    public List<SuggestionResponseDto> suggestBooks(String query) {
        return bookService.suggestNextBookTokens(query);
    }

    @Override
    public BooksDto searchBooksLike(SearchBookRequestDto searchBookRequestDto) {
        return bookService.searchBooks(searchBookRequestDto);
    }

    @Override
    public void updateLibrary() {
        bookService.updateSolrLibraryFromEpubs();
    }

    @Override
    public void clearLibrary() {
        bookService.clearBooks();
    }

}
