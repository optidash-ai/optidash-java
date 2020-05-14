package ai.optidash;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OperationConfiguration {
    public static OperationConfiguration settings() {
        return new OperationConfiguration();
    }
    public static OperationConfiguration settings(String name, Object value) {
        return settings().set(name,value);
    }
    public static OperationConfiguration of(Map<String,Object> values) {
        final OperationConfiguration configuration = new OperationConfiguration();
        configuration.settings.putAll(values);
        return configuration;
    }

    private final Map<String, Object> settings = new HashMap<>();

    public OperationConfiguration set(String name, Object value) {
        settings.put(name, value);
        return this;
    }

    public Map<String,Object> asMap(){
        return Collections.unmodifiableMap(settings);
    }
}
