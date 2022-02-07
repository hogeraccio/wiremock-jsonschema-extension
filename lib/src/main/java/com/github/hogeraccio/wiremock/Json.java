package com.github.hogeraccio.wiremock;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Json {
    private static final InheritableThreadLocal<ObjectMapper> objectMapperHolder = new InheritableThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
            objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, false);
            objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
            objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
            return objectMapper;
        }
    };

    private Json() {
    }

    public static <T> T read(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException processingException) {
            throw new Error(processingException);
        }
    }

    public static <T> T read(String json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException processingException) {
            throw new Error(processingException);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapperHolder.get();
    }

    public static JsonNode node(String json) {
        return read(json, JsonNode.class);
    }
}
