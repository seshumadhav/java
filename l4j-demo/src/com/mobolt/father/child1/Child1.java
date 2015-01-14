package com.mobolt.father.child1;

import org.apache.log4j.Logger;

import com.mobolt.common.MyLogger;
import com.mobolt.father.child2.Child2;

public class Child1 {
	
	// private static final Logger LOG = Logger.getLogger(Child1.class.getCanonicalName());
	private static final Logger LOG = Logger.getLogger("CChild1");
	private static Logger LOG2;
	
	public static void callMe1() {
		LOG.fatal("I am fatal. Look how I ll be printed in next line");
		LOG2 = MyLogger.getLogger();
		LOG2.info("I am fatal");
		
		Child2.callMe2();
	}
	

}
