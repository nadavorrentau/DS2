import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tester {
    public static void main (String[] args) {

        test0();
        /*



        FibonacciHeap t = new FibonacciHeap();
        for (int i=0; i<11; i++) {
            t.insert(i);
        }
        printRoots(t);

        t.deleteMin();
        printRoots(t);

        t.deleteMin();
        printRoots(t);

        FibonacciHeap.HeapNode t1 = t.insert(30);
        FibonacciHeap.HeapNode t2 = t.insert(40);



        //System.out.println("key: " + t.link(t2,t1).getKey());



        /*
        t.insert(10);
        t.insert(20);
        System.out.println(Arrays.toString(t.countersRep()));

        FibonacciHeap.HeapNode ttt = t.insert(30);
        System.out.println(t.insert(45));


        t.decreaseKey(ttt, 14);
        System.out.println(ttt.getKey());

        System.out.println(Arrays.toString(t.countersRep()));

        printRoots(t);
        System.out.println(t.findMin().getKey());

        t.decreaseKey(ttt, 100);
        System.out.println(t.findMin().getKey());

        /*
        String s1 = "hello";
        String s2 = "goodbye";

        if (!s1.equals(s2)) {
            String swap = s1;
            s1 = s2;
            s2 = swap;
        }

        System.out.println(s1+ s2);

         */



    }

    public static void printRoots(FibonacciHeap fib) {
        FibonacciHeap.HeapNode r = fib.First.getNext();
        List<Integer> l = new ArrayList<>();
        l.add(fib.First.getKey());

        while (r != fib.First) {
            l.add(r.getKey());
            r = r.getNext();
        }

        System.out.println(l);
    }

    public static void walkOver(FibonacciHeap fib) {
        FibonacciHeap.HeapNode r = fib.First.getNext();
        List<Integer> l = new ArrayList<>();
        l.add(fib.First.getKey());

        while (r != fib.First) {
            l.add(r.getKey());
            r = r.getNext();
        }

        System.out.println(l);
    }

    static void test0() {
        FibonacciHeap fibonacciHeap = new FibonacciHeap();
        ArrayList<Integer> numbers = new ArrayList();

        int i;
        for(i = 0; i < 22; ++i) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        //System.out.println(numbers);

        for(i = 0; i < 22; ++i) {
            fibonacciHeap.insert((Integer)numbers.get(i));
        }

        for(i = 0; i < 22; ++i) {
            //System.out.println("i: " + i + " find_min: " + fibonacciHeap.findMin().getKey());
            if (fibonacciHeap.findMin().getKey() != i) {
                System.out.println("i: " + i + " find_min: " + fibonacciHeap.findMin().getKey());
                return;
            }

            fibonacciHeap.deleteMin();
        }

    }
    private void printKids(FibonacciHeap.HeapNode p) {
        FibonacciHeap.HeapNode child = p.getChild();
        int[] arr = new int[25];
        arr[0] = child.getKey();
        FibonacciHeap.HeapNode nex = child.getNext();
        for (int i=0; i<25; i++) {
            if (nex == child) break;
            arr[i] = nex.getKey();
            nex = nex.getNext();
        }
        System.out.println(Arrays.toString(arr));


    }

}
