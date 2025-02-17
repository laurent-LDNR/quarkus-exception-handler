package acme.custom.extension.exception.handler.runtime.external.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;

/** Exception class with rest exception attributes. */
@RegisterForReflection
public abstract class ApiStatusResponseException extends RuntimeException {

  /** status of the exception. */
  final String status;

  /** path of the exception. */
  final String path;

  /** trace id of the exception. */
  final String traceId;

  /** details of the exception. */
  final String details;

  /**
   * constructor with all parameters.
   *
   * @param message exception message
   * @param status exception status
   * @param path exception path
   * @param traceId exception trace id
   * @param details exception details
   */
  protected ApiStatusResponseException(
      String message, String status, String path, String traceId, String details) {
    super(message);
    this.status = status;
    this.path = path;
    this.traceId = traceId;
    this.details = details;
  }

  /**
   * get status.
   *
   * @return exception status
   */
  public String getStatus() {
    return status;
  }

  /**
   * get path.
   *
   * @return exception path
   */
  public String getPath() {
    return path;
  }

  /**
   * check if the exception is retryable.
   *
   * @return if the exception is retryable
   */
  public abstract boolean isRetryable();

  @Override
  public String toString() {
    return "ApiStatusResponseException [message="
        + this.getMessage()
        + ", status="
        + status
        + ", path="
        + path
        + "]";
  }
}
