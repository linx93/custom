package com.tianji.transdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author linx
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TransDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransDataApplication.class, args);
    }

}
