package acme.custom.extension.exception.handler.runtime.external.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

/** Interface implemented in Api responses errors */
@RegisterForReflection
public interface ResponseError {

  /**
   * Retrieve response's message sent by differents APIs
   *
   * @return message
   */
  public String getMessage();

  /**
   * Retrieve response's path sent by differents APIs
   *
   * @return path
   */
  public String getPath();

  /**
   * Retrieve response's trace id sent by differents APIs
   *
   * @return trace id
   */
  public String getTraceId();

  /**
   * Retrieve response's status code sent by differents APIs
   *
   * @return status
   */
  public String getStatus();

  /**
   * Retrieve response's details code sent by differents APIs
   *
   * @return details
   */
  public String getDetails();
}
