package com.rntgroup.big.data.task3.job.mapper.impl;

import com.rntgroup.big.data.task3.job.mapper.WordMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

public class WordCountMapper extends WordMapper {

    private static final String NOT_A_LETTER = "[^a-zA-Z]";
    private static final Set<String> STOP_WORDS = new HashSet<>();
    private final static IntWritable ONE = new IntWritable(1);

    // I don't like it, but it's to be static because Hadoop don't know how to use Mapper constructor :C
    public static void addStopWords(Collection<String> stopWords) {
        STOP_WORDS.addAll(stopWords);
    }

    /**
     * Splits given line into the words and counts its appearance
     */
    @Override
    public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) {
        String line = value.toString();

        final Text word = new Text();
        Collections.list(new StringTokenizer(line)).stream()
                .map(Objects::toString)
                .map(String::toLowerCase)
                .map(this::cleanToken)
                .filter(token -> !STOP_WORDS.contains(token))
                .forEach(token -> tryWrite(context, token, word));
    }

    /**
     * Removes redundant symbols from token
     *
     * @param token - the token to clear
     * @return only letters token
     */
    private String cleanToken(String token) {
        return token.replaceAll(NOT_A_LETTER, "");
    }

    /**
     * Inserts counted token into the job context
     */
    private void tryWrite(Mapper<LongWritable, Text, Text, IntWritable>.Context context, String token, Text word) {
        word.set(token);
        try {
            context.write(word, ONE);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
