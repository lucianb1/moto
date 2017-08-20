package ro.motorzz.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import ro.motorzz.core.exception.InvalidArgumentException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {

    private ObjectMapper objectMapper;

    public JsonUtils() {
        this(new ObjectMapper());
    }

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Object object) {
        if (Objects.isNull(object)) {
            throw new InvalidArgumentException("Object is null");
        } else {
            try {
                return this.objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException var3) {
                throw new InvalidArgumentException(var3);
            }
        }
    }

    public String toJsonAcceptNull(Object object) {
        return Objects.isNull(object) ? null : this.toJson(object);
    }

    public <T> T fromJson(String content, Class<T> valueType) {
        try {
            return this.objectMapper.readValue(content, valueType);
        } catch (IOException var4) {
            throw new InvalidArgumentException(var4);
        }
    }

    public <T> T fromJson(String content, JavaType valueType) {
        try {
            return this.objectMapper.readValue(content, valueType);
        } catch (IOException var4) {
            throw new InvalidArgumentException(var4);
        }
    }

    public <T> T fromJsonAcceptNull(String content, Class<T> valueType) {
        return content == null ? null : this.fromJson(content, valueType);
    }

    public <T> T fromJsonAcceptNull(String content, CollectionType collectionType) {
        if (Objects.isNull(content)) {
            return null;
        } else {
            try {
                return this.objectMapper.readValue(content, collectionType);
            } catch (IOException var4) {
                throw new InvalidArgumentException(var4);
            }
        }
    }

    public CollectionType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return this.objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public MapType constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return this.objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    public <T> T readValue(String content, JavaType valueType) {
        try {
            return this.objectMapper.readValue(content, valueType);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void registerModule(Module module) {
        this.objectMapper.registerModule(module);
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
