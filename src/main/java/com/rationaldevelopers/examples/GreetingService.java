package com.rationaldevelopers.examples;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

  public String sayHello(final String appendix) {
    return "hello: " + appendix;
  }
}
