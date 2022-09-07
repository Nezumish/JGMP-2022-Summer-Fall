package com.rntgroup.big.data.task4.service.impl;

import com.rntgroup.big.data.task4.service.SparkWordCountService;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile("sql")
public class SqlSparkWordCountService extends SparkWordCountService {

    private final transient SQLContext sqlSpark;

    public SqlSparkWordCountService(SQLContext sqlSpark) {
        this.sqlSpark = sqlSpark;
    }

    @Override
    public void launchSomeWorkCountJob(String inputDataPath, String outputDataPath, Collection<String> stopWords) {
        var inputDataPathAsLocal = toLocalFilePath(inputDataPath);

        sqlSpark.read()
                .text(inputDataPathAsLocal)
                .createOrReplaceTempView("text");

        splitTextByLines();
        splitLinesByWords();
        clearSymbols();
        clearStopWords(stopWords);
        countWords();
        saveResults(toLocalFilePath(outputDataPath));
    }

    private void splitTextByLines() {
        sqlSpark.sql("SELECT explode(split(lower(value), '[\t\r\n\f]')) AS line FROM text")
                .createOrReplaceTempView("text");
    }

    private void splitLinesByWords() {
        sqlSpark.sql("SELECT explode(split(line, ' ')) AS word FROM text")
                .createOrReplaceTempView("text");
    }

    private void clearSymbols() {
        sqlSpark.sql("SELECT regexp_replace(word, '" + NOT_A_LETTER + "', '') AS word FROM text")
                .createOrReplaceTempView("text");
    }

    private void clearStopWords(Collection<String> stopWords) {
        sqlSpark.sql("SELECT word FROM text WHERE word NOT IN (" + toFilterArray(stopWords) + ")")
                .createOrReplaceTempView("text");
    }

    private String toFilterArray(Collection<String> stopWords) {
        StringBuilder filter = new StringBuilder();
        for (var word : stopWords) {
            filter.append("'");
            filter.append(word);
            filter.append("'");
            filter.append(",");
        }

        filter.deleteCharAt(filter.length() - 1);
        return filter.toString();
    }

    private void countWords() {
        sqlSpark.sql("SELECT word, count(1) as count FROM text GROUP BY word")
                .createOrReplaceTempView("text_word_count");
    }

    private void saveResults(String savePath) {
        var result = sqlSpark.sql("SELECT * FROM text_word_count ORDER BY count DESC");

        result.show();
        result.write().csv(savePath);
    }

}
