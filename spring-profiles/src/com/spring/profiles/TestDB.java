package com.spring.profiles;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TestDB implements Database {

  @Override
  public void save(int money) {
    System.out.println("This is TestDB:save(" + money + ")");
  }

}
