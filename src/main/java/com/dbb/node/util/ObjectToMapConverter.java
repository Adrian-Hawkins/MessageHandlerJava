package com.dbb.node.util;

import com.dbb.node.base.SagaBase;
import com.dbb.node.logging.LoggerConfig;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ObjectToMapConverter {

    private static final Logger logger = LoggerConfig.getLogger(SagaBase.class);
    public static Map<String, Object> convertToMapReflection(Object obj) {
        Map<String, Object> map = new HashMap<>();

        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        }

        return map;
    }
    public static Map<String, Object> convertToMapStream(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toMap(
                        Field::getName,
                        field -> {
                            try {
                                return field.get(obj);
                            } catch (IllegalAccessException e) {
                                return null;
                            }
                        }
                ));
    }
    public static void printObjectAsMap(Object obj) {
        Map<String, Object> map = convertToMapReflection(obj);
        map.forEach((key, value) -> logger.info(key + " : " + value));
    }
}