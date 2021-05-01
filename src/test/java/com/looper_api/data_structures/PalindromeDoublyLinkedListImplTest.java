package com.looper_api.data_structures;

import com.looper_api.data_structures.impl.LooperArrayListImpl;
import com.looper_api.data_structures.impl.LooperDoublyLinkedListImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromeDoublyLinkedListImplTest {
    private LooperDoublyLinkedListImpl<String> list;

    @Before
    public void setUp() {
        this.list = new LooperDoublyLinkedListImpl<>();
        for (int i = 0; i < 100; i++) {
            list.addLast(String.valueOf(i));
        }
    }

    @Test
    public void testAddSingleElementInFront() {
        LooperDoublyLinkedListImpl<Integer> integers = new LooperDoublyLinkedListImpl<>();
        integers.addFirst(73);
        assertEquals(Integer.valueOf(73), integers.getFirst());
    }

    @Test
    public void testAddSingleElementInBack() {

        LooperDoublyLinkedListImpl<Integer> integers = new LooperDoublyLinkedListImpl<>();
        integers.addLast(73);
        assertEquals(Integer.valueOf(73), integers.getLast());
    }

    @Test
    public void testAddFirstShouldAddInFront() {
        this.list.addFirst("Slayer");
        assertEquals("Slayer", list.getFirst());
    }

    @Test
    public void testAddLastShouldAddAtTheEnd() {
        this.list.addLast("Slayer");
        assertEquals("Slayer", list.getLast());
    }

    @Test
    public void testRemoveFirstShouldRemoveTheFirstElement() {
        assertEquals("0", list.getFirst());
        assertEquals("0", list.removeFirst());
        assertEquals("1", list.getFirst());
    }

    @Test
    public void testRemoveLastShouldRemoveTheLAstElement() {
        assertEquals("99", list.getLast());
        assertEquals("99", list.removeLast());
        assertEquals("98", list.getLast());
    }

    @Test
    public void testGetFirstShouldReturnButNotRemove() {
        assertEquals("0", list.getFirst());
        assertEquals("0", list.getFirst());
        assertEquals(100, list.size());
    }

    @Test
    public void testGetLastShouldReturnButNotRemove() {
        assertEquals("99", list.getLast());
        assertEquals("99", list.getLast());
        assertEquals(100, list.size());
    }

    @Test
    public void testSize() {
        assertEquals(100, list.size());
        assertEquals(0, new LooperDoublyLinkedListImpl<>().size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(list.isEmpty());
        assertTrue(new LooperDoublyLinkedListImpl<>().isEmpty());
    }

    @Test
    public void testIterator() {
        int count = 0;
        for (String s : list) {
            assertEquals(s, String.valueOf(count++));
        }
    }

    @Test
    public void testFindLongestPalindromeOddLength() {
        String validPalindrome = "ABBABCDCBAZZ";
        LooperArrayList looperArrayList = new LooperArrayListImpl(validPalindrome);
        String longestPalindrome = looperArrayList.findLongestPalindrome();

        assertEquals("ABCDCBA", longestPalindrome);
    }

    @Test
    public void testFindLongestPalindromeEvenLength() {
        String validPalindrome = "ABBABCDDCBAZZ";
        LooperArrayList looperArrayList = new LooperArrayListImpl(validPalindrome);
        String longestPalindrome = looperArrayList.findLongestPalindrome();

        assertEquals("ABCDDCBA", longestPalindrome);
    }
}