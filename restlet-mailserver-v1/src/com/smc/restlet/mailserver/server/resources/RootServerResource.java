package com.smc.restlet.mailserver.server.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.smc.restlet.mailserver.common.RootResource;

public class RootServerResource extends ServerResource implements RootResource {

  @Override
  @Get("txt")
  public String represent() {
	return "Welcome to " + getApplication().getName() + " !";
  }

}
