package ai.optidash;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

public class Dynamic {

    public static Dynamic dynamic() {
        return new Dynamic();
    }

    public static Dynamic of(Map<String, Object> map) {
        return new Dynamic().set(map);
    }


    private final Map<String, Object> properties = new HashMap<>();

    public Dynamic set(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    public <T> T get(String name) {
        return (T) properties.get(name);
    }

    public Dynamic set(Map<String, Object> value) {
        properties.putAll(value);
        return this;
    }

    public Map<String, Object> asMap() {
        return new HashMap<>(properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Dynamic dynamic = (Dynamic) o;
        return Objects.equal(properties, dynamic.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(properties);
    }
}
