package ai.optidash.support;

import java.util.Map;

import ai.optidash.Dynamic;
import ai.optidash.Metadata;

class DynamicMetadata implements Metadata {
    private final Dynamic dynamic;

    public static Metadata of(Dynamic dynamic) {
        return new DynamicMetadata(dynamic);
    }

    private DynamicMetadata(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public <T> T get(String name) {
        return dynamic.get(name);
    }

    @Override
    public Dynamic getOutput() {
        return Dynamic.of((Map<String, Object>) get("output"));
    }

    @Override
    public Dynamic getInput() {
        return Dynamic.of((Map<String, Object>) get("input"));
    }

    @Override
    public Map<String, Object> asMap() {
        return dynamic.asMap();
    }
}
