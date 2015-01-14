package com.mobolt.father.child2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.mobolt.common.MyLogger;
import com.mobolt.father.child3.Child3;

public class Child2 {
	
	private static final Logger LOG = Logger.getLogger(Child2.class.getCanonicalName());
	
	public static void callMe2() {
		LOG.debug("I debug. See how am I throwing throwable");
		
		MyLogger.log(Level.FATAL, "I am t", new Throwable("I am throwing"));
		Child3.callMe3();
	}

}
