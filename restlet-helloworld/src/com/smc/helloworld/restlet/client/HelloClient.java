package com.smc.helloworld.restlet.client;

import org.restlet.resource.ClientResource;

public class HelloClient {

  public static void main(String[] args) throws Exception {
	ClientResource helloClientResource = new ClientResource(
	    "http://localhost:8111/");
	helloClientResource.get().write(System.out);
  }

}
