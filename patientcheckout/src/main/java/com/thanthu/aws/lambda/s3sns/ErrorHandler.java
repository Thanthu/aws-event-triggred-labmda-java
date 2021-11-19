package com.thanthu.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class ErrorHandler {

	public void handler(SNSEvent snsEvent, Context context) {
		LambdaLogger logger = context.getLogger();
		snsEvent.getRecords().forEach(record -> {
			logger.log("Dead Letter Queue Event: " + record.getSNS().getMessage());
		});
	}

}
