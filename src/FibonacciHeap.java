import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
	public HeapNode Min;
	public HeapNode First;
	public int totalSize = 0;
	public static int linksCounter = 0;
	public static int cutsCounter = 0;
	public int treesCounter = 0;
	public int markedCounter = 0;


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

    //public void link(HeapNode y)
		
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
        totalSize++;
    	return newNode;
    }

    /*
    private void insertNodeToHeap(HeapNode newNode) { // Time Complexity: O(1)
        newNode.setParent(null);
        //newNode.setChild(null);
        HeapNode oldFirst = this.First;
        if (oldFirst != null) {
        	HeapNode oldPrev = oldFirst.getPrev();
        	newNode.setNext(oldFirst);
        	oldPrev.setNext(newNode);
        }
        else {
        	newNode.setNext(newNode);
        }
        this.First = newNode;
        verifyMin(this.First);
        this.treesCounter++;
        this.totalSize++;
    }

     */

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin() { // Amortized Time Complexity: O(log n), Worst Case Time Complexity: O(n)
        printForest(Min);
        System.out.println("MINIMUM IS:   " + Min.getKey() + "  BEHOLD!");
        System.out.println(Min.getKey());
        printKids(Min);
    	if (this.totalSize <= 1) { //Heap will be empty
    		this.turnIntoEmpty();
    		return;
    	}

        if (Min.getChild() == null) { //Min is childless (a single node)
            if (First == Min) { //min was first
                First = patchOver(Min);
                System.out.println("A");
            }
            else { //min wasn't first
                patchOver(Min);
                System.out.println("B");
            }
        }
        else { //min is a parent
            HeapNode child = Min.getChild();
            if (First == Min) First = child;
            while (!child.isRoot()) {
                HeapNode nex = child.getNext();
                cut(child, Min); //severs tie with father
                Min.getPrev().setNext(child);
                child.setNext(Min.getNext());
                treesCounter++;
                child = nex; //move on to the next kid
            }
            patchOver(Min);
            System.out.println("C");
            //Min.removeNode();
        }
        Min = null;
        //System.out.println("here " + this.Min.getKey() + " " + this.First.getKey());
        this.totalSize--;
        this.treesCounter--;
        this.consolidate();
    }

    private void printForest(HeapNode p) {
        System.out.println("PRINT FOREST ~~~~~~~~~~~~ PRINT FOREST");
        printTree(First);

        HeapNode r = First.getNext();
        while (r != First) {
            printTree(r);
            r = r.getNext();
        }

    }

    private void printTree(HeapNode p) {
        System.out.println("PrintTree: Tree root is "  + p.getKey());
        while (p != null) {
            printKids(p);
            p = p.getChild();
        }
    }

    private void printKids(HeapNode p) {
        if (p.getChild() == null) {
            System.out.println("Childless");
            return;
        }
        //System.out.println("Min prev: " + p.getPrev().getKey() + " Min next " + p.getNext().getKey());
        HeapNode child = p.getChild();
        List<Integer> l= new ArrayList<>();
        l.add(child.getKey());
        HeapNode nex = child.getNext();
        System.out.println(child.getKey());
        while (nex != child) {
            l.add(nex.getKey());
            System.out.println("child: " + child.getKey() + " nex " + nex.getKey());
            nex = nex.getNext();
        }
        System.out.println(l);
    }

    private HeapNode patchOver(HeapNode del) {//makes del float in space; returns its next;
        HeapNode delPrev = del.getPrev();
        HeapNode delNext = del.getNext();
        //System.out.println("del: " + del.getKey() + " del prev " + del.getPrev());
        delPrev.setNext(delNext); //bi-directional link
        del.setNext(del); //bi-directionally connect del to itself

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
        //a.setSize(a.getSize() + b.getSize() + 1);
        linksCounter++;
	this.treesCounter--;
        return a;
    }

    private void consolidate() { // Amortized Time Complexity: O(log n); Worst Case Time Complexity: O(n)
        HeapNode[] forest = new HeapNode[this.treesCounter + 1];
        System.out.println("NEW RUN");

        forest[First.getDegree()] = First;
        HeapNode r = First.getNext();
        while (r != this.First) { //successive linking
            HeapNode nex = r.getNext();
            while (forest[r.getDegree()] != null) {//node r has not found its eternal resting place
                //System.out.println("r key: " + r.getKey() + " other key: " + forest[r.getDegree()].getKey() );
                r = link(r, forest[r.getDegree()]); //link between r and the occupant
                //System.out.println("r key: " + r.getKey());
                forest[r.getDegree() - 1] = null; //vacate their former cell
            }
            forest[r.getDegree()] = r; //place their union in an empty spot
            System.out.println("r (key) : " + r.getKey());
            r = nex;
        }

        System.out.println(Arrays.toString(forest));

        First = null;
        HeapNode a = null;
        for (HeapNode root : forest) {
            if (root != null && First == null) {
                First = root;
                a = root;
                System.out.println("a: " + a + " key: " + a.getKey() + " degree: " + a.getDegree());
                verifyMin(First);
            }
            else if (root != null) {
                a.setNext(root);
                a = root;
                System.out.println("a: " + a + " key: " + a.getKey());
                verifyMin(root);
            }
        }
        this.First.setPrev(a); //this heap is now consolidated
    }
        /*
        HeapNode dummyNode = new HeapNode(Integer.MAX_VALUE); //forest[r.getDegree()] == dummyNode ||
         || r.getDegree() == (forest.length-1)

         */
        
        /*
        int minIndex = 0;
        for (int i = 0; i < forest.length; i++) { // find the tree with the min degree, and set him to be First at this Heap.
        	if (forest[i] != null && forest[i] != dummyNode) {
                this.First = forest[i];
                this.Min = forest[i];
                this.totalSize = forest[i].getSize();
                this.treesCounter = 1;
                minIndex = i;
                break;
        	}
        }



        HeapNode curr = this.First;
        curr.setNext(curr);
        curr.setPrev(curr);
        for (int i = minIndex+1; i < forest.length; i++) { // adding the other trees to this heap.
        	if (forest[i] != null || forest[i] != dummyNode) {
        		continue;
        	}
        	curr.setNext(forest[i]);
        	verifyMin(forest[i]);
        	this.totalSize += forest[i].getSize();
        	
        	curr = forest[i];
        	this.First.setPrev(curr);
        	this.treesCounter++;
        }

         */


    private int maxRank() { // Time Complexity: O(n)
        int maxRank = First.getDegree();
        HeapNode r = First.getNext();
        while (r != First) {
            if (r.getDegree() > maxRank) maxRank = r.getDegree();
            r = r.getNext();
        }
        return maxRank;
    }
    /*

    private int maxRank(HeapNode[] arr) { // Time Complexity: O(1)
        for (int n=arr.length -1; n>=0; n--) {
            if (arr[n] != null) return n;
        }
        return 0;
    }

     */

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()  { // Time Complexity: O(1)
    	return Min;
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

    public void left_meld (FibonacciHeap heap2) { // Time Complexity: O(1)
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
    	return totalSize;
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

    private boolean heapInvariant(HeapNode x) {
        if (x.isRoot()) return true;
        else return x.getKey() >= x.getParent().getKey();

    }
    
    private void cascadingCuts(HeapNode x, HeapNode y)
    { // Amortized Time Complexity: O(1); Worst Case Time Complexity: O(log n)
    	cut(x,y);
        this.left_meld(new FibonacciHeap(x)); //melds cut tree to our heap
	if (x.isMark()) markedCounter--;
        x.setMark(false); //unmark x now that it's been cut

    	if (!y.isRoot()) {
    		if (!y.isMark()) { //mark the father
			if (!x.isMark()) markedCounter++;
    			y.setMark(true);
    		}
    		else { //father was already marked -- trigger cascading cuts
    			cascadingCuts(y, y.getParent());
    		}
    	}	
    }
    
    private void cut(HeapNode x, HeapNode y) { //sever x from his father y. Time Complexity: O(1)
        x.setParent(null);
        y.setDegree(y.getDegree() - 1);

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


    
    /*
    private void cascadingCuts(HeapNode x) { // Time Complexity: ///////////////////////////////////////////////////////////////////////////
        HeapNode p = cut(x); //sever x from its father

        while (p != null) { //cascading cuts; root is never marked.
            p = cut(p);
            cutsCounter++;
        }
    }

    private HeapNode cut(HeapNode x) { //sever x from his father and return the father if cut should continue. Time Complexity: O(1)
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
    */

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
    	return linksCounter;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts() { // Time Complexity: O(1)   
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
    public static int[] kMin(FibonacciHeap H, int k) {  // Time Complexity: O(k deg(H))
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

    	public int key;
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
           this.setParent(null);
           this.setChild(null);
           this.setNext(this);
           this.setPrev(this);
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
       
       public HeapNode getOriginalHeap() {
    	   return this.originalHeap;
       }
       
       public void setOriginalHeap(HeapNode node) {
    	   this.originalHeap = node;
       }
   }
}
