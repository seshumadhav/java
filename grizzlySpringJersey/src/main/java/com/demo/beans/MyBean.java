package com.demo.beans;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MyBean {
  
  public MyBlob getTimeBlob(String user) {
    try {
      Thread.sleep(3 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    return new MyBlob(new Date(), user + " asked time. It is now: " + (new Date()).toString());
  }

}
