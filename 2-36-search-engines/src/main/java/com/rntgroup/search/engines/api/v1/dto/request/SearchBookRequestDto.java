package com.rntgroup.search.engines.api.v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Books searching criteria DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SearchBookRequestDto {

    private String field;

    private String value;

    private String facetField;

    private boolean fulltext;

    private String query;

}
