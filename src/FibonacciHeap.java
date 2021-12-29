/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
	
	private HeapNode Min;
	private HeapNode First;
	private static int LINKS_COUNTER = 0;
	private static int CUTS_COUNTER = 0;
	private int totalSize = 0;
	private int treesCounter = 0;
	private int markedCounter = 0;

    public FibonacciHeap() { //Create an empty FibHeap. Time Complexity: O(1)
        First = null;
        Min = null;
    }

    public FibonacciHeap(HeapNode root) { //Create a rank 1 FibHeap (single tree). Time Complexity: O(1)
        First = root;
        Min = root;
        treesCounter = 1;
        root.setNext(root); //make root circular
        root.setMark(false); //roots are always unmarked
    }

   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty() { // Time Complexity: O(1)
    	return Min == null;
    }
    
    private void turnIntoEmpty() { // Time Complexity: O(1)
    	this.Min = null;
    	this.First = null;
    	this.totalSize = 0;
    	this.treesCounter = 0;
    	this.markedCounter = 0;
    }

    private void verifyMin(HeapNode x) { // Time Complexity: O(1)
        if (Min == null) Min = x;
        else if (x.getKey() < Min.getKey()) Min = x;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    *
    * Returns the newly created node.
    */
    public HeapNode insert(int key) { // Time Complexity: O(1)
        HeapNode newNode = new HeapNode(key);
        this.left_meld(new FibonacciHeap(newNode));
        this.totalSize++;
    	return newNode;
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin() { // Amortized Time Complexity: O(log n), Worst Case Time Complexity: O(n)
    	if (this.totalSize <= 1) { //Heap will be empty
    		this.turnIntoEmpty();
    		return;
    	}
    	
    	HeapNode child = Min.getChild();
    	if (child != null) { //chaining children
    		child.setParent(null);
    		HeapNode nextTo = child.getNext();
    		while (nextTo != child) {
    			nextTo.setParent(null);
    			nextTo = nextTo.getNext();
    		}
    		
    		HeapNode rightMost = nextTo.getPrev(); //add to parent's layer
    		HeapNode leftMost = child;
    		leftMost.prev = this.Min.getPrev();
    		this.Min.getPrev().next = leftMost;
    		rightMost.next = this.Min;
    		this.Min.prev = rightMost;

    	}
    	HeapNode toBeRemoved = this.Min;
    	toBeRemoved.next.prev = toBeRemoved.prev;
    	toBeRemoved.prev.next = toBeRemoved.next;
    	if (this.First == this.Min) { //temp first
    		if (child != null) this.First = child;
    		else this.First = First.getNext();
    	}
    	toBeRemoved.removeNode(); //floating node
    	
        this.Min = this.First; //temp min       
        this.totalSize--;
        this.treesCounter--;
        this.consolidate();
    }
    
    private HeapNode patchOver(HeapNode del) { //makes del float in space; returns its next. Time Complexity: O(1)
        HeapNode delPrev = del.getPrev();
        HeapNode delNext = del.getNext();
        delPrev.setNext(delNext); //bi-directional link
        del.removeNode(); //floating node

        return delNext;
    }

    public HeapNode link(HeapNode a, HeapNode b) { // Time Complexity: O(1)
        if (a.getKey() >= b.getKey()) { //enforce a < b
            HeapNode swap = a;
            a = b;
            b = swap;
        }

        HeapNode firstChild = a.getChild();
        a.setChild(b);
        if (firstChild != null) { //a has other children
            HeapNode lastChild = firstChild.getPrev();
            b.setNext(firstChild);
            b.setPrev(lastChild);
        }
        else {
            b.setNext(b);
        }
        a.setDegree(a.getDegree() + 1);
        a.setSize(a.getSize() + b.getSize());
        LINKS_COUNTER++;
        this.treesCounter--;
        return a;
    }
    
    private int maxNumOfTrees() { // Time Complexity: O(1)
        double goldenRatio = ((1 + Math.sqrt(5)) / 2);
        int n = 0;
        if (this.totalSize >0) n = (int) Math.ceil(Math.log(this.totalSize) / Math.log(goldenRatio));

        return n+1;
    }

    private void consolidate() { // Amortized Time Complexity: O(log n); Worst Case Time Complexity: O(n)
        HeapNode[] forest = new HeapNode[maxNumOfTrees()];
        HeapNode pivot = this.First;
        pivot.getPrev().setNext(null);

        while (pivot != null) { //adding trees to the forest
        	verifyMin(pivot);
        	HeapNode currPivot = pivot;
        	pivot = pivot.getNext();
        	while (forest[currPivot.getDegree()] != null) {
        		currPivot = link(currPivot,forest[currPivot.getDegree()]);
        		forest[currPivot.getDegree()-1] = null;
        	}
        	forest[currPivot.getDegree()] = currPivot;
        }

        //collecting trees from the forest to our heap
        this.turnIntoEmpty();
        for (int i = forest.length-1; i >= 0; i--) {
        	if (forest[i] == null) {
        		continue;
        	}
            this.left_meld(new FibonacciHeap(forest[i]));
            this.totalSize += this.First.getSize();
        } //this heap is now consolidated
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()  { // Time Complexity: O(1)
    	return this.Min;
    }

   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */

    public void meld (FibonacciHeap heap2) { // Time Complexity: O(1)
        if (heap2.isEmpty()) return;

        else if (this.isEmpty()) {
            this.First = heap2.First;
            this.Min = heap2.Min;
        }
        //connect heaps lazily:
        HeapNode middleLast = this.First.getPrev();
        HeapNode trueLast = heap2.First.getPrev();
        middleLast.setNext(heap2.First); //link is implemented bi-directionally
        trueLast.setNext(this.First); //link is implemented bi-directionally
        this.verifyMin(heap2.Min);
        this.totalSize += heap2.totalSize;
        this.treesCounter += heap2.treesCounter;
        this.markedCounter += heap2.markedCounter;
    }

    private void left_meld (FibonacciHeap heap2) { // Time Complexity: O(1)
        if (heap2.isEmpty()) return;

        else if (this.isEmpty()) {
            this.First = heap2.First;
            this.Min = heap2.Min;
        }

        //connect heaps lazily:
        HeapNode trueLast = this.First.getPrev();
        HeapNode middleLast = heap2.First.getPrev();
        heap2.First.setPrev(trueLast); //link is implemented bi-directionally
        middleLast.setNext(this.First); //link is implemented bi-directionally

        this.First = heap2.First; //new is always to the left

        this.verifyMin(heap2.Min);
        this.totalSize += heap2.totalSize;
        this.treesCounter += heap2.treesCounter;
        this.markedCounter += heap2.markedCounter;
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size() { // Time Complexity: O(1)
    	return this.totalSize;
    }
    
    private int maxRank() { // Time Complexity: O(n)
        int maxRank = First.getDegree();
        HeapNode r = First.getNext();
        while (r != First) {
            if (r.getDegree() > maxRank) maxRank = r.getDegree();
            r = r.getNext();
        }
        return maxRank;
    }
    
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
    * 
    */
    public int[] countersRep() { // Time complexity O(n)
    	if (this.isEmpty()) {
    		return new int[0];
    	}
    	int[] tally = new int[this.maxRank() + 1];
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
    public void delete(HeapNode x) { // Amortized Time Complexity: O(log n), Worst Case Time Complexity: O(n)
    	int distFromMin = x.getKey() - this.Min.getKey();
        decreaseKey(x, distFromMin - 1); //x is now surely the minimum
        this.deleteMin(); //as the new minimum, x has been deleted
    }

    private boolean heapInvariant(HeapNode x) { // Time complexity O(n)
        if (x.isRoot()) return true;
        else return x.getKey() >= x.getParent().getKey();

    }
    
   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta) { // Amortized Time Complexity: O(1), Worst Case Time Complexity: O(log n)
        x.setKey(x.getKey() - delta); //decrease-key
        verifyMin(x);
        if (!heapInvariant(x)) { //invariant has been invalidated
            cascadingCuts(x, x.getParent()); //preforms cascading cuts and melds into the heap
        }
    }
    
    private void cascadingCuts(HeapNode x, HeapNode y) { // Amortized Time Complexity: O(1); Worst Case Time Complexity: O(log n)
    	cut(x,y);
        CUTS_COUNTER++;
        this.left_meld(new FibonacciHeap(x)); //melds cut tree to our heap
		//if (x.isMark()) this.markedCounter--;
        x.setMark(false); //unmark x now that it's been cut

    	if (!y.isRoot()) {
    		if (!y.isMark()) { //mark the father
    			this.markedCounter++;
    			y.setMark(true);
    		}
    		else { //father was already marked -- trigger cascading cuts
    			cascadingCuts(y, y.getParent());
    		}
    	}	
    }
    
    private void cut(HeapNode x, HeapNode y) { //sever x from his father y. Time Complexity: O(1)
    	if (x.isMark()) {
    		this.markedCounter--;
    	}
        x.setParent(null);
        if (y.getDegree() > 0) y.setDegree(y.getDegree() - 1);

        if (x.getNext() == x) { //x is an only child
            y.setChild(null);
        }
        else if (y.getChild() == x) {//x is the first child of many
            y.setChild(x.getNext());
            patchOver(x);
        }
        else { //x is a middle child
            patchOver(x);
        }
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
    public int potential() { // Time Complexity: O(1)
    	return this.treesCounter + (2 * this.markedCounter);
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks() { // Time Complexity: O(1)
    	return LINKS_COUNTER;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts() { // Time Complexity: O(1)   
    	return CUTS_COUNTER;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k) {  // Time Complexity: O(k deg(H))
    	if (k < 0 || H.isEmpty()) {
    		return new int[0];
    	}
    	if (k > H.size()) {
    		k = H.size();
    	}
        int[] arr = new int[k];
        FibonacciHeap kHeap = new FibonacciHeap();
        kHeap.insert(H.findMin().getKey());
        kHeap.findMin().setOriginalHeap(H.findMin());

        for (int i = 0; i < k; i++) {
        	HeapNode curr = kHeap.findMin();
        	arr[i] = curr.getKey();
        	kHeap.deleteMin();
        	HeapNode pointer = curr.getOriginalHeap().getChild();
        	if (pointer == null) continue;
        	do {
        		kHeap.insert(pointer.getKey());
        		HeapNode justIn = kHeap.First;
        		justIn.setOriginalHeap(pointer);
        		pointer = pointer.next;
        	} while (pointer != curr.getOriginalHeap().getChild());
        }
        
        return arr;
    }
        
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{

    	private int key;
        private HeapNode parent;
        private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private int degree;
        private int size;
        private boolean mark;
        private HeapNode originalHeap;

       public HeapNode(int key) { // Time Complexity: O(1)
    		this.key = key;
            this.size = 1;
            this.degree = 0;
            this.mark = false;
    	}

    	public int getKey() { // Time Complexity: O(1)
    		return this.key;
    	}

       public void setKey(int key) { // Time Complexity: O(1)
           this.key = key;
       }

       public HeapNode getParent() { // Time Complexity: O(1)
           return parent;
       }

       public void setParent(HeapNode p) { //link is bi-directional, Time Complexity: O(1)
           this.parent = p;
           if (p != null) p.child = this;

       }

       public HeapNode getChild() { // Time Complexity: O(1)
           return child;
       }

       public void setChild(HeapNode c) { //link is bi-directional, Time Complexity: O(1)
           this.child = c;
           if (c != null) c.parent = this;
       }

       public HeapNode getNext() { // Time Complexity: O(1)
           return next;
       }

       public void setNext(HeapNode n) { //link is bi-directional, Time Complexity: O(1)
           this.next = n;
           if (n != null) n.prev = this;
       }

       public HeapNode getPrev() { // Time Complexity: O(1)
           return prev;
       }

       public void setPrev(HeapNode p) { //link is bi-directional, Time Complexity: O(1)
           this.prev = p;
          if (p != null) p.next = this;
       }

       public int getDegree() { // Time Complexity: O(1)
           return degree;
       }

       public void setDegree(int d) { // Time Complexity: O(1)
           this.degree = d;
       }

       public boolean isMark() { // Time Complexity: O(1)
           return mark;
       }

       public void setMark(boolean m) { // Time Complexity: O(1)
           if (m) this.mark = this.parent != null; //mark node as MARKED only if it is not a root;
           else this.mark = false;
       }

       public boolean isRoot() { // Time Complexity: O(1)
           return this.parent == null;
       }

       public int getSize() { // Time Complexity: O(1)
           return size;
       }

       public void setSize(int s) {
           this.size = s;
       }
       
       private void removeNode() { // Time Complexity: O(1)
    	   this.parent = null;
    	   this.child = null;
    	   this.next = null;
    	   this.prev = null;
    	   this.degree = 0;
       }
       
       public HeapNode getOriginalHeap() { // Time complexity O(n)
    	   return this.originalHeap;
       }
       
       public void setOriginalHeap(HeapNode node) { // Time complexity O(n)
    	   this.originalHeap = node;
       }
   }
}
