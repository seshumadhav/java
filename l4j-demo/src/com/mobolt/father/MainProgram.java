package com.mobolt.father;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.mobolt.common.MyLogger;
import com.mobolt.father.child1.Child1;

public class MainProgram {
	
	private static final Logger LOG = Logger.getRootLogger();
	
	public static void main(String[] args) {
		MyLogger.log(Level.FATAL, "Beginning beginning fatal!");
		
		LOG.info("Calling Child1");
		Child1.callMe1();
	}

	public static void callMain() {
		LOG.error("I errored");
	}

}
