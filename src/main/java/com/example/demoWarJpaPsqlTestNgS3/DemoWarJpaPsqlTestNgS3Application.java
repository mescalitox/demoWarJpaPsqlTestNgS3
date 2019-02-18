package com.example.demoWarJpaPsqlTestNgS3;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.example.demoWarJpaPsqlTestNgS3.storage.StorageProperties;

@EnableConfigurationProperties({ StorageProperties.class })

@SpringBootApplication
public class DemoWarJpaPsqlTestNgS3Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoWarJpaPsqlTestNgS3Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
