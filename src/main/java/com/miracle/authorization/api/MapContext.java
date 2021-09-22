package com.miracle.authorization.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author Administrator
 * Created at 2021/9/22 15:36
 **/
@SuppressWarnings("unchecked")
public class MapContext implements Context {

    private Map<String, Object> map = new ConcurrentHashMap<>();


    @Override
    public <T> Optional<T> get(ContextKey<T> key) {
        return Optional.ofNullable(map.get(key.getKey()))
                       .map(v -> (T)v);
    }


    @Override
    public <T> T getOrDefault(ContextKey<T> key, Supplier<? extends T> defaultValue) {
        return (T)map.computeIfAbsent(key.getKey(), ignore -> defaultValue.get());
    }

    @Override
    public <T> void put(ContextKey<T> key, T value) {
        map.put(key.getKey(), value);
    }

    @Override
    public <T> T remove(ContextKey<T> key) {
        return (T)map.remove(key.getKey());
    }

    @Override
    public Map<String, Object> getAll() {
        return new HashMap<>(map);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
