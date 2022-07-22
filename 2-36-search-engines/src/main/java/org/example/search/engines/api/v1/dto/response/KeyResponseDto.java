package org.example.search.engines.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The name of the key of the field in {@link FacetResponseDto}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class KeyResponseDto {

    private String name;

}
