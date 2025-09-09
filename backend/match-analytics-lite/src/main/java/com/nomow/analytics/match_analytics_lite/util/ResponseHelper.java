package com.nomow.analytics.match_analytics_lite.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class ResponseHelper {

    public static Map<String, Object> success(String key, Object value) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put(key, value);
        return Collections.unmodifiableMap(response); 
    }


    public static Map<String, Object> success() {
        return Collections.unmodifiableMap(Collections.singletonMap("status", "success"));
    }

    public static Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        return Collections.unmodifiableMap(response); // make it immutable
    }

    private ResponseHelper() {}
}
