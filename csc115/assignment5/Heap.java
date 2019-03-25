/*
* Name: Evan Roubekas
* ID: V00891470
* Date: November 27th, 2018
* Filename: Heap.java
* Details: CSC115 Assignment 5
*/

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * The shell of the class, to be completed as part
 * of CSC115 Assignment 5: Emergency Room.
 */


public class Heap<E extends Comparable<E>> {

	private Vector<E> heapArray;
	public int count;

	// Creates an empty heap
	public Heap() {
		heapArray = new Vector<E>();
		count = 0;
	}

	// True if the heap is empty, false if it is not
	public boolean isEmpty(){
		if(heapArray.size() == 0) return true;
		return false;
	}

	// The number of items in the heap
	public int size(){
		return count;
	}
	// --------------------- INSERT ----------------------------------------------
	// Inserts an item into the heap
	// ---------------------------------------------------------------------------
	public void insert(E item){
		heapArray.add(count++, item);
		bubbleUp(count-1);
	}
	// BubbleUp method moves ER_Patiends up the tree according to priority
	private void bubbleUp(int index){
		E temp;
		if(index != 0){
			int parentIndex = getParenetindex(index);
			if(heapArray.get(parentIndex).compareTo(heapArray.get(index)) < 0){
				temp = heapArray.get(parentIndex);
				heapArray.set(parentIndex, heapArray.get(index));
				heapArray.set(index, temp);
				bubbleUp(parentIndex);
			}
		}
	}
	// ----------------------- DELETE --------------------------------------------
	// Removes the item at the root node of the heap and returns it
	// ---------------------------------------------------------------------------
	public E removeRootItem() throws NoSuchElementException {
		// If Heap is empty
		if(heapArray.isEmpty()) throw new NoSuchElementException("Empty Heap!");
		// If Heap contains one item
		if(heapArray.size() == 1) return heapArray.remove(0);
		// If Heap contains multiple items
		E temp = heapArray.elementAt(1);
		heapArray.set(0, heapArray.get(--count));
		heapArray.remove(count);
		bubbleDown(0);
		return temp;
	}
	// BubbleDown method moves ER_Patients down the tree according to priority
	private void bubbleDown(int index){
		int child, rightChild; E temp;
		if(index*2 <= count){
			child = getChildindex(index)+1;
			if(index*2+1 <= count){
				rightChild = child+1;
				if(heapArray.get(rightChild).compareTo(heapArray.get(child)) > 0){
					child = rightChild;
				}
				if(heapArray.get(index).compareTo(heapArray.get(child)) < 0){
					temp = heapArray.get(child);
					heapArray.set(child, heapArray.get(index));
					heapArray.set(index, temp);
					bubbleDown(child);
				}
			}
		}
	}
	// -------------------- TOOLS ------------------------------------------------
	// Various tool methods
	// ---------------------------------------------------------------------------
	// Retrieves, without removing the item in the root
	public E getRootItem() throws NoSuchElementException {
		// If Heap is empty
		if(heapArray.isEmpty()) throw new NoSuchElementException("Empty Heap!");
		// Otherwise
		return heapArray.firstElement();
	}
	private int indexOf(E p){
		for (int i = 1; i < heapArray.capacity(); i++) {
			if (heapArray.elementAt(i).equals(p))   {
				return i;
			}
		}
		return -1;
	}
	private int getParenetindex(int child){
		return child/2;
	}
	private int getChildindex(int parent){
		if(parent == 0){ return 0; }
		return parent*2;
	}
	public void print_vector() {
		System.out.println(" *************** Array is ***************");
		for (int i = 0; i < heapArray.size(); i++){
			System.out.println(heapArray.elementAt(i));
		}
	}

	// MAIN ***************************************************************** MAIN
	// ***************************************************************************
	public static void main(String args []){

		Heap <ER_Patient> hp = new Heap <ER_Patient>();
		hp.insert(new ER_Patient ("Walk-in"));
		hp.insert(new ER_Patient ("Chronic"));
		hp.insert(new ER_Patient ("Life-threatening"));
		hp.insert(new ER_Patient ("Major fracture"));
		hp.insert(new ER_Patient ("Walk-in"));
		hp.print_vector();
		hp.insert(new ER_Patient ("Major fracture"));
		hp.insert(new ER_Patient ("Walk-in"));
		hp.insert(new ER_Patient ("Chronic"));
		hp.insert(new ER_Patient ("Life-threatening"));
		hp.insert(new ER_Patient ("Chronic"));
		hp.print_vector();
		hp.removeRootItem();
		hp.getRootItem();
		hp.print_vector();
		hp.removeRootItem();
		hp.print_vector();

		/* you can add more tests */
	}
}
