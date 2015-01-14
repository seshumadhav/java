package com.grizzly.http;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.core.Application;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;

import com.grizzly.Utils;

/**
 * Jersey {@code Container} implementation based on Grizzly
 * {@link org.glassfish.grizzly.http.server.HttpHandler}.
 *
 * @author Jakub Podlesak (jakub.podlesak at oracle.com)
 * @author Libor Kramolis (libor.kramolis at oracle.com)
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public final class IHttpHandler extends HttpHandler implements Container {

  private static int index = 0;

  final ExecutorService executorService = GrizzlyExecutorService
      .createInstance(ThreadPoolConfig.defaultConfig().copy()
          .setCorePoolSize(4).setMaxPoolSize(4));

  private volatile ApplicationHandler appHandler;

  /**
   * Create a new Grizzly HTTP container.
   *
   * @param application
   *          JAX-RS / Jersey application to be deployed on Grizzly HTTP
   *          container.
   */
  public IHttpHandler(final Application application) {
  }

  @Override
  public void start() {
    super.start();
  }

  @Override
  public void service(final Request request, final Response response) {
    StringBuffer sb = new StringBuffer();
    sb.append("\nRequest number: " + index);
    sb.append("\nCurrent thread ID: " + Utils.getThreadName());
    sb.append("\nSuspended at: " + new Date().toLocaleString());

    int requestNumber = index++;

    // Instruct Grizzly to not flush response, once we exit service(...) method
    response.suspend();

    System.out.println(sb.toString());
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        try {
          sb.append("\nExecutor Service.Current thread ID: "
              + Utils.getThreadName());
          Thread.sleep(15 * 1000);
        } catch (Exception e) {
          response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        } finally {
          sb.append("\nRequest processing completed at: "
              + new Date().toLocaleString());
          try {
            response.getWriter().write(sb.toString());
          } catch (IOException e) {
            sb.append("\nError");
            response.setStatus(500, sb.toString());
          }

          System.out.println(sb.toString());
          response.resume();
        }
      }
    });

    System.out.println("HttpHandler:service() method ended for request "
        + requestNumber + "!");
  }

  @Override
  public ApplicationHandler getApplicationHandler() {
    return appHandler;
  }

  @Override
  public void destroy() {
    super.destroy();
    appHandler = null;
  }

  // Auto-generated stuff
  @Override
  public ResourceConfig getConfiguration() {
    return null;
  }

  @Override
  public void reload() {

  }

  @Override
  public void reload(ResourceConfig configuration) {
  }

}
