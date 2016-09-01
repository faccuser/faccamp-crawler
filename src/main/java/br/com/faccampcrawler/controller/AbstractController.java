package br.com.faccampcrawler.controller;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {
    private static final int SUCCESS_CODE = 0;
    private static final int FAIL_CODE = 1;
    private static final int ERROR_CODE = 2;

    Map buildSuccessResponse(Object response) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", SUCCESS_CODE);
        responseMap.put("response", response);
        return responseMap;
    }

    Map buildFailResponse(Object response) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", FAIL_CODE);
        responseMap.put("response", response);
        return responseMap;
    }

    Map buildErrorResponse(Object response) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", ERROR_CODE);
        responseMap.put("response", response);
        return responseMap;
    }
}
