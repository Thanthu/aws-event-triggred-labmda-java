package com.thanthu.aws.lambda.s3sns;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanthu.aws.lambda.s3sns.model.PatientCheckoutEvent;

public class BillManagementLambda {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public void handler(SNSEvent snsEvent, Context context) {
		LambdaLogger logger = context.getLogger();
		snsEvent.getRecords().forEach(record -> {
			try {
				PatientCheckoutEvent patientCheckoutEvent = objectMapper.readValue(record.getSNS().getMessage(),
						PatientCheckoutEvent.class);
				logger.log(patientCheckoutEvent.toString());
			} catch (JsonProcessingException e) {
				StringWriter stringWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(stringWriter));
				logger.log(stringWriter.toString());
				throw new RuntimeException(e);
			}
		});
	}

}
