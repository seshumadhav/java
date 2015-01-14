package com.demo.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.beans.MyBean;

@Component
@Path("/{username}")
public class ResourceFoo {
  
  @Autowired
  MyBean myBean;
  
  private String username;
  
  @Path("time")
  public ResourceFoo wta(@PathParam("username") String username) {
    this.username = username;
    return this;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response status() {
    return Response.ok(myBean.getTimeBlob(username)).build();
  }

}
