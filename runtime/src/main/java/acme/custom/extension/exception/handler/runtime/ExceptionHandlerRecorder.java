package acme.custom.extension.exception.handler.runtime;

import acme.custom.extension.exception.handler.runtime.external.ApiResponseExceptionMapper;
import acme.custom.extension.exception.handler.runtime.internal.ThrowableMapper;
import io.quarkus.runtime.annotations.Recorder;
import jakarta.enterprise.inject.spi.CDI;

@Recorder
public class ExceptionHandlerRecorder {

  public void recordExceptionMappers() {
    CDI.current().select(ThrowableMapper.class).get();
    CDI.current().select(ApiResponseExceptionMapper.class).get();
  }
}
