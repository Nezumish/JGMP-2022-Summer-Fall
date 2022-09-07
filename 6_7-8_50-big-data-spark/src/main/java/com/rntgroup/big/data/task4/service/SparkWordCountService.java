package com.rntgroup.big.data.task4.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

public abstract class SparkWordCountService implements Serializable {

    protected static final String SLASH_PREFIX = "/";
    protected static final String LOCAL_FILE_PREFIX = "file:";
    protected static final String NOT_A_LETTER = "[^a-zA-Z]";

    protected final transient Logger log;

    protected SparkWordCountService() {
        this.log = LoggerFactory.getLogger(SparkWordCountService.class);
    }

    public abstract void launchSomeWorkCountJob(String inputDataPath, String outputDataPath, Collection<String> stopWords);

    protected String toLocalFilePath(String filePath) {
        StringBuilder builtFilePath = new StringBuilder(LOCAL_FILE_PREFIX);

        var addSlashPrefix = !StringUtils.startsWith(filePath, SLASH_PREFIX);
        if (addSlashPrefix) {
            builtFilePath.append(SLASH_PREFIX);
        }

        builtFilePath.append(filePath);
        return builtFilePath.toString();
    }

    protected String removeSymbols(String string) {
        return string.replaceAll(NOT_A_LETTER, "");
    }

}
