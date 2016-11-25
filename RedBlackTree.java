import java.util.NoSuchElementException;

import javafx.scene.Parent;

// Rules of a red black tree:
// Dont talk about red-black trees
// Every node is red or black
// Root is always black
// Red nodes can't have red parents or children
// There is the same number of black nodes from root to a null node

public class RedBlackTree<K extends Comparable<K>, E>  implements IBinarySearchTree<K,E>
{
	int numNodes; // Number of nodes in tree total
	Node root; // First node, root of our tree
	
	public RedBlackTree(){
		numNodes = 0;
		root = null;
	}
	
    public void put(K key, E element)
    {
    	Node newNode = new Node(key, element); // Instantiate the new node
    	
    	if(root == null) // If this is out first node, set it as the root
    	{
    		
    		root = newNode;
    		numNodes++;
    		balance(newNode);
    	} else { // Otherwise, we have to search for where to place it
    		Node current = root;
    		Node parent;
    		while(true)
    		{
    			parent = current;
    			if(newNode.key.compareTo((K)current.key) < 0) // Check if we should pick left node
    			{
    				current = current.leftChild;
    				if(current == null)
    				{
    					parent.leftChild = newNode; // Set left node and finish
    					newNode.parent = parent;
    					numNodes++;
    					balance(newNode);
    					return;
    				}
    			} else { // If not, pick right node
    				current = current.rightChild;
    				if(current == null)
    				{
    					parent .rightChild = newNode; // Set right node and finish
    					newNode.parent = parent;
    					numNodes++;
    					balance(newNode);
    					return;
    				}
    			}
    		}
    	}
    }
    
    public void balance(Node n)
    {
    	Node grandpa = null;
		Node uncle = null;
		Node parent = n.parent;
		
    	if(n == root) // Simple fix, set root to black
    	{
    		n.setBlack();
    		return;
    	}
    	
    	if(parent.color == "Black") // No problems
    	{
    		return;
    	} else { // If not, we need to set variables and make some changes
    		n.setRed();
    		if(parent == root) // If child of root
    		{
    			grandpa = null; // There is no grandpa
    			uncle = null; // There is no uncle
    		} else {
    			grandpa = parent.parent; // Otherwise set grandparent
    			
    			if(grandpa.leftChild == parent) // Check left or right
    			{
    				uncle = grandpa.rightChild; // Set uncle
    			} else {
    				uncle = grandpa.leftChild;
    			}
    			
    			//BEGIN THE CHECKS
    			if(uncle != null && uncle.color == "Red") // Red parent, red uncle, CASE 1
    			{
    				case1(n, parent, uncle, grandpa);
    				return;
    			}
    			
    			if(uncle == null || uncle.color == "Black")
    			{
    				if(n == parent.leftChild && parent == grandpa.rightChild) // Left child of right child, CASE 2
    				{
    					case2(n, parent, uncle, grandpa);
    					return;
    				}
    				
    				if(n == parent.rightChild && parent == grandpa.leftChild) // Right child of left child, CASE 2
    				{
    					case3(n, parent, uncle, grandpa);
    					return;
    				}
    				
    				if( n == parent.rightChild && parent == grandpa.rightChild) // Right child of right child, CASE 3
    				{
    					case4(n, parent, uncle, grandpa);
    					return;
    				}
    				
    				if( n == parent.leftChild && parent == grandpa.leftChild) // Left child of left child, CASE 3
    				{
    					case5(n, parent, uncle, grandpa);
    					return;
    				}
    			}
    	
    		}
    		
    	}
    		
    }
    private void case1(Node n, Node parent, Node uncle, Node grandpa)
    {
    	if(grandpa.color == "Red"){ grandpa.setBlack();} // Changes grandpa's color
    	else { grandpa.setRed();}
    	
    	if(parent.color == "Red"){ parent.setBlack();} // Changes parent's color
    	else { parent.setRed();}
    	uncle.setBlack(); // Set uncle to black
    	balance(grandpa); // Continue balancing
    }
    
