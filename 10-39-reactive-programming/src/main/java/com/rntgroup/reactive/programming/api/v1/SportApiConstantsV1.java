package com.rntgroup.reactive.programming.api.v1;

public final class SportApiConstantsV1 {

    private SportApiConstantsV1() {
        throw new UnsupportedOperationException("It's a constants class and can't be instantiated");
    }
    public static final String BASE_PATH = "/api/v1/sport";

    public static final String ALL_SPORTS = BASE_PATH;

    public static final String FIND_SPORTS_BY_NAME = BASE_PATH + "?q";
    // The thing above just doesn't work .-. Even out-of-box-API ...and(queryParam("q", exists -> true)) is useless

    public static final String ADD_NEW_SPORT_WITH_NAME = BASE_PATH + "/{sportname}";

}
