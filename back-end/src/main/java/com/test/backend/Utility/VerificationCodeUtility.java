package com.test.backend.Utility;

import java.util.Random;

import lombok.experimental.UtilityClass;
@UtilityClass
public class VerificationCodeUtility {
	
	public class VerificationCodeGenerator {
		  
		  public static String generateVerificationCode() {
		    Random random = new Random();
		    int code = 100000 + random.nextInt(900000);
		    return String.valueOf(code);
		  }
	}
}
