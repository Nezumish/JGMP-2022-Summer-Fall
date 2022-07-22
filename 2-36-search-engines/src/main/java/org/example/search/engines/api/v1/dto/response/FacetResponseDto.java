package org.example.search.engines.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Found facet fields after performing searching query
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FacetResponseDto {

    private Long valueCount;

    private String value;

    private FieldResponseDto field;

    private KeyResponseDto key;

}
