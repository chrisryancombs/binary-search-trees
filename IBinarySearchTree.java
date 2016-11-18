// This represents a simplified bst interface to be implemented as a binary
// search tree for CS249.
public interface IBinarySearchTree<K extends Comparable<K>,E> {

    ////////////////////////////////////////////////////////////////////////////
    // Public interface

    //  adds a node to the tree containing this key element combination.
    void put(K key, E element);

    // retrives an element from the tree based on the key.
    // throws a NoSuchElementException if no such key exists.
    E get(K key);

    // Returns the number of elements in this tree.
    int size();

    // Returns this map in string form, surrounded by [] brackets and with each
    // items separated by a comma and space. each key element pair should be
    // written as key: element.  For example:
    // [1: Dog, 2: Cat, 3: Bird, 4: Cow]
    // the order of pairs should be in ascending key order.
    String toString();

    ////////////////////////////////////////////////////////////////////////////
    // Private interface
    //
    // Normally, everything past this point would be private.  For the sake of
    // testing in CS249, however, all of these must be provided as public.

    // returns the highest distance from the root to a leaf node in the entire
    // tree.
    int getHeight();

    // Returns a string depiction of this BST as a tree.  Each node is depicted
    // as [Key, LeftBranch, RightBranch] where LeftBranch and RightBranch are
    // either null or another node.  In a Red-Black tree use [Color, Key,
    // LeftBranch, RightBranch] instead.  Should the tree be empty return [].
    String getTreeString();
}
