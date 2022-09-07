package com.rntgroup.big.data.task3.service;

import com.rntgroup.big.data.task3.ApplicationParameter;

import java.util.Map;

public interface JobService {

    /**
     * Launches an encapsulated Hadoop Job with the given parameters and returns it result
     *
     * @param jobConfiguration - job parameters
     * @return result code
     */
    int launchJobWithParameters(Map<ApplicationParameter, String> jobConfiguration) throws Exception;

}
