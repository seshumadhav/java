package com.smc.threads.daemonity;

import java.util.Date;

/**
 * @author smc
 */
public class MainSimpleThreadNonDaemonForever {

  public static void main(String[] args) {
    String name = "main() - " + "main():Simple-Thread:NonDaemon:Forever";
    System.out.println(name + " started");

    Runnable aRunnable = new NonDaemonForever();

    Thread t1 = new Thread(aRunnable);
    // t1.setDaemon(true);
    t1.start();

    System.out.println(name + " > " + new Date());
    System.out.println(name + " ended.");
  }
}

class NonDaemonForever implements Runnable {

  @Override
  public void run() {
    String name = "Thread-" + "NonDaemon:Forever-For-Simple:main()";
    System.out.println(name + " started");

    while (true) {
      System.out.println(name + " > " + new Date());
    }

    //     System.out.println(name + " ended"); // Unreachable code
  }

}
