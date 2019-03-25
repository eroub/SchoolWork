/*
* Name: Evan Roubekas
* ID: V00891470
* Date: November 24rd, 2018
* Filename: BinarySearchTree.java
* Details: CSC115 Assignment 4
*/

import java.util.Iterator;

/*
 * NOTE TO STUDENT:
 * Comment and implement all incomplete methods.
 * Any methods that have comments are already complete and
 * you must not change them.
 * You may add methods that you find helpful, remembering
 * that no public method allows acces to a TreeNode directly.
 * Remove this comment an provide your own header.
 */

/**
 * BinarySearchTree is an ordered binary tree, where the element in each node
 * comes after all the elements in the left subtree rooted at that node
 * and before all the elements in the right subtree rooted at that node.
 * For this assignment, we can assume that there are no elements that are
 * identical in this tree.
 * A small modification will allow duplicates.
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {

	// the root is inherited from BinaryTree.

	/**
	 * Create an empty BinarySearchTree.
	 */
	public BinarySearchTree() {
		super();
	}

	/**
	 * Creates a BinarySearchTree with a single item.
	 * @param item The single item in the tree.
	 */
	public BinarySearchTree(E item) {
		super(item);
	}

	/**
 	 * <b>This method is not allowed in a BinarySearchTree.</b>
	 * It's description from the subclass:<br>
	 * <br>
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException if this method is invoked.
	 */

	 // Attaches a subtree to the left of the root node,
	 // replacing any subtree that was previously there.
	public void attachLeftSubtree(BinaryTree<E> left) {
		throw new UnsupportedOperationException();
	}

	// Attaches a subtree to the right of the root node,
	// replacing any subtree that was previously there.
	public void attachRightSubtree(BinaryTree<E> right) {
		throw new UnsupportedOperationException();
	}

	// compareTo method to compare nodes in the BinarySearchTree
	//public int comparable(BinarySearchTree<E> item){
	//	return root.item.comparable(item.root.item);
	//}

	// Inserts a new item into the tree, maintaining its order.
	public void insert(E item) {
		if(root == null){
			root = new TreeNode<E>(item);
			root.left = null; root.right = null;
		} else {
			boolean L = false; boolean R = false;
			TreeNode<E> node = root;
			TreeNode<E> parent = root;
			while(node != null){
				if(item.compareTo(node.item) < 0){ // LEFT
					parent = node; node = node.left;
					L = true; R = false;
				} else if(item.compareTo(node.item) > 0){ // RIGHT
					parent = node; node = node.right;
					R = true; L = false;
				} else { break; }
			}
			TreeNode<E> nItem = new TreeNode<E>(item);
			if(L) {
				parent.left = nItem;
			} else if(R) {
				parent.right = nItem;
			}
		}
	}

	// Looks for the item in the tree that is equivalent to the item.
	public E retrieve(E item) throws TreeException {
		if(root == null){
			throw new TreeException("Empty Tree");
		}
		TreeNode<E> node = root;
		while(node != null){
			if(item.compareTo(node.item) < 0){
				node = node.left;
			} else if(item.compareTo(node.item) > 0){
				node = node.right;
			} else {
				return node.item;
			}
		}
		return null;
	}
	// Looks for item in the tree, returns the node the item is in.
	private TreeNode<E> retrieveNode(TreeNode<E> node, E item) throws TreeException {
		if(node == null){
			throw new TreeException("Empty Tree");
		}
		TreeNode<E> searchedNode = node;
		while(searchedNode != null){
			if(item.compareTo(searchedNode.item) < 0){
				searchedNode = searchedNode.left;
			} else if(item.compareTo(searchedNode.item) > 0){
				searchedNode = searchedNode.right;
			} else {
				return searchedNode;
			}
		}
		return null;
	}
	// Looks for item in the tree, returns the parent of the node its in
	private TreeNode<E> retrieveParent(E item){
		if(root == null){
			throw new TreeException("Empty Tree");
		}
		TreeNode<E> node = root;
		TreeNode<E> parent = root;
		while(node != null){
			if(item.compareTo(node.item) < 0){
				parent = node;
				node = node.left;
			} else if(item.compareTo(node.item) > 0){
				parent = node;
				node = node.right;
			} else {
				return parent;
			}
		}
		return null;
	}

	// Finds the item that is equivalent to the item and removes it, if it's in the tree.
	public E delete(E item) throws TreeException{
		return delete(root, item);
	}
	private E delete(TreeNode<E> node, E item){
		TreeNode<E> targetNode = retrieveNode(node, item);
		TreeNode<E> parentNode = retrieveParent(item);
		E temp = targetNode.item;

		// Check which side targetNode is
		boolean L = false;
		boolean R = false;
		if(item.compareTo(parentNode.item) < 0){
			L = true;
		} else if(item.compareTo(parentNode.item) > 0){
			R = true;
		}

		// If targetNode has no children -------------------------------------------
		if(targetNode.left == null && targetNode.right == null){
			if(L){
				parentNode.left = null; // Update left ref in parent node
			} else if(R){
				parentNode.right = null; // Update right ref in parent node
			}
			return temp;

		// If targetNode has one child ---------------------------------------------
		} else if(targetNode.left != null ^ targetNode.right != null){
			TreeNode<E> replace = new TreeNode<E>(null);
			if(targetNode.left == null){
				replace = targetNode.right;
			} else {
				replace = targetNode.left;
			}
			if(L && parentNode.left.item.compareTo(targetNode.item) == 0){
				parentNode.left = replace; // Update left ref in parent node
			} else if(R && parentNode.right.item.compareTo(targetNode.item) == 0){
				parentNode.right = replace; // Update right ref in parent node
			}
			return temp;
		}
		// If targetNode has two children ------------------------------------------
		// Find targetNode successor and replace
		TreeNode<E> successor = findSuccessor(targetNode);
		E successorItem = successor.item;
		delete(targetNode, successorItem);
		targetNode.item = successorItem;
		return temp;
	}
	// Find leftMost successor
	private TreeNode<E> findSuccessor(TreeNode<E> node){
		if(node.right != null){
			node = node.right;
			if(node.left == null){
				return node;
			}
			return findSuccessor(node.left);
		} else if(node.left != null){
			node = node.left;
			if(node.right == null){
				return node;
			}
			return findSuccessor(node.right);
		}
		return null;
	}

	/**
	 * Internal test harness.
	 * @param args Not used.
	 */
	 	public static void main(String[] args) {
		// NOTE TO STUDENT: something to get you started.
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		String s2 = new String("Needs");
		tree.insert("Evan");
		tree.insert("Software");
		tree.insert("Engineering");
		tree.insert("Pineapple");
		tree.insert("Zamboni");
		tree.insert("Qwerty");
		tree.insert(s2);
		tree.insert("Roubekas");
		tree.insert("Alpaca");
		tree.insert("Ecstatic");
		tree.insert("Cardio");
		DrawableBTree<String> insert = new DrawableBTree<String>(tree);
		insert.showFrame();
		tree.delete("Roubekas"); // Delete leaf
		tree.delete("Evan"); // Delete node with two children <-- also root
		tree.delete("Ecstatic"); // Delete node with one child

		DrawableBTree<String> delete = new DrawableBTree<String>(tree);
		delete.showFrame();

		if(tree.retrieve("Alpaca") == "Alpaca"){
			System.out.println("Retrieve 'Alpaca' succesful. *this one should work");
		} else {
			System.out.println("Retrieve 'Alpaca' not successful.");
		}
		if(tree.retrieve("Qwerty") == "Qwerty"){
			System.out.println("Retrieve 'Qwerty' succesful. *this one should work");
		} else {
			System.out.println("Retrieve 'Qwerty' not successful.");
		}
		if(tree.retrieve("yeet") == "yeet"){
			System.out.println("Retrieve 'yeet' successful.");
		} else {
			System.out.println("Retrieve 'yeet' not successful. *this should be the output");
		}


		Iterator<String> it = tree.inorderIterator();
		System.out.println("printing out the contents of the tree in sorted order:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		}
		System.out.println();
		Iterator<String> that = tree.postorderIterator();
		System.out.println("printing out the contents of the tree in post sorted order:");
		while (that.hasNext()) {
			System.out.print(that.next()+" ");
		}
		System.out.println();
		Iterator<String> what = tree.preorderIterator();
		System.out.println("printing out the contents of the tree in pre sorted order:");
		while (what.hasNext()) {
			System.out.print(what.next()+" ");
		}
		System.out.println();
	}
}
