package com.mobolt.father.child3;

import org.apache.log4j.Logger;

import com.mobolt.father.MainProgram;

public class Child3 {
	
	private static final Logger LOG = Logger.getLogger(Child3.class.getCanonicalName());
	
	public static void callMe3() {
		LOG.info("I info");
		MainProgram.callMain();
	}
	

}