    private void case2(Node n, Node parent, Node uncle, Node grandpa)
    {
    	parent.leftChild = n.rightChild;
    	if(n.rightChild != null){ n.rightChild.parent = parent;} // Give n's child to parent
    	
    	n.rightChild = parent; // Parent becomes n's child
    	parent.parent = n;
    	
    	grandpa.rightChild = n; // Grandpa becomes n's parent
    	n.parent = grandpa;
    	
    	balance(parent); // Continue balancing
    }
    
    private void case3(Node n, Node parent, Node uncle, Node grandpa)
    {
    	parent.rightChild = n.leftChild;
    	if(n.leftChild != null){ n.leftChild.parent = parent;} // Give n's child to parent
    	
    	n.leftChild = parent; // Parent becomes n's child
    	parent.parent = n;
    	
    	grandpa.leftChild = n; // Grandpa becomes n's parent
    	n.parent = grandpa;
    	
    	balance(parent); // Continue balancing
    }
    
    private void case4( Node n, Node parent, Node uncle, Node grandpa) // Rotate left
    {
    	grandpa.rightChild = parent.leftChild; // Grandpa inherits parents child
    	if(parent.leftChild != null) {parent.leftChild.parent = grandpa;}
		
    	parent.parent = grandpa.parent;// Grandpa's parent  becomes parent's parent
    	if(grandpa.parent != null)
		{
			if(grandpa == grandpa.parent.leftChild)
			{
				grandpa.parent.leftChild = parent;
			}
			if(grandpa == grandpa.parent.rightChild)
			{
				grandpa.parent.rightChild = parent;
			}
		}
		
		parent.leftChild = grandpa; // Grandpa becomes parent's child
		grandpa.parent = parent;
		
		String tempColor = parent.color; // Swap colors
		parent.color = grandpa.color;
		grandpa.color = tempColor;
		
		if(grandpa == root) // if need be, set new root
		{
			root = parent;
		}
		
		balance(n); // Continue balancing
    	
	}
    
    private void case5( Node n, Node parent, Node uncle, Node grandpa) // Rotate right
    {
    	grandpa.leftChild = parent.rightChild;
    	if(parent.rightChild != null) {parent.rightChild.parent = grandpa;} // Grandpa inherits parent's child
		
    	parent.parent = grandpa.parent; // Grandpa's parent  becomes parent's parent
    	if(grandpa.parent != null)
		{
			if(grandpa == grandpa.parent.rightChild)
			{
				grandpa.parent.rightChild = parent;
			}
			if(grandpa == grandpa.parent.leftChild)
			{
				grandpa.parent.leftChild = parent;
			}
		}
		parent.rightChild = grandpa; // Grandpa becomes parent's child
		grandpa.parent = parent;
		
		String tempColor = parent.color; // Swap colors
		parent.color = grandpa.color;
		grandpa.color = tempColor;
		
		
		if(grandpa == root) // if need be, set new root
		{
			root = parent;
		}
		
		balance(n); // Continue balancing
    	
	}
    

    public E get(K key)
    {
    	Node current = root; // Start at top of tree
    	
    	while(current.key != key)
    	{
    		if(key.compareTo((K)current.key) < 0) // See if we should check left node
    		{
    			current = current.leftChild;
    		} else { // Otherwise check right node
    			current = current.rightChild;
    		}
    		if(current == null) 
    		{
    			throw new NoSuchElementException(); // If we can't find it, return null
    		}
    	}
    	
    	return (E) current.element; // Cast to E
    }

    public int size()
    {
    	return numNodes; // Returns number of nodes(items)
    }

    public String toString()
    {
    	if(root == null) // Special case: no root
    	{
    		return"[]";
    	}
    	String keyString = root.toString(); // Save printable version
    	String elementString = root.element.toString() + ", "; // Element and comma
    	String leftString = "";
    	String rightString = "";
    	
    	leftString = toStringRecursive(root.leftChild); // Recursive calls on left and right
    	rightString = toStringRecursive(root.rightChild);
    	
    	
    	String a = "["+leftString  +keyString +": "+ elementString + rightString +"]"; // Concatenate using in-order
    	return a.substring(0, a.length()-3) + "]"; // Remove last comma
    }
    
