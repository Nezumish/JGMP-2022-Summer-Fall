package com.rntgroup.big.data.task3.job.partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public abstract class WordPartitioner extends Partitioner<Text, IntWritable> {
}
