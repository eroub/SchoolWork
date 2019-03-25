/*
* Name: Evan Roubekas
* ID: V00891470
* Date: November 27th, 2018
* Filename: PriorityQueue.java
* Details: CSC115 Assignment 5
*/

import java.util.NoSuchElementException;

/**
 * The shell of the class, to be completed as part of the
 * CSC115 Assignment 5 : Emergency Room
 */

/**
 * Complete this class as per the Heap.html specification document.
 * Fill in any of the parts that have the comment:
 * ********  COMPLETE *******
 * Do not change method headers or code that has been supplied.
 * Delete all messages to you, including this one, before submitting.
 */

public class PriorityQueue<E extends Comparable<E>> {

	private Heap<E> heap;

	// Creates an empty priority queue
	public PriorityQueue() {
		heap = new Heap<E>();
	}
	// Removes the highest priority item from the queue
  public E dequeue() throws NoSuchElementException {
		if(heap.isEmpty()) throw new NoSuchElementException("Heap Empty!");
		return heap.removeRootItem();
  }
	// Insets an item into the queue
 	public void enqueue(E item) {
		heap.insert(item);
  }
	// True if the queue is empty, false if it is not
  public boolean isEmpty() {
		if(heap.size() == 0) return true;
		return false;
  }
	// Retrieves, but does not remove the next item out of the queue
  public E peek() throws NoSuchElementException {
		if(heap.isEmpty()) throw new NoSuchElementException("Heap Empty!");
		return heap.getRootItem();
  }

    public static void main(String args []) {

		PriorityQueue <ER_Patient> p1 = new PriorityQueue <ER_Patient> ();

		System.out.println(p1.isEmpty());
		p1.enqueue(new ER_Patient("Walk-in"));
		System.out.println(p1.peek());
		System.out.println(p1.isEmpty());

		/* you can add more tests */
    }
}
