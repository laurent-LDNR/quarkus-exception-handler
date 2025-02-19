package acme.custom.extension.exception.handler.runtime.external;

import acme.custom.extension.exception.handler.runtime.external.exception.ApiNotRetryableException;
import acme.custom.extension.exception.handler.runtime.external.exception.ApiRetryableException;
import acme.custom.extension.exception.handler.runtime.external.exception.ApiStatusResponseException;
import acme.custom.extension.exception.handler.runtime.external.response.CustomerApiStatusResponseError;
import acme.custom.extension.exception.handler.runtime.external.response.DefaultStatusResponseError;
import acme.custom.extension.exception.handler.runtime.external.response.ResponseError;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;

/**
 * Mapper that allows to convert Rest Response to Exception.
 *
 * <p>use of default constructor with no arguments
 */
@RegisterForReflection
@Provider
@ApplicationScoped
public class ApiResponseExceptionMapper
    implements ResponseExceptionMapper<ApiStatusResponseException> {

  @Inject Logger logger;

  @Override
  public ApiStatusResponseException toThrowable(Response response) {
    ResponseError error = tryDeserializeResponse(response);
    logger.debugf("error: %s", error);
    if (isRetryableStatus(response.getStatus())) {
      return new ApiRetryableException(
          error.getMessage(),
          error.getStatus(),
          error.getPath(),
          error.getTraceId(),
          error.getDetails());
    }

    return new ApiNotRetryableException(
        error.getMessage(),
        error.getStatus(),
        error.getPath(),
        error.getTraceId(),
        error.getDetails());
  }

  /**
   * Check the API where the response comes from before serializing it
   *
   * @param response retrieves from the target API
   * @return serialized response
   */
  private ResponseError tryDeserializeResponse(Response response) {
    List<Class<? extends ResponseError>> responseErrorClasses =
        List.of(CustomerApiStatusResponseError.class, DefaultStatusResponseError.class);

    for (Class<? extends ResponseError> errorClass : responseErrorClasses) {
      try {
        return response.readEntity(errorClass);
      } catch (Exception e) {
        logger.errorf("read entity exception : " + e);
      }
    }
    return new DefaultStatusResponseError(
        String.valueOf(response.getStatus()), response.getStatusInfo().getReasonPhrase(), "");
  }

  /**
   * Check if the status is retryable or not
   *
   * @param status checked
   * @return boolean retryable
   */
  private boolean isRetryableStatus(int status) {
    return status == Status.INTERNAL_SERVER_ERROR.getStatusCode()
        || status == Status.GATEWAY_TIMEOUT.getStatusCode()
        || status == Status.SERVICE_UNAVAILABLE.getStatusCode();
  }
}
