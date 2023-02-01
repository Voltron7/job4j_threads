package ru.job4j.threads.jcip;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(2);
    }
}