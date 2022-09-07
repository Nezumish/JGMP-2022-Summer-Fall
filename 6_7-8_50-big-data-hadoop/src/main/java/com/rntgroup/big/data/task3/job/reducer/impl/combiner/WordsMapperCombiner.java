package com.rntgroup.big.data.task3.job.reducer.impl.combiner;

import com.rntgroup.big.data.task3.job.reducer.WordReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class WordsMapperCombiner extends WordReducer {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        System.out.println("Combiner: combining map results...");
        super.reduce(key, values, context);
    }

}
