package com.smc.restlet.mailserver.server.resources;

import org.restlet.resource.ServerResource;

import com.smc.restlet.mailserver.common.RootResource;

public class RootServerResourceWithAnnots

extends ServerResource implements RootResource {

  @Override
  public String represent() {
	return "This is the root resource";
  }

  public String describe() {
	throw new RuntimeException("Not yet implemented");
  }

}
