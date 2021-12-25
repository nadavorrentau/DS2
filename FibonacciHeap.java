/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    HeapNode Min;
    HeapNode First;
    int size;
    int linksCounter = 0;
    int cutsCounter = 0;

    public FibonacciHeap() { //Create an empty FibHeap. Complexity: O(1)
        First = null;
        Min = null;
        size = 0;
    }

    public FibonacciHeap(HeapNode root) { //Create a single node FibHeap. Complexity: O(1)
        First = root;
        Min = First;
        size = 1;
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

    private void updateSize(FibonacciHeap otherHeap) { //Complexity: O(1)
        this.size += otherHeap.size();
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
        this.updateSize(heap2);
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()     { //Complexity: O(1)
    	return this.size;
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

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
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
    	return -345; // should be replaced by student code
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
    	return -456; // should be replaced by student code
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
        private   HeapNode degree;
        private boolean mark;

       public HeapNode(int key) {
    		this.key = key;
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

       public void setParent(HeapNode p) { //Complexity: O(1)
           this.parent = p;
       }

       public HeapNode getChild() { //Complexity: O(1)
           return child;
       }

       public void setChild(HeapNode c) { //Complexity: O(1)
           this.child = c;
       }

       public HeapNode getNext() { //Complexity: O(1)
           return next;
       }

       public void setNext(HeapNode n) { //link is bi-directional, Complexity: O(1)
           this.next = n;
           n.prev = this;
       }

       public HeapNode getPrev() { //Complexity: O(1)
           return prev;
       }

       public void setPrev(HeapNode p) { //link is bi-directional, Complexity: O(1)
           this.prev = p;
           p.next = this;
       }

       public HeapNode getDegree() { //Complexity: O(1)
           return degree;
       }

       public void setDegree(HeapNode d) { //Complexity: O(1)
           this.degree = d;
       }

       public boolean isMark() { //Complexity: O(1)
           return mark;
       }

       public void Mark() { //Complexity: O(1)
           this.mark = this.parent != null; //mark node as MARKED only if it is not a root;
       }
   }
}
