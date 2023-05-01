package com.hw.securitydemo5.function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Helper {
    public static  <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Object> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
