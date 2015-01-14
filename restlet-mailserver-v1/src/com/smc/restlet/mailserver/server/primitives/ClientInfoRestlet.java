package com.smc.restlet.mailserver.server.primitives;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

public class ClientInfoRestlet extends Restlet {
  
  public ClientInfoRestlet(Context context) {
	super(context);
  }
  
  @Override
  public void handle(Request request, Response response) {
	StringBuilder sb = new StringBuilder();
	
	sb.append("Method: ");
	sb.append(request.getMethod());
	
	sb.append("\nResource URI: ");
	sb.append(request.getResourceRef());
	
	sb.append("\nIP Address: ");
	sb.append(request.getClientInfo().getAddress());
	
	sb.append("\nAgent name: ");
	sb.append(request.getClientInfo().getAgentName());
	
	sb.append("\nAgent version: ");
	sb.append(request.getClientInfo().getAgentVersion());
	
	response.setEntity(sb.toString(), MediaType.TEXT_PLAIN);
  }

}
