package acme.custom.extension.exception.handler.runtime.internal;

import io.quarkus.runtime.annotations.RegisterForReflection;

/** Error print to the user following the Customer recommendations. */
@RegisterForReflection
public record CustomErrorMessage(String code, String message, String description) {}
