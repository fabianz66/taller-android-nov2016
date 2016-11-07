package com.fabian.tallernov2016.networking.volley;

import com.fabian.tallernov2016.models.User;
import com.fabian.tallernov2016.networking.BackendAccess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabian on 9/22/16.
 */
public class RequestHeaders {

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
    public static Map<String, String> getCurrentHeaders() {
        return sDefaultHeaders;
    }

    /**
     * Set default headers
     */
    public static void clearHeaders() {
        sDefaultHeaders.clear();
    }


}
