package com.rntgroup.search.engines.impl.repository;

import com.rntgroup.search.engines.impl.model.SolrBook;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolrBookRepository extends SolrCrudRepository<SolrBook, String> {
    String COLLECTION = "solr-book";

}