    public String toStringRecursive(Node n)
    {
    	if(n == null) // Special case: no root
    	{
    		return"";
    	}
    	String keyString = n.toString(); // Save printable version
    	String elementString = n.element.toString() + ", "; // Element and comma
    	String leftString = toStringRecursive(n.leftChild); // More recursive calls
    	String rightString = toStringRecursive(n.rightChild);
    	
    	
    	return (leftString  +keyString +": "+ elementString + rightString); //Concatenate
    }

    public int getHeight() // Returns the height of the tree
    {
    	if(root == null) // Empty tree means return zero
    	{
    		return 0;
    	} else { // Otherwise we get to work
    		int leftHeight = getHeightRecursive(root.leftChild); // Call recursive helper to get longest on each side
    		int rightHeight = getHeightRecursive(root.rightChild); 
    		if(leftHeight > rightHeight) // Return the larger height
    		{
    			return leftHeight;
    		} else {
    			return rightHeight;
    		}
    	}
    }
    
    private int getHeightRecursive(Node<K,E> current)
    {
    	if(current == null) // Check if we've reached the end
    	{
    		return 0; // Return zero to start our count
    	} else { // Otherwise check left and right
    		int leftHeight = getHeightRecursive(current.leftChild); // Recursively look for the end to start counting
    		int rightHeight = getHeightRecursive(current.rightChild);
    		if(leftHeight > rightHeight) // Always return larger of the two (or right of equal)
    		{
    			return ++leftHeight; // Increment as we go down in levels of recursion
    		} else {
    			return ++rightHeight;
    		}
    	}
    }

    public String getTreeString()
    {	
    	if(root == null) // Special case: no root
    	{
    		return"[]";
    	}
    	String keyString = root.toString(); // Save printable version
    	String leftString;
    	String rightString;
    	
    	if(root.leftChild == null) // Return null string for each side of null with no node
    	{
    		leftString = "null";
    	} else { 
    		leftString = getTreeStringRecursive(root.leftChild);
    	}
    	
    	if(root.rightChild == null) // Return null string for each side of null with no node
    	{
    		rightString = "null";
    	} else { 
    		rightString = getTreeStringRecursive(root.rightChild);
    	}
    	
    	
    	return ("["  + root.color + ", " +keyString + ", " + leftString + ", " + rightString + "]");
    }
    
    private String getTreeStringRecursive(Node n)
    {	
    	if(n == null) // Base case: return string representation of a null node
    	{
    		return "null";
    	}
    	
    	String keyString = n.toString(); // Save printable version
    	String leftString;
    	String rightString;
    	
    	if(n.leftChild == null) // Add null string for each side of null with no node
    	{
    		leftString = "null";
    	} else { 
    		leftString = getTreeStringRecursive(n.leftChild);
    	}
    	
    	if(n.rightChild == null) // Add null string for each side of null with no node
    	{
    		rightString = "null";
    	} else { 
    		rightString = getTreeStringRecursive(n.rightChild);
    	}
    	
    	
    	return ("[" + n.color + ", " +keyString + ", " + leftString + ", " + rightString + "]");
    }
	
    private class Node<K extends Comparable<K>,E>{
    	K key;
    	E element; // Item being held
    	Node parent; // Node above this one
    	Node leftChild; // Child to the left
    	Node rightChild; // child to the right
    	String color; // Red or black
    	public Node(K key, E element)
    	{
    		this.key = key;
    		this.element = element;
    		this.parent = null;
    		this.leftChild = null;
    		this.rightChild = null;
    		this.color = "Red";
    		
    	}
    	public String toString()
    	{
    		return key.toString();
    	}
    	
    	public void setBlack()
    	{
    		this.color = "Black";
    	}
    	
    	public void setRed()
    	{
    		this.color = "Red";
    	}
    	
    
    }
}