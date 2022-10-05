package com.flz.rm.sb.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T cast(byte[] bytes, TypeReference<T> type) {
        try {
            return OBJECT_MAPPER.convertValue(bytes, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T cast(String json, TypeReference<T> type) {
        try {
            return OBJECT_MAPPER.convertValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T cast(byte[] bytes, Class<T> type) {
        try {
            return OBJECT_MAPPER.convertValue(bytes, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T cast(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.convertValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String silentMarshal(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
