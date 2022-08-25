package com.rntgroup.search.engines.impl.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class to handle .epub files and their {@link Book} projections
 */
@UtilityClass
public class EpubBookUtils {

    private static final String CONTENTS_DELIMITER = " ";

    /**
     * @return authors list of the given {@link Book}
     */
    public static List<String> getAuthors(Book book) {
        return book.getMetadata().getAuthors().stream()
                .map(Author::toString)
                .collect(Collectors.toList());
    }

    /**
     * @return content of the given {@link Book}
     */
    public static String readContent(Book book) {
        return book.getContents().stream()
                .map(EpubBookUtils::contentAsString)
                .collect(Collectors.joining(CONTENTS_DELIMITER));
    }

    /**
     * @return portion of the content of the given {@link Book} as String
     */
    @SneakyThrows
    public static String contentAsString(Resource content) {
        return IOUtils.toString(content.getInputStream(), StandardCharsets.UTF_8);
    }

}
