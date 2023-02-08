package ru.job4j.cache;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("Valeri");
        assertThat(cache.add(base)).isTrue();
        assertThat(cache.getById(base.getId())).isEqualTo(base);
    }

    @Test
    void whenAddIsUnsuccessful() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base base1 = new Base(1, 0);
        assertThat(cache.add(base)).isTrue();
        assertThat(cache.add(base1)).isFalse();
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("Valeri");
        Base base1 = new Base(1, 0);
        base1.setName("Dev");
        cache.add(base);
        cache.update(base1);
        assertThat(cache.getById(1).getName()).isEqualTo("Dev");
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base base1 = new Base(2, 0);
        cache.add(base);
        cache.add(base1);
        cache.delete(base1);
        List<Base> result = cache.getAll();
        assertThat(result).isEqualTo(List.of(base));
    }

    @Test
    void whenVersionsAreNotEqualThenException() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base base1 = new Base(1, 1);
        cache.add(base);
        assertThatThrownBy(() -> cache.update(base1))
                .isInstanceOf(OptimisticException.class)
                .hasMessage("Versions are not equal");
    }
}