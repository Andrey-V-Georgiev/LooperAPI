package com.looper_api.data_structures.impl;

import com.looper_api.data_structures.LooperArrayList;

import java.util.Arrays;
import java.util.Iterator;

public class LooperArrayListImpl<E> implements LooperArrayList<E> {

    private static final int INITIAL_SIZE = 4;
    private Object[] elements;
    private int size;
    private int capacity;


    public LooperArrayListImpl() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
        this.capacity = INITIAL_SIZE;
    }

    public LooperArrayListImpl(Integer[] numbers) {
        Object[] objects = new Object[numbers.length * 2];
        for (int i = 0; i < numbers.length; i++) {
            objects[i] = numbers[i];
        }
        this.elements = objects;
        this.size = numbers.length;
        this.capacity = this.size * 2;
    }

    public LooperArrayListImpl(String palindromeString) {
        Object[] objects = new Object[palindromeString.length() * 2];
        for (int i = 0; i < palindromeString.length(); i++) {
            objects[i] = palindromeString.charAt(i);
        }
        this.elements = objects;
        this.size = palindromeString.length();
        this.capacity = this.size * 2;
    }

    @Override
    public boolean add(E element) {

        /* Resize array length when reach full capacity */
        this.increaseTheSizeIfNeeded();

        /* Add element on last position */
        this.elements[this.size] = element;

        /* Increase the size of th array according the new added element */
        this.size++;

        return true;
    }


    @Override
    public boolean add(int index, E element) {

        /* Validate index */
        this.validateIndex(index);

        /* Resize array length when reach full capacity */
        this.increaseTheSizeIfNeeded();

        /* Shift all elements to the right from this index to the end */
        this.shiftRight(index);

        /* Set the new element to the index */
        this.elements[index] = element;

        /* Increase the size of th array according the new added element */
        this.size++;

        /* Element is successfully added on index */
        return true;
    }

    @Override
    public E get(int index) {

        /* Validate index */
        this.validateIndex(index);

        /* Return the element on this index */
        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {

        /* Validate index */
        this.validateIndex(index);

        /* Resize array length when reach full capacity */
        this.increaseTheSizeIfNeeded();

        /* Find the old value on this index */
        Object oldElement = this.elements[index];

        /* Set new value to this index */
        this.elements[index] = element;

        /* Return old value which is replaced now */
        return (E) oldElement;
    }

    @Override
    public E remove(int index) {

        /* Validate index */
        this.validateIndex(index);

        /* Find the old value on this index */
        Object oldElement = this.elements[index];

        /* Shift all elements to the left from this index to the end */
        this.shiftLeft(index);

        /* Decrease the size of th array according the removed element */
        this.size--;

        /* Decrease array length when size is 3 times less than capacity */
        this.decreaseTheSize();

        /* Return old value which is replaced now */
        return (E) oldElement;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {

        /* If iterating over the array find same element return his index */
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        /* If no match return -1 */
        return -1;
    }

    @Override
    public boolean contains(E element) {
        if (this.indexOf(element) < 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }

    @Override
    public String convertToCSV() {
        String csvString = "";

        if (this.size > 0) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < this.size; i++) {
                int num = Integer.parseInt(this.elements[i].toString());
                String prefix = num % 2 == 0 ? "e" : "o";
                sb.append(String.format("%s%d", prefix, num)).append(", ");
            }
            csvString = sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1).toString();
        }
        return csvString;
    }

    @Override
    public String findLongestPalindrome() {

        /* The maximal length of palindrome to this moment */
        int longestPalindromeLength = 1;

        /* Start index of latest palindrome with maximal length */
        int startIndex = 0;

        /* Start searching palindromes through all the collection */
        for (int i = 0; i < this.size; i++) {

            /* Start new substring */
            for (int j = i; j < this.size; j++) {
                boolean isValidPalindrome = true;
                int currentSubstringLength = j - i + 1;

                /*
                 * Check current substring is valid palindrome
                 * walk through the substring
                 * starts from beginning to the core and from end to the core
                 * comparing the pairs for equality
                 */
                for (int k = 0; k < currentSubstringLength / 2; k++) {

                    /* If is not valid palindrome flag this substring as falsy */
                    if (this.get(i + k) != this.get(j - k)) {
                        isValidPalindrome = false;
                    }
                }

                /*
                 * If current substring is valid palindrome
                 * and same time is longer than the longest palindrome for the moment
                 * set stat index for the new longest palindrome
                 * and set the new longest palindrome length
                 */
                if (isValidPalindrome && currentSubstringLength > longestPalindromeLength) {
                    startIndex = i;
                    longestPalindromeLength = currentSubstringLength;
                }
            }
        }

        /* Obtain end index of longest palindrome */
        int endIndex = startIndex + longestPalindromeLength;

        /* Convert characters to String */
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            sb.append(this.get(i));
        }
        return sb.toString();
    }

    /**
     * Check if index is in bound size of the collection
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index < this.size;
    }

    /**
     * Validate index is in bound size of the collection
     * on invalid index throws IndexOutOfBoundsException
     */
    private void validateIndex(int index) {
        if (!this.isValidIndex(index)) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid index %s in CsvArrayList with %d elements", index, this.size));
        }
    }

    /**
     * When size get to full capacity the collection
     * has been resized to twice bigger capacity
     */
    private void increaseTheSizeIfNeeded() {
        if (this.size == this.capacity) {
            this.capacity *= 2;
            Object[] tmp = new Object[this.capacity];
            System.arraycopy(this.elements, 0, tmp, 0, this.elements.length);
            this.elements = tmp;
        }
    }

    /**
     * When size is three times smaller than the capacity
     * collection has been resized to twice smaller capacity
     */
    private void decreaseTheSize() {
        if (this.size <= this.capacity / 3) {
            this.capacity /= 2;
            this.elements = Arrays.copyOf(this.elements, this.capacity);
        }
    }

    /**
     * Increase index position by one
     * for all elements in the collection starts with the element on given index position
     * to the last element
     */
    private void shiftRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.elements[i + 1] = this.elements[i];
        }
    }

    /**
     * Decrease index position by one
     * for all elements in the collection starts with the element on given index position
     * to the last element
     */
    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }
}
