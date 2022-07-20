package org.example.search.engines.impl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.solr.client.solrj.beans.Field;
import org.example.search.engines.impl.repository.SolrBookRepository;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

/**
 * The model of the Book Document in the Solr
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@SolrDocument(collection = SolrBookRepository.COLLECTION)
public class SolrBook {

    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "full_title", type = "string")
    private String fullTitle;

    @Indexed(name = "title", type = "string")
    private String title;

    @Indexed(name = "authors", type = "strings")
    private List<String> authors;

    @Indexed(name = "content", type = "text")
    private String content;

    @Field
    private String language;

}
