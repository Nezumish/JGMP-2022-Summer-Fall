package com.rntgroup.big.data.task4;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationParameter {

    DATA_FILE_PATH("--data", " <file-path> \n\tthe path to a text file to process", true),
    OUTPUT_FILE_PATH("--output"," <path> \n\tthe result output path" , true),
    STOP_WORDS("--filter", " <word1,word2,...,wordN> \n\tthe stop words list", false);

    private static final Set<String> IGNORED_PROPERTY_PREFIXES = Set.of("--spring");

    private static final String PARAMETER_PREFIX = "--";

    private static final String REQUIRED_CLAUSE = "Required parameters:";

    private static final String OPTIONAL_CLAUSE = "Optional parameters:";

    private final String parameterName;

    private final String description;

    private final boolean required;

    ApplicationParameter(String parameterName, String description, boolean required) {
        this.parameterName = parameterName;
        this.description = description;
        this.required = required;
    }


    public static List<String> getArgumentsList() {
        List<String> argumentsList = new ArrayList<>();
        argumentsList.add(REQUIRED_CLAUSE);
        argumentsList.addAll(
                Arrays.stream(values())
                        .filter(ApplicationParameter::isRequired)
                        .map(ApplicationParameter::formatParameterInfo)
                        .collect(Collectors.toList())
        );

        argumentsList.add(OPTIONAL_CLAUSE);
        argumentsList.addAll(
                Arrays.stream(values())
                        .filter(parameter -> !parameter.isRequired())
                        .map(ApplicationParameter::formatParameterInfo)
                        .collect(Collectors.toList())
        );

        return argumentsList;
    }

    private static String formatParameterInfo(ApplicationParameter applicationParameter) {
        return applicationParameter.getParameterName() + applicationParameter.getDescription();
    }

    public static Map<ApplicationParameter, String> extractParametersAndArguments(List<String> passedValues) {
        List<Integer> parametersPlacements = passedValues.stream()
                .filter(ApplicationParameter::isNotIgnored)
                .filter(parameter -> StringUtils.startsWith(parameter, PARAMETER_PREFIX))
                .map(passedValues::indexOf)
                .collect(Collectors.toList());

        Map<ApplicationParameter, String> argumentsByParameters = new EnumMap<>(ApplicationParameter.class);
        for (int parameterIndex : parametersPlacements) {
            String parameterName = passedValues.get(parameterIndex);
            ApplicationParameter parameter = tryParseApplicationParameter(parameterName);

            int argumentIndex = parameterIndex + 1;
            String argument = tryGetArgumentAtIndex(passedValues, argumentIndex);

            argumentsByParameters.put(parameter, argument);
        }

        return argumentsByParameters;
    }

    private static boolean isNotIgnored(String parameter) {
        return IGNORED_PROPERTY_PREFIXES.stream()
                .noneMatch(prefix -> StringUtils.startsWith(parameter, prefix));
    }

    private static ApplicationParameter tryParseApplicationParameter(String suggestedParameterName) {
        return Arrays.stream(values())
                .filter(parameterValue -> parameterValue.getParameterName().equals(suggestedParameterName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown parameter: " + suggestedParameterName));
    }

    private static String tryGetArgumentAtIndex(List<String> values, int argumentIndex) {
        String argument;

        try {
            argument = values.get(argumentIndex);

            if (StringUtils.startsWith(argument, PARAMETER_PREFIX)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            int parameterIndex = argumentIndex - 1;
            String parameter = values.get(parameterIndex);

            throw new IllegalArgumentException("No valid argument for parameter " + parameter);
        }

        return argument;
    }


    public final String getParameterName() {
        return this.parameterName;
    }

    public final String getDescription() {
        return this.description;
    }

    private boolean isRequired() {
        return this.required;
    }

}
