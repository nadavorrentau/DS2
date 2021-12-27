import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tester {
    public static void main (String[] args) {



        FibonacciHeap t = new FibonacciHeap();
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
}
