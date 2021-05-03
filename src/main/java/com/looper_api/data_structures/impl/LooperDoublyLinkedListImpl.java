package com.looper_api.data_structures.impl;


import com.google.gson.annotations.Expose;
import com.looper_api.data_structures.LooperArrayList;
import com.looper_api.data_structures.LooperLinkedList;

import java.util.Iterator;

public class LooperDoublyLinkedListImpl<E> implements LooperLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;


    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> previous;

        public Node(E element) {
            this.value = element;
            this.next = null;
            this.previous = null;
        }
    }

    public LooperDoublyLinkedListImpl() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public LooperDoublyLinkedListImpl(String palindrome) {

        /* Set initial values */
        this.head = null;
        this.tail = null;
        this.size = 0;

        /* Seed the chars from the palindrome as nodes */
        for (int i = 0; i < palindrome.length(); i++) {
            Character character = palindrome.charAt(i);
            this.addLast((E) character);
        }
    }

    @Override
    public void addFirst(E element) {

        Node<E> newElement = new Node<>(element);

        if (!this.isEmpty()) {

            /* Head previous gets old head */
            this.head.previous = newElement;

            /* Old head becomes second element */
            newElement.next = this.head;
        }

        /* Assign the new Node to the head as first element in the LinkedList */
        this.head = newElement;

        /* Increase size accordingly to the new added element */
        this.size++;
    }

    @Override
    public void addLast(E element) {

        Node<E> newLastElement = new Node<>(element);

        if (this.isEmpty()) {

            /* If no elements and head is null, this last element is assigned to head */
            this.head = newLastElement;

            /* If no elements tail point to the head */
            this.tail = this.head;
        } else {

            /* Start from the first element, which is the head */
            Node<E> current = this.head;

            /* Iterate to the last element, recognise him by his 'next' property is null */
            while (current.next != null) {

                /* Shift forward by assigning next element to 'current' */
                current = current.next;
            }

            /* Assign to collection's last element 'next' property, the new Node */
            current.next = newLastElement;

            /* Assign the pre-last element to the last element 'previous' field */
            newLastElement.previous = current;

            /* Assign the last element to the tail */
            this.tail = newLastElement;
        }
        /* Increase size accordingly to the new added element */
        this.size++;
    }

    @Override
    public E removeFirst() {

        /* Check if LinkedList is empty throw IllegalStateException */
        this.ensureNonEmpty();

        /* Get the value that will be removed */
        E value = this.head.value;

        /* Assign the second element to become first */
        this.head = this.head.next;

        /* Decrease size accordingly to the removed element */
        this.size--;

        /* Return the removed value */
        return value;
    }


    @Override
    public E removeLast() {

        /* Check if LinkedList is empty throw IllegalStateException */
        this.ensureNonEmpty();

        /* If there is only one element in the LinkedList */
        if (this.size == 1) {
            E value = this.head.value;
            this.head = null;
            this.tail = null;
            this.size--;
            return value;
        }

        Node<E> preLastElement = this.head;
        Node<E> elementForRemove = this.head.next;

        /* Iterate to the last element, recognise him by his 'next' property is null */
        while (elementForRemove.next != null) {

            /* Keep track of last and pre-last elements */
            preLastElement = elementForRemove;
            elementForRemove = elementForRemove.next;
        }

        /* Set 'next' property to null of the old pre-last to make him current last element */
        preLastElement.next = null;

        /* Assign the new last element to the tail */
        this.tail = preLastElement;

        /* Decrease size accordingly to the removed element */
        this.size--;

        /* return removed last element's value */
        return elementForRemove.value;
    }

    @Override
    public E getFirst() {

        /* Check if LinkedList is empty throw IllegalStateException */
        this.ensureNonEmpty();
        return this.head.value;
    }

    @Override
    public E getLast() {

        /* Check if LinkedList is empty throw IllegalStateException */
        this.ensureNonEmpty();
        return this.tail.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {

            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public boolean isPalindrome() {
        return recursivePalindromeCheck(this.head, this.tail);
    }

    /**
     * Recursively walk through the palindrome string
     * from beginning to the core and from end to the core
     * comparing the pairs values equality
     */
    private boolean recursivePalindromeCheck(Node<E> forward, Node<E> backward) {

        /* If the palindrome is with odd length and is valid palindrome */
        if (forward.equals(backward)) {
            return true;
        }

        /* If the palindrome is with even length and is valid palindrome */
        if (forward.next.equals(backward) && forward.value.equals(backward.value)) {
            return true;
        }

        /* If the pair elements values are not equal */
        if (!forward.value.equals(backward.value)) {
            return false;
        }
        if (!forward.equals(backward)) {
            return recursivePalindromeCheck(forward.next, backward.previous);
        }

        return true;
    }

    /**
     * Validate the LinkedList is not empty (without elements)
     * If no elements, throw IllegalStateException
     */
    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Forbidden operation, LinkedList is empty");
        }
    }
}
