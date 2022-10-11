package com.rntgroup.rpc.protocols.avro.producer.utils;

import com.arakelian.faker.service.RandomPerson;
import com.rntgroup.rpc.protocols.avro.schema.Genre;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class BookRandomPropertiesUtils {

    private static final List<String> RANDOM_UTIL_WORDS = List.of(
            "Ktulhu", "Twilight", "Hat", "Nutcracker", "Moon", "Bass-Without-Strings",
            "Magic", "Mushroom", "Dog", "Unicorn", "Chicken", "USSR", "Hobo", "Cloud",
            "Death", "Heisenberg", "Pink", "Water", "Hot", "Solar", "Eagle", "Platypus",
            "Fang", "Necromancy", "From", "And", "Of", "Pie", "Vodka", "Knife", "Lord",
            "Hawk", "Poop", "Dragon", "Telescope", "Named By", "HTTP", "Lips", "Eye",
            "Frog", "Corpse", "Born By", "Coffee", "Suicide", "Scars", "Little"
    );

    private static final int MAX_TITLE_SIZE = 6;

    private BookRandomPropertiesUtils() {
        throw new UnsupportedOperationException("It's an utility class and can't be instantiated");
    }

    public static Genre getRandomGenre() {
        var randomGenreId = ThreadLocalRandom.current().nextInt(0, Genre.values().length);

        return Genre.values()[randomGenreId];
    }

    public static String getRandomAuthor() {
        var person = RandomPerson.get().next();

        return person.getFirstName() + " " + person.getLastName();
    }

    public static String getRandomTitle() {
        var titleSize = ThreadLocalRandom.current().nextInt(0, MAX_TITLE_SIZE);
        var titleWords = IntStream.rangeClosed(1, titleSize)
                .mapToObj(num -> getRandomWord())
                .collect(Collectors.toList());

        return StringUtils.join(titleWords, " ");
    }

    private static String getRandomWord() {
        var randomPivotalWordIndex = ThreadLocalRandom.current().nextInt(0, RANDOM_UTIL_WORDS.size());

        return RANDOM_UTIL_WORDS.get(randomPivotalWordIndex);
    }

}
