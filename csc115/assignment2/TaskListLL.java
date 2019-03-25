/*
* Name: Evan Roubekas
* ID: V0089140
* Date: Oct. 5th
* Filename: TaskListLL.java
* Details: CSC115 Assignment 2
*/

public class TaskListLL implements TaskList {

    private TaskListNode head;
    private int count;

    public TaskListLL() {
      head = null;
      count = 0;
    }

// ------------------------------------------------------------------------------------
    public int getLength() {
        //System.out.println("ECHO getLength()");
        return count;
    }

// ------------------------------------------------------------------------------------
    public boolean isEmpty() {
        //System.out.println("ECHO isEmpty()");
        return (getLength() == 0);
    }

// ------------------------------------------------------------------------------------
    public Task removeHead() {
        //Task result;
        //System.out.println("ECHO removeHead()");

        TaskListNode temp = null;
        TaskListNode test = head;

        if(head != null) {
          //System.out.println(head.task);
          temp = head;
          this.head = head.next;
          count--;
          return temp.task;
        } else {

          return null;
        }
    }

// ------------------------------------------------------------------------------------
    public Task remove(int number) {
        //System.out.println("ECHO remove(task with number " + number + ")" );

        TaskListNode curr = head;
        TaskListNode prev = curr;
        TaskListNode temp = null;

        if(count == 1){
          count--;
          temp = curr;
          head = null;
          return temp.task;
        }

        while(curr != null) {
          //System.out.println(curr.task);
          if(curr.task.getNumber() == number) {
            //System.out.println(head.task);
            count--;
            if(curr.next != null) {
              temp = curr;
              curr = curr.next;
              return temp.task;
            } else if(prev != null) {
              prev.next = null;
            }
          } else if (curr.next != null) {
            curr = curr.next;
            return curr.task;
          }
            break;
        }

        return null;
    }

// ------------------------------------------------------------------------------------
    public boolean insert(int priority, int number) {

        TaskListNode newNode = new TaskListNode(new Task(priority, number));

        //System.out.println("ECHO insert(Task(" + priority + " " + number + "))");

        TaskListNode prev = null;
        TaskListNode curr = head;

        while (curr != null) {
          //System.out.println(curr.task);
          if(curr.task.getNumber() == number){
            return false;
          } else if(priority <= curr.task.getPriority()) {
            prev = curr;
            curr = curr.next;
          } else {
            break;
          }
        }
        newNode.next = curr;

        if(prev == null) {
          head = newNode;
          count++;
          return true;
        } else {
          prev.next = newNode;
          count++;
          return true;
        }
    }

// ------------------------------------------------------------------------------------
    public Task retrieve(int pos) {
        //System.out.println("ECHO retrieve(" + pos + ")");

        TaskListNode curr = head;

        while(curr != null) {
          for(int i = 0; i < pos; i++) {
            curr = curr.next;
          }
          return curr.task;
        }
        return null;
    }
}
