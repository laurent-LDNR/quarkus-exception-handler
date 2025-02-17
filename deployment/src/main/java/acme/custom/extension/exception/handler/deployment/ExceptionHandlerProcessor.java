package acme.custom.extension.exception.handler.deployment;

import acme.custom.extension.exception.handler.runtime.ExceptionFeature;
import acme.custom.extension.exception.handler.runtime.ExceptionHandlerRecorder;
import acme.custom.extension.exception.handler.runtime.external.ApiResponseExceptionMapper;
import acme.custom.extension.exception.handler.runtime.internal.CustomErrorMessage;
import acme.custom.extension.exception.handler.runtime.internal.CustomErrorMessageFactory;
import acme.custom.extension.exception.handler.runtime.internal.ThrowableMapper;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class ExceptionHandlerProcessor {

  private static final String FEATURE = "custom-exception-handler";

  @BuildStep
  FeatureBuildItem feature() {
    return new FeatureBuildItem(FEATURE);
  }

  @BuildStep
  AdditionalBeanBuildItem registerExceptionMappersAndBeans(ExceptionHandlerRecorder recorder) {
    recorder.recordExceptionMappers();
    return AdditionalBeanBuildItem.builder()
        .addBeanClasses(
            ApiResponseExceptionMapper.class,
            ThrowableMapper.class,
            CustomErrorMessageFactory.class,
            CustomErrorMessage.class,
            ExceptionFeature.class)
        .setUnremovable()
        .build();
  }
}
