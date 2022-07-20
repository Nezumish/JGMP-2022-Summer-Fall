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
public class FacetDto {

    private Long valueCount;

    private String value;

    private FieldDto field;

    private KeyDto key;

}
