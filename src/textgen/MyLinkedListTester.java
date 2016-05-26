/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	MyLinkedList<Integer> addEndList;
	MyLinkedList<String> addAtIndexList;
	MyLinkedList<String> setAtIndexList;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
		shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

		// prepare for testAddEnd
		addEndList = new MyLinkedList<Integer>();

		// prepare for testAddAtIndex
		addAtIndexList = new MyLinkedList<String>();
		addAtIndexList.add("A");
		addAtIndexList.add("C");
		addAtIndexList.add("D");
		addAtIndexList.add("E");

		// prepare for setAtIndex
		setAtIndexList = new MyLinkedList<String>();

	}

	/**
	 * Test if the get method is working correctly.
	 */
	/*
	 * You should not need to add much to this method. We provide it as an
	 * example of a thorough test.
	 */
	@Test
	public void testGet() {
		// test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		// test longer list contents
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
		}

		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

	}

	/**
	 * Test removing an element from the list. We've included the example from
	 * the concept challenge. You will want to add more tests.
	 */
	@Test
	public void testRemove() {
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());

		// TODO: Add more tests here

		try {
			addAtIndexList.remove(-1);
			fail("check LowerBound in Set");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			addAtIndexList.remove(addAtIndexList.size);
			fail("check upperBound in Set");
		} catch (IndexOutOfBoundsException e) {

		}

		String A = addAtIndexList.get(0);
		assertEquals("Remove: check a is correct ", "A", A);
		String E = addAtIndexList.get(addAtIndexList.size - 1);
		assertEquals("Remove: check a is correct ", "E", E);

	}

	/**
	 * Test adding an element into the end of the list, specifically public
	 * boolean add(E element)
	 */
	@Test
	public void testAddEnd() {
		// TODO: implement this test

		try {
			addEndList.add(-1, new Integer(100000));
			fail("check LowerBound in Set");
		} catch (IndexOutOfBoundsException e) {

		}

		for (int i = 0; i < 50; i++) {
			addEndList.add(i);
		}

		for (int j = 0; j < 50; j++) {

			assertEquals("check Add to End, it is not working properly", new Integer(j), addEndList.get(j));
		}
	}

	/** Test the size of the list */
	@Test
	public void testSize() {
		assertEquals("check SHORTLIST Size", new Integer(2), new Integer(shortList.size));
		assertEquals("check EMPTYLIST Size", new Integer(0), new Integer(emptyList.size));
		assertEquals("check LONGERLIST Size", new Integer(10), new Integer(longerList.size));

	}

	/**
	 * Test adding an element into the list at a specified index, specifically:
	 * public void add(int index, E element)
	 */
	@Test
	public void testAddAtIndex() {
		// TODO: implement this test
		assertEquals("check testAddAtIndex()", "A", addAtIndexList.get(0));
		assertEquals("check testAddAtIndex()", "C", addAtIndexList.get(1));
		assertEquals("check testAddAtIndex()", "D", addAtIndexList.get(2));
		assertEquals("check testAddAtIndex()", "E", addAtIndexList.get(3));

		// Inserting
		addAtIndexList.add(1, "B");

		assertEquals("check testAddAtIndex()", "A", addAtIndexList.get(0));
		assertEquals("check testAddAtIndex()", "B", addAtIndexList.get(1));
		assertEquals("check testAddAtIndex()", "C", addAtIndexList.get(2));
		assertEquals("check testAddAtIndex()", "D", addAtIndexList.get(3));
		assertEquals("check testAddAtIndex()", "E", addAtIndexList.get(4));

	}

	/** Test setting an element in the list */
	@Test
	public void testSet() {
		// TODO: implement this test
		setAtIndexList.add(0, "Mustafa");
		setAtIndexList.add(1, "Hatem");
		setAtIndexList.add(2, "Ahmed");
		setAtIndexList.add(3, "Haytham");

		String old0 = setAtIndexList.set(0, "Hazem");
		assertEquals("check Set it is not working properly", "Mustafa", old0);

		String old1 = setAtIndexList.set(1, "Hazem");
		assertEquals("check Set it is not working properly", "Hatem", old1);

		String old2 = setAtIndexList.set(2, "Hazem");
		assertEquals("check Set it is not working properly", "Ahmed", old2);

		String old3 = setAtIndexList.set(3, "Hazem");
		assertEquals("check Set it is not working properly", "Haytham", old3);

		try {
			setAtIndexList.set(-1, "lowerBoundTest");
			fail("check LowerBound in Set");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			setAtIndexList.set(setAtIndexList.size, "upperBoundTest");
			fail("check upperBound in Set");
		} catch (IndexOutOfBoundsException e) {

		}
	}

	// TODO: Optionally add more test methods.

}
