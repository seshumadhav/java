package com.demo.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringRunner {
  
  @Autowired
  private MyBean myBean;

  /**
   * @param args
   */
  public static void main(String[] args) {
    GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
    
    MyBean bean = context.getBean(com.demo.beans.MyBean.class);
    System.out.println(bean.getTimeBlob("Context"));
    
    SpringRunner runner = context.getBean(com.demo.beans.SpringRunner.class);
    System.out.println(runner.myBean.getTimeBlob("Autowire"));
  }

}
