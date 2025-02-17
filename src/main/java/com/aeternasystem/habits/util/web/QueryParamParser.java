package com.aeternasystem.habits.util.web;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParamParser {

    private QueryParamParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Map<String, String> toMap(String params) {
        return Arrays.stream(params.split("&"))
                .map(pair -> pair.split("=", 2))
                .filter(pair -> pair.length == 2)
                .collect(Collectors.toMap(
                        pair -> URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                        pair -> URLDecoder.decode(pair[1], StandardCharsets.UTF_8)
                ));
    }
}