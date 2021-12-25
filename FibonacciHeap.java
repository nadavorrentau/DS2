
/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    HeapNode Min;
    HeapNode First;
    int totalSize;
    static int linksCounter = 0;
    static int cutsCounter = 0;

    public FibonacciHeap() { //Create an empty FibHeap. Complexity: O(1)
        First = null;
        Min = null;
        totalSize = 0;
    }

    public FibonacciHeap(HeapNode root) { //Create a rank 1 FibHeap. Complexity: O(1)
        First = root;
        Min = First;
        totalSize = root.getSize();
        root.setPrev(null);
        root.setNext(null);
        root.setMark(false); //roots are always unmarked
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
        if (Min == null) Min = x;
        else if (x.getKey() < Min.getKey()) Min = x;
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
    public void deleteMin() {
        if (Min.getChild() == null) { //Min is childless
            if (First == Min) {
                First = Min.getNext();
                Min.getNext().setPrev(null); //Min is now floating in space
            }
            else {
                Min.getPrev().setNext(Min.getNext()); //Min is now floating in space
            }
        }
        else {
            HeapNode start = Min.getChild();
            cut(start);

            HeapNode nex = start.getNext();
            while (nex != start) {
                cut(nex); //sever ties with father and melds child to heap
                nex = nex.getNext();
            }

            cut(Min); //Min is now floating in space
        }

        this.consolidate();
    }

    public HeapNode link (HeapNode a, HeapNode b) {
        if (a.getKey() > b.getKey()) { //enforce a < b
            HeapNode swap = a;
            a = b;
            b = swap;
        }
        HeapNode kid = a.getChild();
        kid.getPrev().setNext(b);
        b.setNext(kid);
        a.setChild(b);
        linksCounter++;
        a.setDegree(a.getDegree() + 1);

        return a;
    }

    public void consolidate() {
        FibonacciHeap consolidated = new FibonacciHeap();
        FibonacciHeap[] forest = new FibonacciHeap[this.maxRank()];

        HeapNode r = First.getNext();
        forest[First.getDegree()] = new FibonacciHeap(First);

        while (r != this.First) { //successive linking
            boolean moveOn = false;
            HeapNode tmp = r.getNext();
            FibonacciHeap union = new FibonacciHeap(r);
            while (!moveOn) {
                if (forest[r.getDegree()] == null) {
                    forest[r.getDegree()] = union;
                    moveOn = true;
                }
                else { //rank is occupied in array
                    r = link(r, forest[r.getDegree()].First);
                }
            }
            r = tmp;
        }

        for (FibonacciHeap tree: forest) {
            consolidated.meld(tree);
        }

        this.First = consolidated.First;
        this.Min = consolidated.Min;
        this.totalSize = consolidated.totalSize; //this heap is now consolidated
    }

    private int maxRank() {
        double phi = 1.618033989;
        int n = 0;
        if (totalSize >0) n = (int) Math.ceil(Math.log(totalSize) / Math.log(phi));

        return n;
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()  { //Complexity: O(1)
    	return Min;
    }


    private HeapNode findLastRoot() { //Complexity: O(1)
        if (this.First.getPrev() != null) return this.First.getPrev();
        else return First;
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

        HeapNode Last1 = this.findLastRoot();
        HeapNode Last2 = heap2.findLastRoot();
        Last2.setNext(this.First); //link is implemented bi-directionally
        heap2.First.setPrev(Last1);
        this.First = heap2.First; //new is always to the left

        this.verifyMin(heap2.Min);
        this.totalSize += heap2.totalSize;
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()     { //Complexity: O(1)
    	return totalSize;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
    * 
    */
    public int[] countersRep() { //complexity O(log(n))
    	int[] tally = new int[this.maxRank()];
        tally[First.getDegree()]++;

        HeapNode r = First.getNext();

        while (r != First) {
            tally[r.getDegree()]++;
            r = r.getNext();
        }

        return tally;
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
    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey() - delta); //decrease-key
        verifyMin(x);

        if (x.isRoot()) return;
        else if (x.getKey() >= x.getParent().getKey())  return; //heap invariant holds
        else { //invariant has been invalidated
            cascadingCuts(x); //preforms cascading cuts and melds into the heap
        }
    }

    public void cascadingCuts(HeapNode x) {
        HeapNode p = cut(x); //sever x from its father

        while (p != null) { //cascading cuts; root is never marked.
            p = cut(p);
            cutsCounter++;
        }
    }

    public HeapNode cut(HeapNode x) { //sever x from his father and return the father if cut should continue
        HeapNode p = x.getParent();
        x.setParent(null);
        x.setNext(null);
        x.setPrev(null);
        p.setChild(null);

        boolean wasMarked = p.isMark(); //should the cascade continue?
        p.setMark(true); //mark the father

        this.meld(new FibonacciHeap(x)); //meld the freshly cut sapling to the heap

        if (wasMarked) return p;
        else return null;
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
            this.degree = 0;
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

       public void setMark(boolean m) { //Complexity: O(1)
           if (m) this.mark = this.parent != null; //mark node as MARKED only if it is not a root;
           else this.mark = false;
       }

       public boolean isRoot() {
           return this.parent == null;
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
