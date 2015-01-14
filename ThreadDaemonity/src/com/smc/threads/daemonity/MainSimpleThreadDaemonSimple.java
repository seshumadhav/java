package com.smc.threads.daemonity;

import java.util.Date;

/**
 * @author smc
 */
public class MainSimpleThreadDaemonSimple {

  public static void main(String[] args) {
    String name = "main() - " + "main():Simple-Thread:Daemon:Simple";
    System.out.println(name + " started");

    Runnable aRunnable = new DaemonSimple();

    Thread t1 = new Thread(aRunnable);
    t1.setDaemon(true);
    t1.start();

    System.out.println(name + " > " + new Date());
    System.out.println(name + " ended.");
  }
}

class DaemonSimple implements Runnable {

  @Override
  public void run() {
    String name = "Thread-" + "Daemon:Simple-For-Simple:main()";
    System.out.println(name + " started");

    System.out.println(name + " > " + new Date());

    System.out.println(name + " ended");
  }

}
