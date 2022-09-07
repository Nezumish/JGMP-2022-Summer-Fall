package com.rntgroup.big.data.task3.job.partitioner.impl;

import com.rntgroup.big.data.task3.job.partitioner.WordPartitioner;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/**
 * Separated words by two partitions for reducers based on its length
 */
public class WordByLengthPartitioner extends WordPartitioner {

    private static final int SHORT_WORD_LENGTH = 5;

    @Override
    public int getPartition(Text text, IntWritable intWritables, int numPartitions) {
        int partition;

        int textLength = text.getLength();
        if (textLength <= SHORT_WORD_LENGTH) {
            partition = 0;
        } else {
            partition = 1;
        }

        return partition;
    }

}
