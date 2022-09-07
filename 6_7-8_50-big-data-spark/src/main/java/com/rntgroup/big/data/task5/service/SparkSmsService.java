package com.rntgroup.big.data.task5.service;

import org.apache.spark.sql.Row;

import java.util.List;

public interface SparkSmsService {

    List<Row> takeTop5SpamNotOccurredWords(String smsDataPath);

}
