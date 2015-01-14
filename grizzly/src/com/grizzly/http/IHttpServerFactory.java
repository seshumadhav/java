package com.grizzly.http;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;

/**
 * @author smc
 */
public class IHttpServerFactory {

	private static final int DEFAULT_HTTP_PORT = 80;

	public static HttpServer createHttpServer(URI uri, IHttpHandler handler) {

		final String host = uri.getHost() == null ? NetworkListener.DEFAULT_NETWORK_HOST
		    : uri.getHost();
		final int port = uri.getPort() == -1 ? DEFAULT_HTTP_PORT : uri.getPort();

		final NetworkListener listener = new NetworkListener("IGrizzly", host, port);
		listener.setSecure(false);

		final HttpServer server = new HttpServer();
		server.addListener(listener);

		final ServerConfiguration config = server.getServerConfiguration();
		if (handler != null) {
			config.addHttpHandler(handler, uri.getPath());
		}

		config.setPassTraceRequest(true);
		return server;
	}
}
