package com.smc.threads.daemonity;

import java.util.Date;

/**
 * @author smc
 */
public class MainSimpleThreadDaemonForever {

  public static void main(String[] args) {
    String name = "main() - " + "main():Simple-Thread:Daemon:Forever";
    System.out.println(name + " started");

    Runnable aRunnable = new DaemonForever();

    Thread t1 = new Thread(aRunnable);
    t1.setDaemon(true);
    t1.start();

    // main() has a small task
    System.out.println(name + " > " + new Date());
    System.out.println(name + " ended.");
  }
}

class DaemonForever implements Runnable {

  @Override
  public void run() {
    String name = "Thread-" + "Daemon:Forever-For-Simple:main()";
    System.out.println(name + " started");

    while (true) {
      System.out.println(name + " > " + new Date());
    }

    //     System.out.println(name + " ended"); // Unreachable code
  }

}
