package com.smc.restlet.mailserver.server.resources;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.smc.restlet.mailserver.common.AccountResource;

public class AccountServerResource extends ServerResource implements
    AccountResource {

  private int accountId;

  @Override
  protected void doInit() throws ResourceException {
	this.accountId = Integer.parseInt(getAttribute("accountId"));
  }

  @Override
  @Get("txt")
  public String represent() {
	System.out.println("Request to represent account '" + accountId + "'");

	return AccountsServerResource.getAccounts().get(this.accountId);

  }

  @Override
  @Put("txt")
  public void store(String account) {
	System.out.println("Request to update account '" + accountId
	    + "' with content: '" + account + "'");

	AccountsServerResource.getAccounts().set(this.accountId, account);
  }

  @Override
  @Delete
  public void remove() {
	System.out.println("Request to delete account '" + accountId + "'");

	AccountsServerResource.getAccounts().remove(this.accountId);
  }

}
