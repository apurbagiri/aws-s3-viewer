package com.ag.aws.s3viewer.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class ViewerService {

	@Autowired
	private S3ObjectService s3ObjectService;

	private static final String BUCKET_NAME = "my-test-bucket-1242812312";
	private static final String PREFIX = "";

	public void searchObjects() throws ParseException {
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2022-11-28 11:40:00");
		Date dateUntil = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2022-11-30 11:50:00");

		List<S3Object> objectList = s3ObjectService.getS3ObjectsByDate(BUCKET_NAME, PREFIX, dateFrom, dateUntil);

		printObjectList(objectList);
		System.out.println("Operation completed");
	}

	private void printObjectList(List<S3Object> objectList) {
		for (S3Object object : objectList) {
			System.out.println("Object Key: " + object.key() + "     " + "Date/Time: "
					+ LocalDateTime.ofInstant(object.lastModified(), ZoneOffset.systemDefault()));
		}
	}

}
