package com.rntgroup.big.data.task3.job.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.stream.StreamSupport;

/**
 * Provides with the most simple reduce for counting words
 */
public abstract class WordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    /**
     * Groups counted words after mapping
     */
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int occurrenceTimes = StreamSupport.stream(values.spliterator(), false)
                .map(IntWritable::get)
                .reduce(0, Integer::sum);

        context.write(key, new IntWritable(occurrenceTimes));
    }

}
