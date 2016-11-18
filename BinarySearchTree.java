public class BinarySearchTree<K extends Comparable<K>, E>  implements IBinarySearchTree<K,E>
{
	int numNodes; // Number of nodes in tree total
	Node root; // First node, root of our tree
	
	public BinarySearchTree(){
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
    					numNodes++;
    					return;
    				}
    			} else { // If not, pick right node
    				current = current.rightChild;
    				if(current == null)
    				{
    					parent .rightChild = newNode; // Set right node and finish
    					numNodes++;
    					return;
    				}
    			}
    		}
    	}
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
    		if(current == null) return null; // If we can't find it, return null
    	}
    	
    	return (E)current; // Cast to E
    }

    public int size()
    {
    	return numNodes; // Returns number of nodes(items)
    }

    public String toString()
    {
    	return "";
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
    	return "";
    }
	
    private class Node<K extends Comparable<K>,E>{
    	K key;
    	E element;
    	Node parent;
    	Node leftChild;
    	Node rightChild;
    	public Node(K key, E element)
    	{
    		this.key = key;
    		this.element = element;
    		this.parent = null;
    		this.leftChild = null;
    		this.rightChild = null;
    		
    	}
    	public String toString()
    	{
    		return element.toString();
    	}
    	
    	public String toStringRecursive(Node current)
    	{
    		return "";
    	}
    }
}