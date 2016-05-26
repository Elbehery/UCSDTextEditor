package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		this.head = new LLNode<E>();
		this.tail = new LLNode<E>();

		// setting the sentinel nodes to link to each other
		this.head.next = this.tail;
		this.tail.prev = this.head;
		// initialize the size of the list to zero
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element
	 *            The element to add
	 */
	public boolean add(E element) {
		// TODO: Implement this method

		if (element == null) {
			throw new NullPointerException("Adding an element with NULL is not allowed");
		}

		LLNode<E> current = new LLNode<E>(element);

		current.next = this.tail;
		current.prev = current.next.prev;

		current.next.prev = current;
		current.prev.next = current;

		// update the size
		size++;

		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E get(int index) {
		// TODO: Implement this method.

		// checking the correct index
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("The requested index is out of the List Bounds");
		}

		// retrieve the node at the specified Index
		LLNode<E> currentNode = findNodeInList(index);
		// sanity check
		if (currentNode.data == null) {
			throw new NullPointerException("The node at index " + index + " does not contain data element ");
		}

		return currentNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The
	 *            index where the element should be added
	 * @param element
	 *            The element to add
	 */
	public void add(int index, E element) {
		// TODO: Implement this method

		// checking the correct index
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("LinkedList should not have a negative index");
		}

		if (element == null) {
			throw new NullPointerException("Adding an element with NULL is not allowed");
		}
		// prepare the node to be inserted
		LLNode<E> insertNode = new LLNode(element);

		// check if element already exist in this index
		if (index < size - 1) {
			// retrieve the node at the specified Index
			LLNode<E> currentNode = findNodeInList(index);

			// inserting the current node in the expected place, and adjusting
			// the
			// whole list
			insertNode.next = currentNode;
			insertNode.prev = currentNode.prev;

			currentNode.prev.next = insertNode;
			currentNode.prev = insertNode;
			// update size
			size++;

		} else {
			add(element);
		}

	}

	/** Return the size of the list */
	public int size() {
		// TODO: Implement this method
		return this.size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException
	 *             If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// TODO: Implement this method
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Specified index is not allowed, please set a reasonable index");
		}

		LLNode<E> currentNode = findNodeInList(index);
		// sanity check
		if (currentNode.data == null) {
			throw new NullPointerException("The node at index " + index + " is NULL ");
		}

		// handling the node removal, to retain the list in the right order.
		currentNode.next.prev = currentNode.prev;
		currentNode.prev.next = currentNode.next;

		currentNode.next = null;
		currentNode.prev = null;

		size--;

		return currentNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index
	 *            The index of the element to change
	 * @param element
	 *            The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E set(int index, E element) {
		// TODO: Implement this method
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Specified index does not exist, please set a reasonable index");
		}

		if (element == null) {
			throw new NullPointerException("setting a node data with NULL is not allowed");
		}

		LLNode<E> currentNode = findNodeInList(index);

		if (currentNode == null) {
			throw new NullPointerException("Retrieved Element is NULL");
		}

		E oldElement = currentNode.data;
		currentNode.data = element;
		return oldElement;
	}

	/**
	 * Find the node in {@link MyLinkedList} instance, at the given index.
	 * 
	 * @param index
	 * @return {@link LLNode}
	 */
	private LLNode<E> findNodeInList(int index) {

		// retrieve the whole node, because we need access to Next && Prev.
		LLNode<E> currentNode = this.head;
		int i = 0;

		while (i <= index) {
			if (i == index) {
				currentNode = currentNode.next;
				break;
			} else {
				currentNode = currentNode.next;
				i++;
			}
		}

		return currentNode;
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	public LLNode() {
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
