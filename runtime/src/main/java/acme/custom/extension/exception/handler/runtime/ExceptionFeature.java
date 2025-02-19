package acme.custom.extension.exception.handler.runtime;

import acme.custom.extension.exception.handler.runtime.external.ApiResponseExceptionMapper;
import acme.custom.extension.exception.handler.runtime.internal.ThrowableMapper;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionFeature implements Feature {

  @Override
  public boolean configure(FeatureContext context) {
    context.register(ThrowableMapper.class);
    context.register(ApiResponseExceptionMapper.class);
    return true;
  }
}
