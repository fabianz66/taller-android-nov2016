package com.fabian.tallernov2016.networking;

import com.fabian.tallernov2016.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabian on 11/20/16.
 */

public class Headers {

    private static Map<String,String> sDefaultHeaders = new HashMap<>();

    /**
     * Add a new header
     */
    public static void addUserHeaders(User user) {
        sDefaultHeaders.put("X-User-Email", user.getEmail());
        sDefaultHeaders.put("X-User-Token", user.getAuthToken());
    }

    /*
     * Get current headers
     */
    public static Map<String, String> getHeaders() {
        return sDefaultHeaders;
    }

    /**
     * Set default headers
     */
    public static void clear() {
        sDefaultHeaders.clear();
    }

}
