package org.example.search.engines.impl.resource;

import lombok.RequiredArgsConstructor;

import org.example.search.engines.api.v1.BookResource;
import org.example.search.engines.api.v1.dto.request.SearchBookRequestDto;
import org.example.search.engines.api.v1.dto.response.BookResponseDto;
import org.example.search.engines.api.v1.dto.response.BooksResponseDto;
import org.example.search.engines.api.v1.dto.response.SuggestionResponseDto;
import org.example.search.engines.impl.exception.NoBookException;
import org.example.search.engines.impl.service.BookService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The implementation of {@link BookResource}
 */
@RestController
@RequiredArgsConstructor
public class BookResourceImpl implements BookResource {

    private final BookService bookService;

    @Override
    public BookResponseDto getBookById(String bookId) {
        return bookService.findBookById(bookId)
                .orElseThrow(() -> new NoBookException(bookId));
    }

    @Override
    public List<SuggestionResponseDto> suggestBooks(String query) {
        return bookService.suggestNextBookTokens(query);
    }

    @Override
    public BooksResponseDto searchBooksLike(SearchBookRequestDto searchBookRequestDto) {
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
