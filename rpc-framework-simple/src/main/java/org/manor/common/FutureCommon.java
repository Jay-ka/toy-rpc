package org.manor.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author kanghuajie
 * @date 2022/4/6
 */
public class FutureCommon {
    private static final Map<String, CompletableFuture<Response>> futures = new HashMap<>();

    public static void putFuture(String requestId, CompletableFuture<Response> future) {
        futures.put(requestId, future);
    }

    public static void completeFuture(String requestId, Response response) {
        CompletableFuture<Response> removeFuture = futures.remove(requestId);
        removeFuture.complete(response);
    }
}
