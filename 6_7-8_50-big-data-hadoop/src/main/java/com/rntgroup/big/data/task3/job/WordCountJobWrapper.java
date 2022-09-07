package com.rntgroup.big.data.task3.job;

import com.rntgroup.big.data.task3.WordCountApplication;
import com.rntgroup.big.data.task3.job.mapper.WordMapper;
import com.rntgroup.big.data.task3.job.partitioner.WordPartitioner;
import com.rntgroup.big.data.task3.job.reducer.WordReducer;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

/**
 * The WordCount wrapper around Hadoop Job to make it more simple for configuration and launching
 */
public class WordCountJobWrapper extends Configured implements Tool {

    private static final String JOB_NAME = "WordCount";

    private static final int REDUCERS = 2;

    private static final int SUCCESS_CODE = 0;

    private static final int FAIL_CODE = 1;


    private final Class<? extends WordMapper> wordMapperClass;

    private final Class<? extends WordReducer> combinerClass;

    private final Class<? extends WordPartitioner> wordPartitionerClass;

    private final Class<? extends WordReducer> wordReducerClass;


    public WordCountJobWrapper(Class<? extends WordMapper> wordMapperClass,
                               Class<? extends WordReducer> combinerClass,
                               Class<? extends WordPartitioner> wordPartitionerClass,
                               Class<? extends WordReducer> wordReducerClass) {
        this.wordMapperClass = wordMapperClass;
        this.combinerClass = combinerClass;
        this.wordPartitionerClass = wordPartitionerClass;
        this.wordReducerClass = wordReducerClass;
    }

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf());

        job.setJobName(JOB_NAME);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setJarByClass(WordCountApplication.class);

        job.setMapperClass(wordMapperClass);
        job.setCombinerClass(combinerClass);
        job.setPartitionerClass(wordPartitionerClass);
        job.setReducerClass(wordReducerClass);
        job.setNumReduceTasks(REDUCERS);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? SUCCESS_CODE : FAIL_CODE;
    }

}
