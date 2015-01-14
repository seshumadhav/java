package com.smc.restlet.mailserver.server.primitives;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

public class IpAddressBlockingFilterRestlet extends Filter {
  
  private final Set<String> blacklistedIps;

  public IpAddressBlockingFilterRestlet(Context context) {
	super(context);
	this.blacklistedIps = new CopyOnWriteArraySet<String>();
  }
  
  public Set<String> getBlacklistedIps() {
    return blacklistedIps;
  }

  @Override
  public int beforeHandle(Request request, Response response) {
	String thisRequestIp = request.getClientInfo().getAddress();
	
	if (getBlacklistedIps().contains(thisRequestIp)) {
	  response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	  return STOP;
	}
	
	return CONTINUE;
  }

}
