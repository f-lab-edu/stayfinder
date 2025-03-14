package com.vacation.platform.api.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EntityToDtoConverter {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static <E, D> D convertEntityToDto(E entity, Class<D> dtoClass) {
        if(entity != null) {
            return mapper.convertValue(entity, dtoClass);
        }
        return null;
    }
}
