package com.rntgroup.big.data.task3.job.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public abstract class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
}
