package edu.escuelaing.framework;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private Map<String, String> queryParams = new HashMap<>();

    public Request(String queryString) {
        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }

    public String getValues(String key) {
        return queryParams.get(key);
    }
}
