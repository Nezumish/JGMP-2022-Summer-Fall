package com.rntgroup.big.data.task3;

import com.rntgroup.big.data.task3.service.JobService;
import com.rntgroup.big.data.task3.service.impl.WordCountJobService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCountApplication {

    /**
     * Validates passed parameters and launches a Job with it
     */
    public static void main(String[] values) throws Exception {
        if (valuesArePresent(values)) {
            Map<ApplicationParameter, String> extractedParametersAndArgs = tryParseParametersAndArgs(values);

            JobService jobService = new WordCountJobService();
            jobService.launchJobWithParameters(extractedParametersAndArgs);
        } else {
            printHints();
        }
    }

    private static boolean valuesArePresent(String[] values) {
        return values != null && values.length > 0;
    }

    private static void printHints() {
        System.out.println("Arguments can't be empty: ");
        ApplicationParameter.getArgumentsList().forEach(System.out::println);
    }

    private static Map<ApplicationParameter, String> tryParseParametersAndArgs(String[] values) {
        List<String> valuesAsList = Arrays.stream(values).collect(Collectors.toList());

        Map<ApplicationParameter, String> extractedParametersAndArgs = null;
        try {
            extractedParametersAndArgs = ApplicationParameter.extractParametersAndArguments(valuesAsList);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            printHints();

            System.exit(1);
        }

        return extractedParametersAndArgs;
    }

}
