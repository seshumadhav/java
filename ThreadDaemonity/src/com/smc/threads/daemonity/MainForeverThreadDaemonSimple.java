package com.smc.threads.daemonity;

import java.util.Date;

public class MainForeverThreadDaemonSimple {

  public static void main(String[] args) {
    String name = "main() - " + "main():Forever-Thread:Daemon:Simple";
    System.out.println(name + " started");

    Runnable aRunnable = new DaemonSimpleForForeverMain();

    Thread t1 = new Thread(aRunnable);
    t1.setDaemon(true);
    t1.start();

    while (true) {
      System.out.println(name + " > " + new Date());
    }

    // System.out.println("Main ended"); // Unreachable code
  }
}

class DaemonSimpleForForeverMain implements Runnable {

  @Override
  public void run() {
    String name = "Thread-" + "Daemon:Simple-For-Forever:main()";
    System.out.println(name + " started");

    System.out.println(name + " > " + new Date());

    System.out.println(name + " ended");
  }

}
