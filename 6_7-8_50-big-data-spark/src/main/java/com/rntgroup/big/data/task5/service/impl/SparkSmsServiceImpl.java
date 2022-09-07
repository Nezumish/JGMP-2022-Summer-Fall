package com.rntgroup.big.data.task5.service.impl;

import com.rntgroup.big.data.task5.service.SparkSmsService;
import org.apache.commons.text.StringTokenizer;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.io.Serializable;
import java.util.List;

import static org.apache.spark.sql.functions.column;
import static org.apache.spark.sql.functions.count;
import static org.apache.spark.sql.functions.lower;
import static org.apache.spark.sql.functions.not;
import static org.apache.spark.sql.functions.regexp_replace;
import static org.apache.spark.sql.functions.split;

public class SparkSmsServiceImpl implements SparkSmsService, Serializable {

    private static final String COLUMNS_DELIMITER = "\t";
    private static final String NOT_A_LETTER = "[^a-zA-Z]";

    private final SQLContext sqlSpark;

    public SparkSmsServiceImpl(SQLContext sqlSpark) {
        this.sqlSpark = sqlSpark;
    }

    @Override
    public List<Row> takeTop5SpamNotOccurredWords(String smsDataPath) {

        var sms = sqlSpark.read().text(smsDataPath)
                .select(split(column("value"), COLUMNS_DELIMITER).getItem(0).as("category"),
                        split(column("value"), COLUMNS_DELIMITER).getItem(1).as("message"))
                .cache();

        var hamSmsDistinctWords = getWordsFromCategory(sms, "ham")
                .distinct()
                .collectAsList().stream().map(row -> row.getString(0))
                .toArray();
        var spamSmsCountedWords = countWordsInCategory(sms, "spam");

        var top5SpamDistinctWords = spamSmsCountedWords
                .filter(not(column("word").isin(hamSmsDistinctWords)))
                .sort(column("count").desc()).limit(5);

        return top5SpamDistinctWords.collectAsList();
    }

    private Dataset<Row> getWordsFromCategory(Dataset<Row> wholeDataset, String categoryName) {
        return wholeDataset
                .filter("category == '" + categoryName + "'")
                .select(column("message"))
                .flatMap((FlatMapFunction<Row, String>) line -> new StringTokenizer(line.toString()).getTokenList().iterator(),
                        Encoders.STRING())
                .withColumn("value", regexp_replace(column("value"), NOT_A_LETTER, ""))
                .withColumn("value", lower(column("value")))
                .withColumnRenamed("value", "word");
    }

    private Dataset<Row> countWordsInCategory(Dataset<Row> wholeDataset, String categoryName) {
        return getWordsFromCategory(wholeDataset, categoryName)
                .groupBy(column("word")).agg(count("word").as("count"))
                .drop(column("category"));
    }

}
