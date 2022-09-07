package com.rntgroup.big.data.task4.service.impl;

import com.rntgroup.big.data.task4.service.SparkWordCountService;
import org.apache.commons.text.StringTokenizer;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Collection;
import java.util.List;

@Service
@Profile("rdd")
public class RddSparkWordCountService extends SparkWordCountService {

    private final transient JavaSparkContext spark;

    public RddSparkWordCountService(JavaSparkContext spark) {
        super();
        this.spark = spark;
    }

    @Override
    public void launchSomeWorkCountJob(String inputDataPath, String outputDataPath, Collection<String> stopWords) {
        var inputDataPathAsLocal = toLocalFilePath(inputDataPath);
        var outputDataPathAsLocal = toLocalFilePath(outputDataPath);

        var result = spark.textFile(inputDataPathAsLocal)
                .cache()
                .map(StringTokenizer::new)
                .map(stringTokenizer -> stringTokenizer.setIgnoreEmptyTokens(true))
                .map(StringTokenizer::getTokenList)
                .flatMap(List::iterator)
                .map(this::removeSymbols)
                .map(String::toLowerCase)
                .filter(stringToken -> notStopWord(stringToken, stopWords))
                .mapToPair(stringToken -> new Tuple2<>(stringToken, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey();

        result.saveAsTextFile(outputDataPathAsLocal);
        result.collectAsMap().forEach((key, value) -> log.info("{}: {}", value, key));
    }

    private boolean notStopWord(String token, Collection<String> stopWords) {
        return !stopWords.contains(token);
    }

}
