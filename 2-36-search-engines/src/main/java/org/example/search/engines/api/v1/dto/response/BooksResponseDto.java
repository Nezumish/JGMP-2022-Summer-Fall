package org.example.search.engines.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The total result of the search
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BooksResponseDto {

    private List<BookResponseDto> books;

    private List<FacetResponseDto> facets;

    private Integer numFound;

}
