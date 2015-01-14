package com.demo.web;

import static com.sun.jersey.spi.spring.container.servlet.SpringServlet.CONTEXT_CONFIG_LOCATION;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;


public class MyServer {
  
  /**
   * @param args
   * 
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    HttpServer server = new HttpServer();
    
    addHostPort(server);
    
    WebappContext webappContext = createWebappContext();
    webappContext.deploy(server);
    
    server.start();
    
    System.out.println("In order to test the server please try the following urls:");
    System.out.println("http://localhost:3388/smc/time to see time for smc");
    System.out.println("http://localhost:3388/bsv/time to see time for bsv");

    System.out.println("Press enter to stop the server...");
    System.in.read();
  }
  
  private static WebappContext createWebappContext() {
    WebappContext context = new WebappContext("DEMO REST Server WebappContext", "/");
    
    final ServletRegistration reg = context.addServlet("spring", new SpringServlet());
    reg.addMapping("/*");

    context.addContextInitParameter(CONTEXT_CONFIG_LOCATION, "classpath:applicationContext.xml");
    context.addContextInitParameter(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE.toString());
    context.addListener("org.springframework.web.context.ContextLoaderListener");
    context.addListener("org.springframework.web.context.request.RequestContextListener");
    
    return context;
  }
  
  private static void addHostPort(HttpServer server) {
    NetworkListener listener = new NetworkListener("grizzly2", "localhost", 3388);
    server.addListener(listener);
  }

}
