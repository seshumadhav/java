package com.smc.restlet.mailserver.server.resources;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.smc.restlet.mailserver.common.AccountsResource;

public class AccountsServerResource extends ServerResource implements
    AccountsResource {

  private static final List<String> accounts = new CopyOnWriteArrayList<String>();

  @Override
  @Get("txt")
  public String represent() {
	System.out.println("represent() called");

	StringBuilder result = new StringBuilder();
	for (String account : getAccounts()) {
	  result.append(account == null ? "" : account).append('\n');
	}
	return result.toString();
  }

  @Override
  @Post("txt")
  public String add(String account) {
	System.out.println("account: '" + account + "' attempted to be added");

	getAccounts().add(account);
	return Integer.toString(getAccounts().indexOf(account));
  }

  public static List<String> getAccounts() {
	System.out.println("getAccounts() called; Returning '" + accounts + "'");

	return accounts;
  }

}
