package com.simplespring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

  private MySampleBean mySampleBean;

  // Run with -Denvironment=test to load test properties
  // Run with -Denvironment=prod to load prod properties
  // http://www.particlewave.com/2013/06/01/configuring-spring-for-test-versus-production/
  public App(MySampleBean mySampleBean) {
    this.mySampleBean = mySampleBean;
  }

  public void run() {
    System.out.println("To String: " + mySampleBean.toString());
  }

  public static void main(String[] args) {
    ApplicationContext beanFactory = new ClassPathXmlApplicationContext(
        "spring.xml");
    App app = beanFactory.getBean(App.class);
    app.run();
  }

}
