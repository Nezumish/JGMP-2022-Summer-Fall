package com.rntgroup.big.data.task3.service.impl;

import com.rntgroup.big.data.task3.ApplicationParameter;
import com.rntgroup.big.data.task3.job.WordCountJobWrapper;
import com.rntgroup.big.data.task3.job.mapper.impl.WordCountMapper;
import com.rntgroup.big.data.task3.job.partitioner.impl.WordByLengthPartitioner;
import com.rntgroup.big.data.task3.job.reducer.impl.WordCountReducer;
import com.rntgroup.big.data.task3.job.reducer.impl.combiner.WordsMapperCombiner;
import com.rntgroup.big.data.task3.service.JobService;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The implementation of {@link JobService} launching WorkCount jobs for the given file
 */
public class WordCountJobService implements JobService {

    /**
     * {@inheritDoc}
     */
    @Override
    public int launchJobWithParameters(Map<ApplicationParameter, String> jobConfiguration) throws Exception {
        prepareStopWordsFilterIfPresent(jobConfiguration);

        WordCountJobWrapper workCountJob = new WordCountJobWrapper(
                WordCountMapper.class,
                WordsMapperCombiner.class,
                WordByLengthPartitioner.class,
                WordCountReducer.class
        );
        return ToolRunner.run(workCountJob, getInputOutputArgs(jobConfiguration));
    }

    /**
     * Extracts stop words from passed arguments if present and loads it to the mapper
     */
    private void prepareStopWordsFilterIfPresent(Map<ApplicationParameter, String> jobConfiguration) {
        if (jobConfiguration.containsKey(ApplicationParameter.STOP_WORDS)) {
            String stopWordsLine = jobConfiguration.get(ApplicationParameter.STOP_WORDS);

            WordCountMapper.addStopWords(
                    Arrays.stream(stopWordsLine.split(","))
                            .collect(Collectors.toList())
            );
        }
    }

    /**
     * Prepares job configuration
     *
     * @param jobConfiguration - raw job configuration
     * @return ordered job configuration parameters
     */
    private String[] getInputOutputArgs(Map<ApplicationParameter, String> jobConfiguration) {
        return new String[]{
                jobConfiguration.get(ApplicationParameter.DATA_FILE_PATH),
                jobConfiguration.get(ApplicationParameter.OUTPUT_FILE_PATH)
        };
    }

}
