/*
* Name: Evan Roubekas
* ID: V00891470
* Date: October 23rd, 2018
* Filename: StackEmptyException.java
* Details: CSC115 Assignment 3
*/

// StackEmptyException catches RuntimeExceptions and returns a message
public class StackEmptyException extends RuntimeException {

  public StackEmptyException(String msg) {
    super(msg);
  }

  public StackEmptyException() {
    super();
  }

}
