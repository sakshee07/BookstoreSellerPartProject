package com.bridgelabz.sellerdetails;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class SellerDetailsApplication {

    public static void main(String[] args) {
    	final Logger log = LoggerFactory.getLogger(SellerDetailsApplication.class);
        SpringApplication.run(SellerDetailsApplication.class, args);
        
        System.out.println("--------------------------------");
        log.info("\nHello! Welcome to Book Store Seller Project!");
    }

}
