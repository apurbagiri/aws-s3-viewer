package com.ag.aws.s3viewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ag.aws.s3viewer.service.ViewerService;

@SpringBootApplication(scanBasePackages = "com.ag.aws.s3viewer")
public class Application implements CommandLineRunner {

	@Autowired
	private ViewerService viewerService;
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		viewerService.searchObjects();
	}

}
