package com.vacation.platform.stayfinder.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityToDtoConverter {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <E, D> D convertEntityToDto(E entity, Class<D> dtoClass) {
        if(entity != null) {
            return mapper.convertValue(entity, dtoClass);
        }
        return null;
    }
}
