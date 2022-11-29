package com.ag.aws.s3viewer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

/**
 * Service class to view/retrieve S3 bucket objects
 * 
 * @author apurba.giri
 *
 */
@Service
public class S3ObjectService {

	@Autowired
	private Environment env;
	private static S3Client client;

	public void initClient() {
		if (client == null) {
			String accessKey = env.getProperty("s3.accesskey");
			String secretKey = env.getProperty("s3.secretkey");
			Region region = Region.of(env.getProperty("s3.bucket.region"));

			AwsBasicCredentials awsCred = AwsBasicCredentials.create(accessKey, secretKey);
			client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(awsCred)).region(region)
					.build();
		}
	}

	public List<S3Object> getS3ObjectsByDate(String bucket, String prefix, Date dateFrom, Date dateUntil) {
		initClient();
		ListObjectsV2Request s3Request = ListObjectsV2Request.builder().bucket(bucket).prefix(prefix).build();
		ListObjectsV2Iterable responseIterable = client.listObjectsV2Paginator(s3Request);

		List<S3Object> filteredObjectList = new ArrayList<>();
		for (ListObjectsV2Response objectPage : responseIterable) {
			objectPage.contents().forEach((S3Object object) -> {
				if (dateFrom != null && dateUntil != null) {
					if (object.lastModified().isAfter(dateFrom.toInstant())
							&& object.lastModified().isBefore(dateUntil.toInstant())) {
						filteredObjectList.add(object);
					}
				}
			});
		}

		return filteredObjectList;
	}

}
