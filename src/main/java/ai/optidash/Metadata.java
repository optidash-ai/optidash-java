package ai.optidash;

import java.util.Map;

public interface Metadata {

    <T> T get(String name);

    Dynamic getOutput();

    Dynamic getInput();

    Map<String, Object> asMap();

}
