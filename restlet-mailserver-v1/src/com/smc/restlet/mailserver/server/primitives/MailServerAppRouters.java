package com.smc.restlet.mailserver.server.primitives;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.routing.Template;

public class MailServerAppRouters extends Application {

  public static void main(String[] args) throws Exception {
	Component component = new Component();
	component.getServers().add(Protocol.HTTP, 8111);

	// Attach the application to the component and start it
	component.getDefaultHost().attach(new MailServerAppRouters());
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

	Router router = new Router(context);
	router.setDefaultMatchingMode(Template.MODE_EQUALS);
	router.attach("/", clientInfoRestlet);
	router.attach("/admin", adminFilterRestlet);
	router.attach("/client", clientInfoRestlet);
	router.attach("/filter", ipAddressFilterRestlet);

	return router;
  }
}
