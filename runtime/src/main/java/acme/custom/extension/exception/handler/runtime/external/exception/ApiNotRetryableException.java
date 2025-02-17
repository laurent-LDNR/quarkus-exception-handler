package acme.custom.extension.exception.handler.runtime.external.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;

/** Store the information about non retryable Exception. */
@RegisterForReflection
public class ApiNotRetryableException extends ApiStatusResponseException {

  /**
   * Constructor with all parameters.
   *
   * @param message exception message
   * @param status exception status
   * @param path exception path
   * @param traceId exception trace id
   * @param details exception details
   */
  public ApiNotRetryableException(
      String message, String status, String path, String traceId, String details) {
    super(message, status, path, traceId, details);
  }

  @Override
  public boolean isRetryable() {
    return false;
  }
}
