package ai.optidash.support;

import java.lang.reflect.Type;
import java.util.Map;

import ai.optidash.Dynamic;
import ai.optidash.OperationConfiguration;

import com.google.gson.*;

public class Json {
    private final Gson gson = new GsonBuilder()
                                  .registerTypeAdapter(Dynamic.class, new DynamicTypeAdapter())
                                  .registerTypeAdapter(OperationConfiguration.class, new OperationConfigurationJsonSerializer())
                                  .create();

    public <T> T fromJson(String metadata, Class<T> destClass) {
        return gson.fromJson(metadata, destClass);
    }

    public String toJson(Object value) {
        return gson.toJson(value);
    }

    private static class OperationConfigurationJsonSerializer implements JsonSerializer<OperationConfiguration> {
        @Override
        public JsonElement serialize(OperationConfiguration src,
                                     Type typeOfSrc,
                                     JsonSerializationContext context) {
            return context.serialize(src.asMap());
        }
    }

    class DynamicTypeAdapter implements JsonSerializer<Dynamic>, JsonDeserializer<Dynamic> {
        @Override
        public Dynamic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Dynamic.of((Map<String, Object>) context.deserialize(json, Map.class));
        }

        @Override
        public JsonElement serialize(Dynamic src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.asMap());
        }
    }
}
