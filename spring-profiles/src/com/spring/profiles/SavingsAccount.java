package com.spring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccount implements Account {

  @Autowired
  @Qualifier("theDb")
  private Database db;

  @Override
  public void deposit(int money) {
    System.out.println("\n\nSomeModule starts working");
    db.save(money);
  }

}
