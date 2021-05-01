package com.looper_api.data_structures;

import com.looper_api.data_structures.impl.LooperArrayListImpl;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class LooperArrayListImplTest {

    @Test
    public void addShouldIncreaseCount() {
        LooperArrayListImpl<Integer> numbers = new LooperArrayListImpl<>();
        numbers.add(1);
        assertEquals(1, numbers.size());
    }

    @Test
    public void addShouldIncreaseSize() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }

        assertEquals(100, strings.size());
    }

    @Test
    public void removeShouldReduceSize() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }
        int n = 50;
        while (n-- > 0) {
            strings.remove(0);
        }

        assertEquals(50, strings.size());
    }

    @Test
    public void testIndexOfShouldReturnMinusOneForEmptyList() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        assertEquals(-1, strings.indexOf("1"));
    }

    @Test
    public void testIndexOfShouldReturnCorrectIndex() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }

        assertEquals(73, strings.indexOf("73"));
    }

    @Test
    public void testContainsReturnFalseForEmptyList() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        assertFalse(strings.contains("1"));
        strings.add("1");
        assertTrue(strings.contains("1"));
    }

    @Test
    public void testContainsReturnTrue() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }
        assertTrue(strings.contains("73"));
    }

    @Test
    public void testSizeShouldReturnZeroEmptyList() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();
        assertEquals(0, strings.size());
        strings.add("SingleElement");
        assertEquals(1, strings.size());
    }

    @Test
    public void testSizeShouldReturnOneHundred() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }

        assertEquals(100, strings.size());
    }

    @Test
    public void testIsEmptyReturnTrueEmptyList() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        assertTrue(strings.isEmpty());
    }

    @Test
    public void testIsEmptyShouldReturnFalse() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }
        assertTrue(new LooperArrayListImpl<>().isEmpty());
        assertFalse(strings.isEmpty());
    }

    @Test
    public void testIterator() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
            stringBuilder.append(i);
        }

        String values = stringBuilder.toString();

        Iterator<String> iter = strings.iterator();
        assertNotNull(iter);
        int counter = 0;
        for (iter = strings.iterator(); iter.hasNext(); ) {
            if (values.contains(iter.next())) {
                counter++;
            } else {
                counter = -1;
                break;
            }
        }

        assertEquals(strings.size(), counter);
    }

    @Test
    public void testRemoveShouldReturnCorrectElementsAndShiftTheRemaining() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
            stringBuilder.append(i);
        }

        String values = stringBuilder.toString();

        String removed = strings.remove(0);
        assertNotNull(removed);
        int index = 0;
        for (int i = 0; i < values.length(); i++) {
            assertEquals(values.charAt(i), removed.charAt(index++));
            if (index == removed.length() && i < values.length() - 1) {
                index = 0;
                removed = strings.remove(0);
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveShouldThrowWithInvalidIndex() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }

        strings.remove(strings.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveShouldThrowWithInvalidIndexZero() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        strings.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveShouldThrowWithInvalidIndexMinusOne() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }

        strings.remove(-1);
    }

    @Test
    public void testGetShouldReturnTheCorrectElement() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }
        assertEquals("99", strings.get(99));
    }

    @Test
    public void testSetShouldChangeTheElement() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
        }

        strings.set(99, "666");
        assertEquals("666", strings.get(99));
    }

    @Test
    public void addAndGetMultipleElements() {
        LooperArrayListImpl<Integer> list = new LooperArrayListImpl<Integer>();

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (Integer i = 0; i < 100; i++) {
            assertEquals(i, list.get(i));
        }
    }

    @Test
    public void testInsertShouldSetCorrectlyAndShiftRestToTheRight() {
        LooperArrayListImpl<String> strings = new LooperArrayListImpl<>();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i));
            if (i == 50) {
                stringBuilder.append("666");
            }
            stringBuilder.append(i);
        }

        strings.add(50, "666");

        assertEquals("666", strings.get(50));

        String values = stringBuilder.toString();

        StringBuilder sb = new StringBuilder();

        for (String number : strings) {
            sb.append(number);
        }

        assertEquals(values, sb.toString());
    }

    @Test
    public void testConvertToCsvWorksProperly() {

        Integer[] inputNumbers = new Integer[]{1, 2, 3};
        LooperArrayList numbers = new LooperArrayListImpl(inputNumbers);

        String csv = numbers.convertToCSV();

        assertEquals(csv, "o1, e2, o3");
    }

    @Test
    public void testFindLongestPalindromeWorksProperlyOddPalindrome() {

        LooperArrayList palindromeChecker = new LooperArrayListImpl("zzababcdedcba");
        String longestPalindrome = palindromeChecker.findLongestPalindrome();

        assertEquals(longestPalindrome, "abcdedcba");
    }

    @Test
    public void testFindLongestPalindromeWorksProperlyEvenPalindrome() {

        LooperArrayList palindromeChecker = new LooperArrayListImpl("zzababcdeedcba");
        String longestPalindrome = palindromeChecker.findLongestPalindrome();

        assertEquals(longestPalindrome, "abcdeedcba");
    }
}