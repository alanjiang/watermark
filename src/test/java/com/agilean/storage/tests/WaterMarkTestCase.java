package com.agilean.storage.tests;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.hanniu.Application;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan(value= {"com.hanniu"})
@SpringBootTest(classes= {Application.class})
public class WaterMarkTestCase {
	
	 
}
