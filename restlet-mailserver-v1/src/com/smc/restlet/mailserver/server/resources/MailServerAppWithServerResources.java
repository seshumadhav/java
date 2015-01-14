package com.smc.restlet.mailserver.server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class MailServerAppWithServerResources extends Application {

  public static void main(String[] args) throws Exception {
	Component component = new Component();
	component.getServers().add(Protocol.HTTP, 8111);

	// Attach the application to the component and start it
	MailServerAppWithServerResources mailServer = new MailServerAppWithServerResources();
	mailServer.setName("My Mail Server: Routers & ServerResources");
	mailServer.setAuthor("smc");

	component.getDefaultHost().attach(mailServer);
	component.start();
  }

  @Override
  public Restlet createInboundRoot() {
	Context context = getContext();

	Router router = new Router(context);
	router.attach("/", RootServerResource.class);

	return router;
  }

}
