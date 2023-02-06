package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferFiveElementsAndPollFourElements() {
        var queue = new SimpleBlockingQueue<Integer>(2);
        var producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.offer(i);
            }
        });
        var consumer = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                queue.poll();
            }
        });
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(queue.poll()).isEqualTo(4);
    }

    @Test
    public void when2ConsumersAnd1Producer() {
        var queue = new SimpleBlockingQueue<>(2);
        var producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
            }
        });
        var consumer = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                queue.poll();
            }
        });
        var consumer1 = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                queue.poll();
            }
        });
        producer.start();
        consumer.start();
        consumer1.start();
        try {
            producer.join();
            consumer.join();
            consumer1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(queue.poll()).isEqualTo(8);
    }
}