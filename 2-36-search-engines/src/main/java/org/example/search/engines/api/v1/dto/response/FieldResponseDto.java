package org.example.search.engines.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The value field of the {@link FacetResponseDto}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FieldResponseDto {

    private String name;

}
