package ai.optidash.support;

import static ai.optidash.Dynamic.dynamic;

import java.io.IOException;

import ai.optidash.Metadata;
import ai.optidash.Response;

public class ExceptionResponse implements Response {
    private final Exception e;

    public ExceptionResponse(Exception e) {
        this.e = e;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public String getMessage() {
        return e.getMessage();
    }

    @Override
    public Metadata getMetadata() {
        return DynamicMetadata.of(dynamic()
                                      .set("message", e.getMessage())
                                      .set("success", false));
    }

}
