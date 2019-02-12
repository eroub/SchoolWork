/*
* Name: Evan Roubekas
* ID: V00891470
* Date: October 23rd, 2018
* Filename: Node.java
* Details: CSC115 Assignment 3
*/

// Stack Data-Structure Implementation
public class Node {

  String data;
  Node next;

  public Node() {
    data = null;
    next = null;
  }

  public Node(String n) {
    data = n;
    next = null;
  }

  public Node(String n, Node nextNode) {
    data = n;
    next = nextNode;
  }


}
