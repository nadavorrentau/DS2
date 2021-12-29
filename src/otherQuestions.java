import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class otherQuestions {
	
	public static void main (String[] args) {
		//use only one each run (static variables issue)
		question1(25);
		//question2(10);
	}
	
	public static void question1(int num) {
		System.out.println("### question 1 ###");
        FibonacciHeap heap = new FibonacciHeap();
        int i = num;
        int m = (int) Math.pow(2, i);
        List<FibonacciHeap.HeapNode> forest = new ArrayList<FibonacciHeap.HeapNode>();
        
		Set<Integer> nodesToSave = new HashSet<Integer>();
		for (int j = i; j > 0; j--) {
			nodesToSave.add(m - (int) Math.pow(2, j) + 1);
		}
        
		long startTime = System.nanoTime();
		
        for (int k = m-1; k >= -1; k--) {
        	FibonacciHeap.HeapNode curr = heap.insert(k);
        	if (nodesToSave.contains(k)) {
        		forest.add(curr);
        	}
        }
        
        heap.deleteMin();
        
        for (int k = forest.size()-1; k >= 0; k--) {
        	heap.decreaseKey(forest.get(k), m+1);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        duration /= 1000000;
        
        System.out.println("run-time: " + duration + " ms");
        System.out.println("total links: " + FibonacciHeap.totalLinks());
        System.out.println("total cuts: " + FibonacciHeap.totalCuts());
        System.out.println("Potential: " + heap.potential());
        
        System.out.println("\nhelper - number of trees: " + heap.treesCounter);
        System.out.println("helper - number of marked: " + heap.markedCounter);
	}
	
	public static void question2(int num) {
		System.out.println("### question 2 ###");
        FibonacciHeap heap = new FibonacciHeap();
        int i = num;
        int m = (int) Math.pow(3, i) - 1;
        
        long startTime = System.nanoTime();
        
        for (int k = 0; k <= m; k++) {
        	heap.insert(k);
        }
        
        int limit = (3*m)/4;
        for (int j = 1; j <= limit; j++) {
        	heap.deleteMin();
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        duration /= 1000000;
        
        System.out.println("run-time: " + duration + " ms");
        System.out.println("total links: " + FibonacciHeap.totalLinks());
        System.out.println("total cuts: " + FibonacciHeap.totalCuts());
        System.out.println("Potential: " + heap.potential());
        
        System.out.println("\nhelper - number of trees: " + heap.treesCounter);
        System.out.println("helper - number of marked: " + heap.markedCounter);

	}

}
