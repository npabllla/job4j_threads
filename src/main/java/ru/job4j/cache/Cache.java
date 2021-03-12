package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (integer, stored) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base resultBase = new Base(model.getId(), model.getVersion() + 1);
            resultBase.setName(model.getName());
            return resultBase;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(Base base) {
        return memory.get(base.getId());
    }
}