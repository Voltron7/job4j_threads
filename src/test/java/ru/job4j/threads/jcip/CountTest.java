package ru.job4j.threads.jcip;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        /* Создаем счетчик. */
        final Count count = new Count();
        /* Создаем нити. */
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(2);
    }
}