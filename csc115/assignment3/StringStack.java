/*
* Name: Evan Roubekas
* ID: V00891470
* Date: October 23rd, 2018
* Filename: StringStack.java
* Details: CSC115 Assignment 3
*/

/*
 * The shell of the class, to be completed as part of the CSC115 Assignment 3 : Calculator.
 */

public class StringStack {

	public Node head;

	public StringStack() {
		head = null;
	}

	//--------------------------------------------------------------------------------------------------------------
	// Method isEmpty() checks the Stack for elements. If the Stack is empty return true. Otherwise, return false.
	public boolean isEmpty() {
		if(head == null){
			return true;
		}
		return false;
	}
	//--------------------------------------------------------------------------------------------------------------
	// Method pop() returns the last data inserted into the stack and removes it from the stack
	public String pop() throws StackEmptyException {
		if(isEmpty()){
			throw new StackEmptyException("Stack is Empty! @pop");
		}
		Node temp = head;
		head = head.next;
		return temp.data;
	}
	//--------------------------------------------------------------------------------------------------------------
	// Method peek() retrieves the last data of the stack and returns it.
	public String peek() throws StackEmptyException {
		if(isEmpty()){
			throw new StackEmptyException("Stack is Empty! @peek");
		}
		return head.data;
	}
	//--------------------------------------------------------------------------------------------------------------
	// Method push() inserts an data into the stack
	public void push(String data) throws InvalidExpressionException {
		head = new Node(data, head);
	}
	//--------------------------------------------------------------------------------------------------------------
	// Method popAll() pops all data out of the stack where the first data inserted is the last data popped.
	public void popAll() {
		head = null;
	}
}
