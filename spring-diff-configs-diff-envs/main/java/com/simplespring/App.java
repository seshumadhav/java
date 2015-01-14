package com.simplespring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

  public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "Beans.xml");
    MySampleBean bean = applicationContext.getBean(MySampleBean.class);

    System.out.println(bean.getPropToBeInsertedFromPropsFile());
    System.out.println(bean.getPropToBeInsertedFromXml());
  }

}
