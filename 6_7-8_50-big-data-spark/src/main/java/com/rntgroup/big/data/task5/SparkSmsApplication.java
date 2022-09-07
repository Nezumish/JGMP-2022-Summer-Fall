package com.rntgroup.big.data.task5;

import com.rntgroup.big.data.task5.service.SparkSmsService;
import com.rntgroup.big.data.task5.service.impl.SparkSmsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;

public class SparkSmsApplication {

    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private static final String SLASH_PREFIX = "/";
    private static final String LOCAL_FILE_PREFIX = "file:";

    public static void main(String[] args) {
        validate(args);
        var smsDataPath = toLocalFilePath(args[0]);

        SparkSmsService smsService = new SparkSmsServiceImpl(getSqlContext());
        var top5SpamWords = smsService.takeTop5SpamNotOccurredWords(smsDataPath);


        System.out.println("FOUND TOP 5 DISTINCT SPAM WORDS:");
        top5SpamWords.forEach(System.out::println);
    }

    private static void validate(String[] args) {
        if (args.length != EXPECTED_PARAMETERS_COUNT) {
            System.out.println("Please, write the SMS data file path to process");
        }
    }

    private static SQLContext getSqlContext() {
        var sparkConf = new SparkConf();
        sparkConf.setAppName("SPARK");
        sparkConf.setMaster("local[*]");

        var javaSparkContext = new JavaSparkContext(sparkConf);
        return new SQLContext(javaSparkContext);
    }

    private static String toLocalFilePath(String filePath) {
        StringBuilder builtFilePath = new StringBuilder(LOCAL_FILE_PREFIX);

        var addSlashPrefix = !StringUtils.startsWith(filePath, SLASH_PREFIX);
        if (addSlashPrefix) {
            builtFilePath.append(SLASH_PREFIX);
        }

        builtFilePath.append(filePath);
        return builtFilePath.toString();
    }

}
