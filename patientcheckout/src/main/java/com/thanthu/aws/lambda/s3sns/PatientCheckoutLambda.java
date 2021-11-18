package com.thanthu.aws.lambda.s3sns;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanthu.aws.lambda.s3sns.model.PatientCheckoutEvent;

public class PatientCheckoutLambda {

	private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();

	public void handler(S3Event s3Event, Context context) {
		LambdaLogger logger = context.getLogger();
		
		s3Event.getRecords().forEach(record -> {
			S3ObjectInputStream s3ObjectInputStream = amazonS3
					.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey())
					.getObjectContent();

			try {
				List<PatientCheckoutEvent> pateintCheckoutEvents = Arrays
						.asList(objectMapper.readValue(s3ObjectInputStream, PatientCheckoutEvent[].class));

				logger.log(pateintCheckoutEvents.toString());

				pateintCheckoutEvents.forEach(event -> {
					try {
						amazonSNS.publish(System.getenv("PATIENT_CHECKOUT_TOPIC"),
								objectMapper.writeValueAsString(event));
					} catch (JsonProcessingException e) {
						StringWriter stringWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(stringWriter));
						logger.log(stringWriter.toString());
					}
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
