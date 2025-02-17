package acme.custom.extension.exception.handler.runtime.external.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

/** Retrieve response coming from NFS Api */
@RegisterForReflection
public class CustomerApiStatusResponseError implements ResponseError {

  private String status;
  private String message;
  private String path;
  private String traceId;
  private String details;

  /**
   * Constructor with all parameters.
   *
   * @param status exception status
   * @param message exception message
   * @param path exception path
   * @param traceId exception trace id
   */
  public CustomerApiStatusResponseError(
      String status, String message, String path, String traceId) {
    this.status = status;
    this.message = message;
    this.path = path;
    this.traceId = traceId;
  }

  /** Return status retrieved */
  public String getStatus() {
    return status;
  }

  /** Return message retrieved */
  public String getMessage() {
    return message;
  }

  /** Return path retrieved */
  public String getPath() {
    return path;
  }

  /** Return traceId retrieved */
  public String getTraceId() {
    return traceId;
  }

  /** Return details retrieved */
  public String getDetails() {
    return details;
  }
}
