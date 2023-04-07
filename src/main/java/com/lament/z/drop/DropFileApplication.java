package com.lament.z.drop;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DropFileApplication {
	public static void main(String[] args) {

//		Properties properties = System.getProperties();
//		for (String propertyName : properties.stringPropertyNames()) {
//			System.out.println(propertyName+" | "+properties.get(propertyName));
//		}


		SpringApplication.run(DropFileApplication.class, args);
	}

}
