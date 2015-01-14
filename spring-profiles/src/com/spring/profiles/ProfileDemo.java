package com.spring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProfileDemo {

  @Autowired
  @Qualifier("theDb")
  private Database db;

  @Autowired
  @Qualifier("prod")
  private Database prodDb;

  @Autowired
  @Qualifier("test")
  private Database testDb;

  @Autowired
  private Account  account;

  public static void main(String[] args) {
    GenericXmlApplicationContext context = new GenericXmlApplicationContext();
    context.getEnvironment().addActiveProfile("prod");
    context.load("spring.xml");
    context.refresh();

    ProfileDemo demo = context.getBean(ProfileDemo.class);

    // Test autowiring of databases based on profile
    demo.prodDb.save(10);
    demo.testDb.save(20);
    demo.db.save(30);

    demo.account.deposit(40);
    context.close();
  }

}
