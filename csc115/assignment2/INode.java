/**
  INode is an integer singly linked node class. It stores an integer
  and an INode object which points to the next INode object.
  <p>
  adapted from your textbook, 3rd edition, p250
  @author  Janet J. Prichard
  @author  Frank M. Carrano
  @author  Victoria Li
  @version %I%, %G%
  @since   1.0
*/
public class INode
{
	int item;
	INode next;
	/**
	 Default constructor. Sets the item to 0, next to null.
	*/
	public INode()
	{
		item=0;
		next=null;
	}
	/**
	 Create an INode object with value n.
	 @param n an integer value assigned to the INode object.
	*/
	public INode(int n)
	{
		item=n;
		next=null;
	}
	/**
	 Create an INode object with value n, and the next INode object its next points to.
	 @param n        an integer assigned to the instance variable item.
	 @param nextNode an INode object assigned to the instance object next
	*/
	public INode(int n,INode nextNode )
	{
		item=n;
		next=nextNode;
	}
	/**
	 Gets the value of item
	 @return the value of item
	*/
	public int getItem()
	{
		return item;
	}
	/**
	 Assign newItem to instance variable item
	 @param newItem   an integer assigned to the instance variable item.
	*/
	public void setItem(int newItem)
	{
		item=newItem;
	}
	/**
	 Gets the INode object next points to.
	 @return instance object next
	*/
	public INode getNext()
	{
		return next;
	}
	/**
	 Assign nextNode to instance variable next
	 @param nextNode   an INode object
	*/
	public void setNext(INode nextNode)
	{
		next=nextNode;
	}
}