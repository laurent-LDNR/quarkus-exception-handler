package acme.custom.extension.exception.handler.runtime.internal;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.util.Optional;

/**
 * Transform an exception to a Customer Error message.
 *
 * <p>use of default constructor with no arguments
 */
@ApplicationScoped
@RegisterForReflection
public class CustomErrorMessageFactory {

  /**
   * Transform an exception to a Customer Error message.
   *
   * @param status status of the response
   * @param throwable the exception
   * @return Response in customer error recommendation
   */
  public Optional<Response> build(Response.Status status, Throwable throwable) {
    return Optional.of(
        Response.status(status)
            .entity(
                new CustomErrorMessage(
                    Integer.toString(status.getStatusCode()),
                    status.getReasonPhrase(),
                    throwable.getMessage()))
            .build());
  }
}
