import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

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
 * (1) We are trying routing w.t.h.o Direct exchange
 * (2) Gets severities(of logs) as parameters and creates separate queues for each severity
 * (3) Each queue declared will have a random name
 * (4) Binds queues created to the channel
 * 
 * Run one receiver for one severity for best results
 * 
 * @author smc
 */
public class Recv4 {

  private static final String EXCHANGE_DIRECT = "direct_logs";

  public static void main(String[] argv) throws java.io.IOException,
      java.lang.InterruptedException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");
    String queueName = channel.queueDeclare().getQueue();

    if (argv.length < 1) {
      System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
      System.exit(1);
    }

    for (String arg : argv) {
      System.out.println(" [*] Waiting for messages of type '" + arg
          + "'. To exit press CTRL+C");
      channel.queueBind(queueName, EXCHANGE_DIRECT, arg);
    }

    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName, true, consumer);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      String routingKey = delivery.getEnvelope().getRoutingKey();
      System.out
          .println(" [x] Received '" + routingKey + "':'" + message + "'");
    }
  }

  private static void doWork(String task) throws InterruptedException {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        Thread.sleep(1000);
      }
    }
  }

}
