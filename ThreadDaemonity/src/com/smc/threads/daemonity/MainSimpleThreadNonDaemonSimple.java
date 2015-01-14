package com.smc.threads.daemonity;

import java.util.Date;

/**
 * @author smc
 */
public class MainSimpleThreadNonDaemonSimple {

  public static void main(String[] args) {
    String name = "main() - " + "main():Simple-Thread:NonDaemon:Simple";
    System.out.println(name + " started");

    Runnable aRunnable = new NonDaemonSimple();

    Thread t1 = new Thread(aRunnable);
    // t1.setDaemon(true);
    t1.start();

    System.out.println(name + " > " + new Date());
    System.out.println(name + " ended.");
  }
}

class NonDaemonSimple implements Runnable {

  @Override
  public void run() {
    String name = "Thread-" + "NonDaemon:Simple-For-Simple:main()";
    System.out.println(name + " started");

    System.out.println(name + " > " + new Date());

    System.out.println(name + " ended");
  }

}
