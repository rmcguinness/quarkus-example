package com.rationaldevelopers.examples;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/greeting")
public class GreetingResource {

  @Inject
  private GreetingService greetingService;


  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Yo!";
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/{name}")
  public String hello(final @PathParam("name") String name) {
    return greetingService.sayHello(name);
  }
}
