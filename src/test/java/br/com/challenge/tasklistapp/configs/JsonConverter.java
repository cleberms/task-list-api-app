package br.com.challenge.tasklistapp.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum JsonConverter {
    ;

    public static <T> String toString(T object) {
        final ObjectMapper jsonMapper = new ObjectMapper();

        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
