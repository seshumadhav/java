package com.smc.restlet.mailserver.server.primitives;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

public class AdminFilterRestlet extends Filter {
  
  public AdminFilterRestlet(Context context) {
	super(context);
  }
  
  @Override
  public int beforeHandle(Request request, Response response) {
	response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
	return STOP;
  }

}
