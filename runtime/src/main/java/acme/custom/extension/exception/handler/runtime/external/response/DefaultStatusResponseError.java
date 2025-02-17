package acme.custom.extension.exception.handler.runtime.external.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

/** Retrieve Response if an unknown response was found */
@RegisterForReflection
public class DefaultStatusResponseError implements ResponseError {

  private String status;
  private String message;
  private String path;
  private String traceId;
  private String details;

  /**
   * Constructor with all parameters.
   *
   * @param status exception code
   * @param message exception message
   * @param path exception path
   */
  public DefaultStatusResponseError(String status, String message, String path) {
    this.status = status;
    this.message = message;
    this.path = path;
    this.traceId = "";
    this.details = "";
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public String getTraceId() {
    return traceId;
  }

  @Override
  public String getDetails() {
    return details;
  }
}
