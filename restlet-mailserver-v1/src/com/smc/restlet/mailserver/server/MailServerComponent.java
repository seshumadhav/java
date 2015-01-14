package com.smc.restlet.mailserver.server;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class MailServerComponent extends Component {

  public static void main(String[] args) throws Exception {
	new MailServerComponent().start();
  }

  public MailServerComponent() {
	setName("RESTful Mail server component");
	setDescription("Example for 'Restlet in Action' book");
	setOwner("Restlet SAS");
	setAuthor("Restlet Author team");

	getServers().add(Protocol.HTTP, 8111);
	getDefaultHost().attachDefault(new MailServerApplication());
  }

}
