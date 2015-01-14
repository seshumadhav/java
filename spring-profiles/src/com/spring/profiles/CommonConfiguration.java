package com.spring.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class CommonConfiguration {

  @Autowired
  protected TestDB testDb;

  @Autowired
  protected ProdDb prodDb;

  @Bean
  @Qualifier("test")
  protected Database getTestDb() {
    return testDb;
  }

  @Bean
  @Qualifier("prod")
  protected Database getProdDb() {
    return prodDb;
  }

  @Autowired
  protected SavingsAccount savingsAccount;

  @Bean
  protected Account account() {
    return savingsAccount;
  }

}
