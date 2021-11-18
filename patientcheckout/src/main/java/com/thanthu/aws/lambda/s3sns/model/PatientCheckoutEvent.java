package com.thanthu.aws.lambda.s3sns.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientCheckoutEvent {
	
	private String firstName;
	private String lastName;
	private String ssn;

}
