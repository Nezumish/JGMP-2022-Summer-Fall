package com.rntgroup.big.data.task3.job.reducer.impl;

import com.rntgroup.big.data.task3.job.reducer.WordReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class WordCountReducer extends WordReducer {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        System.out.println("Reduce: processing a word...");
        super.reduce(key, values, context);
    }

}
