/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    HeapNode Min;
    HeapNode First;
    static int linksCounter = 0;
    static int cutsCounter = 0;

    public FibonacciHeap() { //Create an empty FibHeap. Complexity: O(1)
        First = null;
        Min = null;
    }

    public FibonacciHeap(HeapNode root) { //Create a single node FibHeap. Complexity: O(1)
        First = root;
        Min = First;
    }

   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty() { //Complexity: O(1)
    	return Min == null;
    }

    public void verifyMin(HeapNode x) { //Complexity: O(1)
        if (x.getKey() < Min.getKey()) Min = x;
    }

    //public void link(HeapNode y)
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key)    { //Complexity: O(1)
        HeapNode newNode = new HeapNode(key);
        FibonacciHeap singularHeap = new FibonacciHeap(newNode);

        this.meld(singularHeap);

    	return newNode;
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin() //Complexity: O(1)
    {
    	return Min;
    }


    private HeapNode findLastRoot() { //Complexity: O(1)
        return this.First.prev;
    }

   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */

    public void meld (FibonacciHeap heap2) { //Complexity: O(1)
        if (heap2.isEmpty()) return;
        else if (this.isEmpty()) {
            this.First = heap2.First;
            this.Min = heap2.Min;
        }

        HeapNode Last2 = heap2.findLastRoot();
        Last2.setNext(this.First); //link is implemented bi-directionally
        heap2.First.setPrev(this.findLastRoot());
        this.First = heap2.First; //new is always to the left

        this.verifyMin(heap2.Min);
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()     { //Complexity: O(1)
    	int s = First.getSize();
        HeapNode n = First.getNext();
        while (n != First) {
            s += n.getSize();
            n = n.getNext();
        }
        return s;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
    * 
    */
    public int[] countersRep()
    {
    	int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) {
        int distFromMin = x.getKey() - this.Min.getKey();
    	decreaseKey(x, distFromMin - 1); //x is now surely the minimal node

        this.deleteMin(); //as the new minimum, x has been deleted
    }

    /*
    public HeapNode find(HeapNode x) {
        int X = x.getKey();

        HeapNode r = this.First;
        int R = r.getKey();

        while (R < X) {
            r = r.getNext();
        }
    }

     */

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey() - delta); //decrease-key

        if (x.getKey() >= x.getParent().getKey()) {//heap invariant holds
            return;
        }
        else { //invariant has been invalidated
            cascadingCuts(x); //preforms cascading cuts and melds into the heap
        }
    }

    public void cascadingCuts(HeapNode x) {
        HeapNode parent = cut(x); //sever x from its father
        this.meld(new FibonacciHeap(x));

        while (parent.isMark()) { //cascading cuts; root is never marked.
            parent = cut(parent);
        }
    }

    public HeapNode cut(HeapNode x) { //sever x from its father and return the father
        HeapNode p = x.getParent();
        x.setParent(null);
        p.setChild(null);

        p.Mark(); //mark the father
        cutsCounter++;

        this.meld(new FibonacciHeap(x)); //meld the freshly cut sapling to the heap

        return p;
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return -234; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return linksCounter;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return cutsCounter;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{

    	public int key;
        private HeapNode parent;
        private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private int degree;
        private int size;
        private boolean mark;

       public HeapNode(int key) {
    		this.key = key;
            this.size = 1;
            this.mark = false;
    	}

    	public int getKey() { //Complexity: O(1)
    		return this.key;
    	}

       public void setKey(int key) { //Complexity: O(1)
           this.key = key;
       }

       public HeapNode getParent() { //Complexity: O(1)
           return parent;
       }

       public void setParent(HeapNode p) { //link is bi-directional, Complexity: O(1)
           this.parent = p;
           if (p != null) p.child = this;

       }

       public HeapNode getChild() { //Complexity: O(1)
           return child;
       }

       public void setChild(HeapNode c) { //link is bi-directional, Complexity: O(1)
           this.child = c;
           if (c != null) c.parent = this;
       }

       public HeapNode getNext() { //Complexity: O(1)
           return next;
       }

       public void setNext(HeapNode n) { //link is bi-directional, Complexity: O(1)
           this.next = n;
           if (n != null) n.prev = this;
       }

       public HeapNode getPrev() { //Complexity: O(1)
           return prev;
       }

       public void setPrev(HeapNode p) { //link is bi-directional, Complexity: O(1)
           this.prev = p;
          if (p != null) p.next = this;
       }

       public int getDegree() { //Complexity: O(1)
           return degree;
       }

       public void setDegree(int d) { //Complexity: O(1)
           this.degree = d;
       }

       public boolean isMark() { //Complexity: O(1)
           return mark;
       }

       public void Mark() { //Complexity: O(1)
           this.mark = this.parent != null; //mark node as MARKED only if it is not a root;
       }

       public int getSize() {
           return size;
       }

       public void updateSize() {
           HeapNode c = this.child;
           int s = c.getSize();

           HeapNode n = c.next;
           while (c != n) {
               s += n.getSize();
               n = n.next;
           }

           this.size = s +1;
       }
   }
}
