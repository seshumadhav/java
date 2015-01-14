package com.smc.restlet.mailserver.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.smc.restlet.mailserver.server.resources.AccountServerResource;
import com.smc.restlet.mailserver.server.resources.AccountsServerResource;
import com.smc.restlet.mailserver.server.resources.RootServerResource;

public class MailServerApplication extends Application {

  public MailServerApplication() {
	setName("RESTful Mail Server application");
	setDescription("Example application for 'Restlet in Action' book");
	setOwner("Restlet SAS");
	setAuthor("The Restlet Team");
  }

  @Override
  public Restlet createInboundRoot() {
	Router router = new Router(getContext());
	router.attach("/", RootServerResource.class);
	router.attach("/accounts/", AccountsServerResource.class);
	router.attach("/accounts/{accountId}", AccountServerResource.class);
	return router;
  }

}
