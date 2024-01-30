package ru.kodarovs.joperation.concurrency;

import org.junit.jupiter.api.Test;
import ru.kodarovs.joperation.stream.Student;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockingQueueTest {
    @Test
    public void testBlockingQueue() throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(5);
        Student student1 = new Student("Student1", Map.of("Math", 90, "Physics", 85));
        Student student2 = new Student("Student2", Map.of("Math", 95, "Physics", 88));
        Student student3 = new Student("Student3", Map.of("Math", 88, "Chemistry", 92));
        Student student4 = new Student("Student4", Map.of("Physics", 78, "Chemistry", 85));

        queue.enqueue(student1);
        queue.enqueue(student2);
        queue.enqueue(student3);
        queue.enqueue(student4);
        assertEquals(4, queue.size());

        assertEquals(student1, queue.dequeue());
        assertEquals(student2, queue.dequeue());
        assertEquals(2, queue.size());
    }

    @Test
    public void testConcurrentEnqueue() throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(10);
        Student student1 = new Student("Student1", Map.of("Math", 90, "Physics", 85));
        Student student2 = new Student("Student2", Map.of("Math", 95, "Physics", 88));
        Student student3 = new Student("Student3", Map.of("Math", 88, "Chemistry", 92));
        Student student4 = new Student("Student4", Map.of("Physics", 78, "Chemistry", 85));

        Thread thread1 = new Thread(() -> {
            try {
                queue.enqueue(student1);
                queue.enqueue(student2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                queue.enqueue(student3);
                queue.enqueue(student4);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(4, queue.size());
    }
}
