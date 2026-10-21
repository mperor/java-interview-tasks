package pl.mperor.interview.tasks.list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple Linked List 🔗
 *
 * <p>Implement your own singly linked list.
 * The implementation can be very simple - it doesn't have to implement all methods of the {@link List} interface.
 * It should allow to:
 * <ul>
 *     <li>add elements (including <code>null</code>) to the end of the list,</li>
 *     <li>get an element by index and get the first/last element,</li>
 *     <li>check if the list contains an element and remove it,</li>
 *     <li>check the size of the list, clear it and iterate through its elements.</li>
 * </ul>
 * Behave like {@link java.util.LinkedList} in edge cases (e.g. accessing elements of an empty list).
 */
public class SimpleLinkedListTest {

    @Test
    public void testEmptyList() {
        var list = new SimpleLinkedList<String>();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertFalse(list.iterator().hasNext());
        assertThrows(NoSuchElementException.class, list::getFirst);
        assertThrows(NoSuchElementException.class, list::getLast);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    public void testAddAndGetElements() {
        var list = listOf("First", "Second", "Third");

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
        assertEquals("First", list.get(0));
        assertEquals("Second", list.get(1));
        assertEquals("Third", list.get(2));
        assertEquals("First", list.getFirst());
        assertEquals("Third", list.getLast());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    public void testIterateThroughElements() {
        var list = listOf("First", "Second", "Third");

        assertEquals(List.of("First", "Second", "Third"), toJavaList(list));

        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testNullElements() {
        var list = listOf("First", null, "Third");

        assertEquals(3, list.size());
        assertNull(list.get(1));
        assertTrue(list.contains(null));
        assertTrue(list.contains("Third"));
        assertTrue(list.remove(null));
        assertFalse(list.contains(null));
        assertEquals(List.of("First", "Third"), toJavaList(list));
    }

    @Test
    public void testContains() {
        var list = listOf("First", "Second", "Third");

        assertTrue(list.contains("Second"));
        assertFalse(list.contains("Fourth"));
        assertFalse(list.contains(null));
    }

    @Test
    public void testRemoveElements() {
        var list = listOf("First", "Second", "Third", "Fourth");

        assertTrue(list.remove("Second"));
        assertEquals(List.of("First", "Third", "Fourth"), toJavaList(list));

        assertTrue(list.remove("Fourth"));
        assertEquals(List.of("First", "Third"), toJavaList(list));
        assertEquals("Third", list.getLast());

        assertTrue(list.remove("First"));
        assertEquals(List.of("Third"), toJavaList(list));
        assertEquals("Third", list.getFirst());

        assertFalse(list.remove("Fifth"));
        assertTrue(list.remove("Third"));
        assertTrue(list.isEmpty());

        list.add("New");
        assertEquals("New", list.getFirst());
        assertEquals("New", list.getLast());
    }

    @Test
    public void testRemoveConsecutiveElementsWithIterator() {
        var list = listOf("A", "B", "C", "D", "E");

        Iterator<String> iterator = list.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
        while (iterator.hasNext()) {
            String value = iterator.next();
            if (!value.equals("A") && !value.equals("E")) {
                iterator.remove();
            }
        }
        assertEquals(List.of("A", "E"), toJavaList(list));
        assertEquals(2, list.size());

        Iterator<String> secondIterator = list.iterator();
        secondIterator.next();
        secondIterator.remove();
        assertThrows(IllegalStateException.class, secondIterator::remove);

        assertEquals(List.of("E"), toJavaList(list));
        assertEquals(1, list.size());
        assertEquals("E", list.getFirst());
        assertEquals("E", list.getLast());
    }

    @Test
    public void testClear() {
        var list = listOf("First", "Second", "Third");
        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertThrows(NoSuchElementException.class, list::getFirst);
    }

    @SafeVarargs
    private static <T> SimpleLinkedList<T> listOf(T... values) {
        var list = new SimpleLinkedList<T>();
        for (T value : values) {
            assertTrue(list.add(value));
        }
        return list;
    }

    private static <T> List<T> toJavaList(SimpleLinkedList<T> list) {
        List<T> result = new ArrayList<>();
        list.forEach(result::add);
        return result;
    }
}
