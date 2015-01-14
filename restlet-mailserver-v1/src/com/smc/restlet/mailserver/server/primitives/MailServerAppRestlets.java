package com.smc.restlet.mailserver.server.primitives;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;

public class MailServerAppRestlets extends Application {

  public static void main(String[] args) throws Exception {
	Component component = new Component();
	component.getServers().add(Protocol.HTTP, 8111);

	// Attach the application to the component and start it
	component.getDefaultHost().attach(new MailServerAppRestlets());
	component.start();
  }

  @Override
  public Restlet createInboundRoot() {
	Context context = getContext();

	AdminFilterRestlet adminFilterRestlet = new AdminFilterRestlet(context);
	ClientInfoRestlet clientInfoRestlet = new ClientInfoRestlet(context);

	IpAddressBlockingFilterRestlet ipAddressFilterRestlet = new IpAddressBlockingFilterRestlet(
	    context);
	ipAddressFilterRestlet.setNext(clientInfoRestlet);
	ipAddressFilterRestlet.getBlacklistedIps().add("127.0.0.1");

	// return ipAddressFilterRestlet;
	// return adminFilterRestlet;
	return clientInfoRestlet;
  }
}
