package acme.custom.extension.exception.handler.runtime.internal;

import acme.custom.extension.exception.handler.runtime.external.exception.ApiStatusResponseException;
import io.netty.channel.ConnectTimeoutException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.NoSuchElementException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.jboss.logging.Logger;

/**
 * Transform an exception to a rest response.
 *
 * <p>use of default constructor with no arguments
 */
@RegisterForReflection
@Provider
@ApplicationScoped
public class ThrowableMapper implements ExceptionMapper<Throwable> {

  @Inject Logger logger;

  @Inject CustomErrorMessageFactory customerErrorMessageFactory;

  @Override
  public Response toResponse(Throwable throwable) {

    Map<Class<? extends Throwable>, Response.Status> exceptionStatusMap =
        Map.of(
            NoSuchElementException.class, Response.Status.NOT_FOUND,
            TimeoutException.class, Response.Status.GATEWAY_TIMEOUT,
            ConnectTimeoutException.class, Response.Status.GATEWAY_TIMEOUT,
            BadRequestException.class, Response.Status.BAD_REQUEST,
            ConstraintViolationException.class, Response.Status.BAD_REQUEST,
            UnsupportedOperationException.class, Response.Status.NOT_IMPLEMENTED,
            ForbiddenException.class, Response.Status.FORBIDDEN,
            UnauthorizedException.class, Response.Status.UNAUTHORIZED);

    if (throwable instanceof WebApplicationException webEx) {
      Response originalErrorResponse = webEx.getResponse();
      return buildResponse(originalErrorResponse.getStatusInfo().toEnum(), throwable);
    }

    if (throwable instanceof ApiStatusResponseException apiEx) {
      return buildResponse(
          Response.Status.fromStatusCode(Integer.parseInt(apiEx.getStatus())), apiEx);
    }

    return exceptionStatusMap.entrySet().stream()
        .filter(entry -> entry.getKey().isInstance(throwable))
        .findFirst()
        .map(entry -> buildResponse(entry.getValue(), throwable))
        .orElse(buildResponse(Response.Status.INTERNAL_SERVER_ERROR, throwable));
  }

  private Response buildResponse(Response.Status status, Throwable throwable) {
    return customerErrorMessageFactory.build(status, throwable).get();
  }
}
