package com.grizzly;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;

import com.grizzly.http.IHttpHandler;
import com.grizzly.http.IHttpServerFactory;

public class GrizzlyMain {

	private static HttpServer httpServer;

	private static void startHttpServer(int port) throws IOException {
		URI uri = getBaseURI(port);

		httpServer = IHttpServerFactory.createHttpServer(uri,
				new IHttpHandler(null));

		TCPNIOTransport transport = getListener(httpServer).getTransport();

		System.out.println("Num SelectorRunners: "
				+ transport.getSelectorRunnersCount());
		System.out.println("Num WorkerThreads: "
				+ transport.getWorkerThreadPoolConfig().getCorePoolSize());

		httpServer.start();
		System.out.println("Server Started @" + uri.toString());
	}

	public static void main(String[] args) throws InterruptedException,
	IOException, InstantiationException, IllegalAccessException,
	ClassNotFoundException {
		startHttpServer(7777);

		System.out.println("Press any key to stop the server...");
		System.in.read();
	}

	private static NetworkListener getListener(HttpServer httpServer) {
		return httpServer.getListeners().iterator().next();
	}

	private static URI getBaseURI(int port) {
		return UriBuilder.fromUri("https://0.0.0.0/").port(port).build();
	}

}
