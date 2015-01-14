import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * $ javac -cp rabbitmq-client.jar Send43.java Recv43.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Recv43
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a WORKER
 * 
 * New changes:
 * (1) One FanEx. N randomQueues, 1 standardQueue
 * 
 * Run 2 receivers
 * 
 * @author smc
 */
public class Recv43 {

  public static void main(String[] argv) throws java.io.IOException,
      java.lang.InterruptedException {

    Recv43.MyRunnable stickyRunnable = new Recv43().new MyRunnable();
    stickyRunnable.run();
    System.out.println("main() started");

    Channel channel = Send43.getChannel();
    String fanExName = Send43.Constants.fanEx;
    Send43.setUp(channel, fanExName);

    String queue2 = channel.queueDeclare().getQueue();
    channel.queueBind(queue2, fanExName, "");

    QueueingConsumer consumer = new QueueingConsumer(channel);
    String bindResult2 = channel.basicConsume(queue2, true, consumer);
    System.out.println(bindResult2);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      String routingKey = delivery.getEnvelope().getRoutingKey();
      System.out
          .println(" [x] Received '" + routingKey + "':'" + message + "'");
    }
  }

  public class MyRunnable implements Runnable {

    private QueueingConsumer consumer;
    private Channel          channel;

    public MyRunnable() throws IOException {
      init();
    }

    private void init() throws IOException {
      System.out.println("Thread started");

      channel = Send43.getChannel();
      String fanExName = Send43.Constants.fanEx;
      Send43.setUp(channel, fanExName);

      String queue1 = "all - 1601";
      channel.queueDeclare(queue1, false, false, false, null);
      channel.queueBind(queue1, fanExName, "");

      consumer = new QueueingConsumer(channel);
      String bindResult1 = channel.basicConsume(queue1, true, consumer);
      System.out.println(bindResult1);
    }

    @Override
    public void run() {
      while (true) {
        QueueingConsumer.Delivery delivery = null;
        try {
          delivery = consumer.nextDelivery();
        } catch (ShutdownSignalException | ConsumerCancelledException
            | InterruptedException e) {
          e.printStackTrace();
          return;
        }

        String message = new String(delivery.getBody());
        String routingKey = delivery.getEnvelope().getRoutingKey();
        System.out.println(" [x] Received '" + routingKey + "':'" + message
            + "'");
      }
    }

  }

}
