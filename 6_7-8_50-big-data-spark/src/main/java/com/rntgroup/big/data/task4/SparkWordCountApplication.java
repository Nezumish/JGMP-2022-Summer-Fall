package com.rntgroup.big.data.task4;

import com.rntgroup.big.data.task4.service.SparkWordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = GsonAutoConfiguration.class)
public class SparkWordCountApplication implements CommandLineRunner {

    @Autowired
    private SparkWordCountService service;

    public static void main(String[] args) {
        SpringApplication.run(SparkWordCountApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var sourceArgs = Arrays.stream(args).collect(Collectors.toList());
        var applicationParameters = ApplicationParameter.extractParametersAndArguments(sourceArgs);

        String inputPath = applicationParameters.get(ApplicationParameter.DATA_FILE_PATH);
        String outputPath = applicationParameters.get(ApplicationParameter.OUTPUT_FILE_PATH);
        Collection<String> stopWords = getStopWordsList(applicationParameters);

        service.launchSomeWorkCountJob(inputPath, outputPath, stopWords);
    }

    private List<String> getStopWordsList(Map<ApplicationParameter, String> applicationParameters) {
        List<String> stopWords = Collections.emptyList();

        if (applicationParameters.containsKey(ApplicationParameter.STOP_WORDS)) {
            var stopWordsLine = applicationParameters.get(ApplicationParameter.STOP_WORDS);
            stopWords = Arrays.asList(stopWordsLine.split(","));
            stopWords.forEach(String::toLowerCase);
        }

        return stopWords;
    }

}
