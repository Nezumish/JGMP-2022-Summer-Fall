package com.rntgroup.search.engines.impl.utils;

import lombok.experimental.UtilityClass;

import nl.siegmann.epublib.domain.Book;
import org.apache.commons.lang.StringUtils;

import com.rntgroup.search.engines.api.v1.dto.request.SearchBookRequestDto;

import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.util.Optional;

/**
 * Utility class to manage Solr queries
 */
@UtilityClass
public class SolrBookQueryUtils {

    public static String DEFAULT_SOLR_BOOK_FULL_TEXT_FIELD = "content";

    /**
     * @param searchBookRequestDto - search criteria DTO
     * @return {@link SimpleQuery} or {@link SimpleFacetQuery} depending on search criteria
     */
    public static Query qualifyBaseQuery(SearchBookRequestDto searchBookRequestDto) {
        Query solrDataQuery;

        if (searchBookRequestDto.getFacetField() != null) {
            solrDataQuery = buildFacetQuery(searchBookRequestDto);
        } else {
            solrDataQuery = new SimpleQuery();
        }

        return solrDataQuery;
    }

    public static SimpleFacetQuery buildFacetQuery(SearchBookRequestDto searchBookRequestDto) {
        FacetOptions facetOptions = new FacetOptions().addFacetOnField(searchBookRequestDto.getFacetField());
        return new SimpleFacetQuery().setFacetOptions(facetOptions);
    }

    public static boolean canBeFacetSearched(SearchBookRequestDto searchBookRequestDto) {
        return StringUtils.isNotEmpty(searchBookRequestDto.getFacetField());
    }

    public static Optional<Criteria> extractCriteria(SearchBookRequestDto searchBookRequestDto) {
        Criteria criteria = null;

        if (canBeFilterSearched(searchBookRequestDto)) {
            criteria = buildFilterCriteria(searchBookRequestDto);
        } else if (canBeFullTextSearched(searchBookRequestDto)) {
            criteria = buildFullTextCriteria(searchBookRequestDto);
        }

        return Optional.ofNullable(criteria);
    }

    public static boolean canBeFilterSearched(SearchBookRequestDto searchBookRequestDto) {
        return StringUtils.isNotEmpty(searchBookRequestDto.getField())
                && StringUtils.isNotEmpty(searchBookRequestDto.getValue());
    }

    public static Criteria buildFilterCriteria(SearchBookRequestDto searchBookRequestDto) {
        return new Criteria(searchBookRequestDto.getField()).is(searchBookRequestDto.getValue());
    }

    public static boolean canBeFullTextSearched(SearchBookRequestDto searchBookRequestDto) {
        if (StringUtils.isEmpty(searchBookRequestDto.getQuery())) {
            String value = searchBookRequestDto.getValue();
            searchBookRequestDto.setQuery(value);
        }

        return searchBookRequestDto.isFulltext()
                && StringUtils.isNotEmpty(searchBookRequestDto.getQuery());
    }

    public static Criteria buildFullTextCriteria(SearchBookRequestDto searchBookRequestDto) {
        String fullTextField = StringUtils.isNotEmpty(searchBookRequestDto.getField())
                ? searchBookRequestDto.getField()
                : DEFAULT_SOLR_BOOK_FULL_TEXT_FIELD;
        String fullTextQuery = (searchBookRequestDto.getQuery());
        String[] tokens = StringUtils.splitPreserveAllTokens(fullTextQuery, " ");

        return new Criteria(fullTextField).in((Object[]) tokens);
    }

}
