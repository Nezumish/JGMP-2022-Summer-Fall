package com.rntgroup.big.data.task4.configuration;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spark configuration class depending on Spring profile
 */
@Configuration
public class SparkConfiguration {

    @Bean
    SparkConf sparkConf(@Value("${spark.application-name}") String applicationName,
                        @Value("${spark.master}") String master) {
        var sparkConf = new SparkConf();
        sparkConf.setAppName(applicationName);
        sparkConf.setMaster(master);

        return sparkConf;
    }

    @Bean
    JavaSparkContext javaSparkContext(SparkConf sparkConf) {
        return new JavaSparkContext(sparkConf);
    }

    @Bean
    @Profile("!rdd")
    SQLContext sqlContext(JavaSparkContext javaSparkContext) {
        return new SQLContext(javaSparkContext);
    }

}
