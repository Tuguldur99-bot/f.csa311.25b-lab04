package edu.cmu.cs.cs214.rec02;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link IntQueue} implementations.
 *
 * This suite tests both {@link LinkedIntQueue} and {@link ArrayIntQueue}.
 * 
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        // Comment/uncomment to switch between implementations
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = List.of(1, 2, 3);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNonEmptyQueue() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.peek());
        assertEquals(Integer.valueOf(1), mQueue.peek());  // Should remain unchanged
    }

    @Test
    public void testClear() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
    }

    @Test
    public void testEnsureCapacity() {
        for (int i = 0; i < 16; i++) {  
            mQueue.enqueue(i);
        }
        assertEquals(16, mQueue.size());

        // Trigger a resize
        mQueue.enqueue(16);
        assertEquals(17, mQueue.size());
        assertEquals(Integer.valueOf(0), mQueue.peek());

        // Ensure correct order after resizing
        for (int i = 0; i <= 16; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testSequentialOperations() {
        assertTrue(mQueue.isEmpty());

        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());

        mQueue.enqueue(3);
        assertEquals(Integer.valueOf(2), mQueue.peek());

        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());

        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());  // First element remains at front
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testResizePreservesOrder() {
        for (int i = 1; i <= 15; i++) {
            mQueue.enqueue(i);
        }
        assertEquals(15, mQueue.size());

        // Resize should happen here
        mQueue.enqueue(16);
        assertEquals(16, mQueue.size());

        // Dequeue should return elements in correct order
        for (int i = 1; i <= 16; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        assertTrue(mQueue.isEmpty());
    }
    @Test
public void testWrapAroundResize() {
    ArrayIntQueue queue = new ArrayIntQueue();
    
    // Step 1: Fill queue to capacity (assuming default size is 10)
    for (int i = 0; i < 10; i++) {
        queue.enqueue(i);
    }

    // Step 2: Remove some elements (move `head` forward)
    queue.dequeue();  // Removes 0
    queue.dequeue();  // Removes 1
    queue.dequeue();  // Removes 2

    // Step 3: Add more elements to force wraparound
    queue.enqueue(10);
    queue.enqueue(11);
    queue.enqueue(12);

    // Step 4: Add another element to force **resizing**
    queue.enqueue(13);  // Triggers the resize

    // Step 5: Ensure elements are correctly copied
    assertEquals(Integer.valueOf(3), queue.dequeue());  // Next expected element
    assertEquals(Integer.valueOf(4), queue.dequeue());  // Ensures correct shift

    // Step 6: Verify all elements remain in correct order
    for (int i = 5; i <= 13; i++) {
        assertEquals(Integer.valueOf(i), queue.dequeue());
    }

    // Queue should be empty now
    assertTrue(queue.isEmpty());
}

}
