package org.example.search.engines.api.v1;

import org.example.search.engines.api.ApiVersions;

import org.example.search.engines.api.v1.dto.request.SearchBookRequestDto;
import org.example.search.engines.api.v1.dto.response.BookDto;
import org.example.search.engines.api.v1.dto.response.BooksDto;

import org.example.search.engines.api.v1.dto.response.SuggestionResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The Api of the Book resource
 */
@RequestMapping("/api/" + ApiVersions.DEFAULT + "/books")
public interface BookResourceApi {

    /**
     * @param bookId - book id
     * @return {@link BookDto} by its id
     */
    @GetMapping("/{bookId}")
    BookDto getBookById(@PathVariable("bookId") String bookId);

    /**
     * @param query - query to define similar titles of the books
     * @return suggested token combinations to continue searching
     */
    @GetMapping("/suggest")
    List<SuggestionResponseDto> suggestBooks(@RequestParam("query") String query);

    /**
     * @param searchBookRequestDto - searching criteria DTO
     * @return results matching the given searching criteria
     */
    @GetMapping
    BooksDto searchBooksLike(@RequestBody SearchBookRequestDto searchBookRequestDto);

    /**
     * Updates the library
     */
    @PostMapping
    void updateLibrary();

    /**
     * Clears the library
     */
    @DeleteMapping
    void clearLibrary();

}
