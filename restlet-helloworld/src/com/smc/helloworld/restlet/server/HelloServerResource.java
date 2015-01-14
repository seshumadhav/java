package com.smc.helloworld.restlet.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloServerResource extends ServerResource {

	@Get
	public String represent() {
		return "hello, world";
	}

}
