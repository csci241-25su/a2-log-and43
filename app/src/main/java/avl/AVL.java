/**
 * Author: Logan Leo
 * Date: 7/11/2025
 * Purpose: AVL implementation
 */


package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    if(w.compareTo(n.word) < 0){
      if(n.left == null){
        size += 1;
        n.left = new Node(w, n);
      }
      else{
        bstInsert(n.left, w);
      }
    }
    else if(w.compareTo(n.word) > 0){
      if(n.right == null){
        size += 1;
        n.right = new Node(w, n);
      }
      else{
        bstInsert(n.right, w);
      }
    }
    else{
      return;
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    if(root == null){
      root = new Node(w);
      size = 1;
      updateHeight(root);
    }
    avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    if(w.compareTo(n.word) < 0){
      if(n.left == null){
        size += 1;
        n.left = new Node(w, n);
        updateHeight(n.left);
        updateHeight(n);
      }
      else{
        avlInsert(n.left, w);
      }
    }
    else if(w.compareTo(n.word) > 0){
      if(n.right == null){
        size += 1;
        n.right = new Node(w, n);
        updateHeight(n.right);
        updateHeight(n);
      }
      else{
        avlInsert(n.right, w);
      }
    }
    else{
      return;
    }
    rebalance(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    Node y;
    y = x.right;
    x.right = y.left;
    if(y.left != null){
      y.left.parent = x;
    }
    y.parent = x.parent;
    if(x.parent == null){
      root = y;
    }
    else if(x == x.parent.left){
      x.parent.left = y;
    }
    else{
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
    updateHeight(x);
    updateHeight(y);
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    Node x;
    x = y.left;
    y.left = x.right;
    if(x.right != null){
      x.right.parent = y;
    }
    x.parent = y.parent;
    if(y.parent == null){
      root = x;
    }
    else if(y == y.parent.right){
      y.parent.right = x;
    }
    else{
      y.parent.left = x;
    }
    x.right = y;
    y.parent = x;
    updateHeight(y);
    updateHeight(x);
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    updateHeight(n);
    if(balanceFactor(n) < -1){
      if(balanceFactor(n.left) < 0){
        rightRotate(n);
      }
      else{
        leftRotate(n.left);
        rightRotate(n);
      }
    }
    else if(balanceFactor(n) > 1){
      if(balanceFactor(n.right) < 0){
        rightRotate(n.right);
        leftRotate(n);
      }
      else{
        leftRotate(n);
      }
    }
  }



  /**return the balance factor of a given node */
  private int balanceFactor(Node n){
    return findHeight(n.right) - findHeight(n.left);
  }


  /**updates height of node n */
  private void updateHeight(Node n){
    n.height = findHeight(n);
  }



  /**finds height of node n */
  private int findHeight(Node n){
    if(n == null){
      return -1;
    }
    else{
      return 1 + Math.max(findHeight(n.right), findHeight(n.left));
    }
  }


  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
