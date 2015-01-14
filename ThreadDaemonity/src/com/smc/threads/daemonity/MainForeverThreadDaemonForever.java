package com.smc.threads.daemonity;

import java.util.Date;

/**
 * @author smc
 */
public class MainForeverThreadDaemonForever {

  public static void main(String[] args) {
    String name = "main() - " + "main():Forever-Thread:Daemon:Forever";
    System.out.println(name + " started");

    Runnable aRunnable = new DaemonForeverForForeverMain();

    Thread t1 = new Thread(aRunnable);
    t1.setDaemon(true);
    t1.start();

    while (true) {
      System.out.println(name + " > " + new Date());
    }

    // System.out.println(name + " ended"); // Unreachable code
  }
}

class DaemonForeverForForeverMain implements Runnable {

  @Override
  public void run() {
    String name = "Thread-" + "Daemon:Forever-For-Forever:main()";
    System.out.println(name + " started");

    while (true) {
      System.out.println(name + " > " + new Date());
    }

    //     System.out.println(name + " ended"); // Unreachable code
  }

}
