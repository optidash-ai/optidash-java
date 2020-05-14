package ai.optidash.support;

import ai.optidash.Dynamic;
import ai.optidash.Metadata;
import ai.optidash.Response;

public class JsonResponse implements Response {
    private final Dynamic dynamic;

    public JsonResponse(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public String getMessage() {
        return dynamic.get("message");
    }

    @Override
    public boolean isSuccessful() {
        return dynamic.get("success");
    }

    @Override
    public Metadata getMetadata() {
        return DynamicMetadata.of(dynamic);
    }

}
