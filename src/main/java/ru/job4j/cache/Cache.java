package ru.job4j.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base result = memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base updated = new Base(model.getId(), model.getVersion() + 1);
            updated.setName(model.getName());
            return updated;
        });
        return result != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base getById(int id) {
        return memory.get(id);
    }

    public List<Base> getAll() {
        return new ArrayList<>(memory.values());
    }
}
