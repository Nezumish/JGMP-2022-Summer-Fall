package com.rntgroup.big.data.task4.service.impl;

import com.rntgroup.big.data.task4.service.SparkWordCountService;
import org.apache.commons.text.StringTokenizer;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.apache.spark.sql.functions.column;
import static org.apache.spark.sql.functions.count;
import static org.apache.spark.sql.functions.lower;
import static org.apache.spark.sql.functions.not;
import static org.apache.spark.sql.functions.regexp_replace;

@Service
@Profile("df")
public class DataFrameSparkWordCountService extends SparkWordCountService {

    private final transient SQLContext sqlSpark;

    public DataFrameSparkWordCountService(SQLContext sqlSpark) {
        this.sqlSpark = sqlSpark;
    }

    @Override
    public void launchSomeWorkCountJob(String inputDataPath, String outputDataPath, Collection<String> stopWords) {
        var inputDataPathAsLocal = toLocalFilePath(inputDataPath);
        var outputDataPathAsLocal = toLocalFilePath(outputDataPath);

        var wordCount = sqlSpark.read().text(inputDataPathAsLocal)
                .flatMap(
                        (FlatMapFunction<Row, String>) line -> new StringTokenizer(line.toString()).getTokenList().iterator(),
                        Encoders.STRING())
                .withColumnRenamed("value", "word")
                .cache()
                .withColumn("word", regexp_replace(column("word"), NOT_A_LETTER, ""))
                .withColumn("word", lower(column("word")))
                .filter(not(column("word").isin(stopWords.toArray())))
                .groupBy(column("word")).agg(count("word").as("count"))
                .sort(column("count").desc());

        wordCount.write().csv(outputDataPathAsLocal);
        wordCount.show(false);
    }

}
