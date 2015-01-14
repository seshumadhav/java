import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send6.java Recv6.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send6
 * .....
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a RPC Client
 * 
 * New changes:
 * (1) Creates a queue to hold RPC requests & waits on that queue
 * (2) Upon receiving a request, process and send reply with replyTo queue
 * 
 * @author smc
 * 
 */
public class Send6 {

  private static final String RPC_QUEUE_NAME   = "rpc_queue";

  private Connection          connection;
  private Channel             channel;
  private String              requestQueueName = "rpc_queue";
  private String              replyQueueName;
  private QueueingConsumer    consumer;

  public Send6() throws Exception {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	connection = factory.newConnection();
	channel = connection.createChannel();

	replyQueueName = channel.queueDeclare().getQueue();
	consumer = new QueueingConsumer(channel);
	channel.basicConsume(replyQueueName, true, consumer);
  }

  public String call(String message) throws Exception {
	String response = null;
	String corrId = UUID.randomUUID().toString();

	BasicProperties props = new BasicProperties.Builder().correlationId(corrId)
	    .replyTo(replyQueueName).build();

	channel.basicPublish("", requestQueueName, props, message.getBytes());

	while (true) {
	  QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	  if (delivery.getProperties().getCorrelationId().equals(corrId)) {
		response = new String(delivery.getBody(), "UTF-8");
		break;
	  }
	}

	return response;
  }

  public void close() throws Exception {
	connection.close();
  }

  public static void main(String[] argv) {
	Send6 fibonacciRpc = null;
	String response = null;
	try {
	  fibonacciRpc = new Send6();

	  System.out.println(" [x] Requesting fib(30)");
	  response = fibonacciRpc.call("30");
	  System.out.println(" [.] Got '" + response + "'");
	} catch (Exception e) {
	  e.printStackTrace();
	} finally {
	  if (fibonacciRpc != null) {
		try {
		  fibonacciRpc.close();
		} catch (Exception ignore) {
		}
	  }
	}
  }
}
