package com.rntgroup.big.data.task4.configuration;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SparkConfiguration {

    @Bean
    SparkConf sparkConf() {
        var sparkConf = new SparkConf();
        sparkConf.setAppName("SPARK");
        sparkConf.setMaster("local[*]");

        return sparkConf;
    }

    @Bean
    JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    @Profile("!rdd")
    SQLContext sqlContext() {
        return new SQLContext(javaSparkContext());
    }

}
