package com.demicon.techtask.utils;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpHeaders;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isEmpty(String value) {
        return (null == value || value.isEmpty());
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?,?> map) {
        return (map == null || map.isEmpty());
    }

    public static HttpHeaders createEntityAlert(String entityName, int entityNumber) {
        return createAlert(entityNumber + " " + entityName + " created successfully"  );
    }

    public static HttpHeaders createAlert(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-demicon-alert", message);
        return headers;
    }
}
