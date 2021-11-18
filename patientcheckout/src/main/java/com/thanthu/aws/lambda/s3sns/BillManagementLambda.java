package com.thanthu.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanthu.aws.lambda.s3sns.model.PatientCheckoutEvent;

public class BillManagementLambda {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public void handler(SNSEvent snsEvent) {
		snsEvent.getRecords().forEach(record -> {
			try {
				PatientCheckoutEvent patientCheckoutEvent = objectMapper.readValue(record.getSNS().getMessage(),
						PatientCheckoutEvent.class);
				System.out.println(patientCheckoutEvent);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	}

}
