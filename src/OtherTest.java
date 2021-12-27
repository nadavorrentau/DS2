//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Collections;


public class OtherTest {
    static FibonacciHeap heap;
    static FibonacciHeap fibonacciHeap;
    static double grade;
    static double testScore;

    public OtherTest() {

    }

    public static void main(String[] args) {
        grade = 80.0D;
        testScore = 2.206896551724138D;

        try {
            test0();
        } catch (Exception var29) {
            bugFound("test0");
        }

        try {
            test1();
        } catch (Exception var28) {
            bugFound("test1");
        }

        try {
            test2();
        } catch (Exception var27) {
            bugFound("test2");
        }

        try {
            test3();
        } catch (Exception var26) {
            bugFound("test3");
        }

        try {
            test4();
        } catch (Exception var25) {
            bugFound("test4");
        }

        try {
            test7();
        } catch (Exception var24) {
            bugFound("test7");
        }

        try {
            test8();
        } catch (Exception var23) {
            bugFound("test8");
        }

        try {
            test9();
        } catch (Exception var22) {
            bugFound("test9");
        }

        try {
            test10();
        } catch (Exception var21) {
            bugFound("test10");
        }

        try {
            test11();
        } catch (Exception var20) {
            bugFound("test11");
        }

        try {
            test12();
        } catch (Exception var19) {
            bugFound("test12");
        }

        try {
            test13();
        } catch (Exception var18) {
            bugFound("test13");
        }

        try {
            test14();
        } catch (Exception var17) {
            bugFound("test14");
        }

        try {
            test15();
        } catch (Exception var16) {
            bugFound("test15");
        }

        try {
            test16();
        } catch (Exception var15) {
            bugFound("test16");
        }

        try {
            test17();
        } catch (Exception var14) {
            bugFound("test17");
        }

        try {
            test18();
        } catch (Exception var13) {
            bugFound("test18");
        }

        try {
            test19();
        } catch (Exception var12) {
            bugFound("test19");
        }

        try {
            test20();
        } catch (Exception var11) {
            bugFound("test20");
        }

        try {
            test21();
        } catch (Exception var10) {
            bugFound("test21");
        }

        try {
            test22();
        } catch (Exception var9) {
            bugFound("test22");
        }

        try {
            test23();
        } catch (Exception var8) {
            bugFound("test23");
        }

        try {
            test24();
        } catch (Exception var7) {
            bugFound("test24");
        }

        try {
            test25();
        } catch (Exception var6) {
            bugFound("test25");
        }

        try {
            test26();
        } catch (Exception var5) {
            bugFound("test26");
        }

        try {
            test27();
        } catch (Exception var4) {
            bugFound("test27");
        }

        try {
            test28();
        } catch (Exception var3) {
            bugFound("test28");
        }

        try {
            test29();
        } catch (Exception var2) {
            System.out.println("Bug found in test29");
            grade -= 8.0D;
        }

        System.exit((int)grade);
    }

    static void test0() {
        String test = "test0";
        fibonacciHeap = new FibonacciHeap();
        ArrayList<Integer> numbers = new ArrayList();

        int i;
        for(i = 0; i < 5; ++i) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        //System.out.println(numbers);

        for(i = 0; i < 5; ++i) {
            fibonacciHeap.insert((Integer)numbers.get(i));
        }

        for(i = 0; i < 5; ++i) {
            System.out.println(fibonacciHeap.findMin().getKey());
            if (fibonacciHeap.findMin().getKey() != i) {
                System.out.println(i);
                System.out.println(fibonacciHeap.findMin().getKey());
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

    }

    static void test1() {
        String test = "test1";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test2() {
        String test = "test2";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeysReverse(0);

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test3() {
        String test = "test3";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        addKeysReverse(4000);
        addKeys(2000);

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test4() {
        String test = "test4";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        addKeysReverse(4000);
        addKeys(2000);

        for(int i = 0; i < 1000; ++i) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        addKeys(6000);
        addKeysReverse(8000);
        addKeys(10000);

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test5() {
        String test = "test5";
        fibonacciHeap = new FibonacciHeap();
        addKeys(0);
        addKeys(0);
        addKeys(0);

        for(int i = 0; i < 1000; ++i) {
            for(int j = 0; j < 3; ++j) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }

                fibonacciHeap.deleteMin();
            }
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test6() {
        String test = "test6";
        fibonacciHeap = new FibonacciHeap();
        addKeysReverse(1000);
        addKeysReverse(1000);
        addKeys(0);
        addKeys(0);
        addKeys(1000);
        addKeys(1000);
        addKeysReverse(0);
        addKeysReverse(0);

        for(int i = 0; i < 2000; ++i) {
            for(int j = 0; j < 4; ++j) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }

                fibonacciHeap.deleteMin();
            }
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test7() {
        String test = "test7";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        addKeysReverse(3000);
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList();

        int i;
        for(i = 2000; i < 3000; ++i) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for(i = 2000; i < 2500; ++i) {
            fibonacciHeap.delete((FibonacciHeap.HeapNode)nodes.get(i - 2000));
        }

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test8() {
        String test = "test8";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList();

        int i;
        for(i = 2000; i < 3000; ++i) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for(i = 2000; i < 2500; ++i) {
            fibonacciHeap.delete((FibonacciHeap.HeapNode) nodes.get(i - 2000));
        }

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test9() {
        String test = "test9";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList();

        int i;
        for(i = 2000; i < 3000; ++i) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        for(i = 2700; i > 2200; --i) {
            fibonacciHeap.delete((FibonacciHeap.HeapNode)nodes.get(i - 2000));
        }

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test10() {
        String test = "test10";
        heap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();
        addKeys(7000);
        addKeysReverse(9000);
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList();

        int i;
        for(i = 2000; i < 3000; ++i) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));
        }

        heap.deleteMin();
        fibonacciHeap.deleteMin();

        for(i = 2700; i > 2200; --i) {
            fibonacciHeap.delete((FibonacciHeap.HeapNode)nodes.get(i - 2000));
        }

        while(!heap.isEmpty()) {
            heap.deleteMin();
            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test11() {
        String test = "test11";
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(9999);
        fibonacciHeap.decreaseKey(h, 9999);
        if (fibonacciHeap.findMin().getKey() != 0) {
            bugFound(test);
        } else {
            fibonacciHeap.deleteMin();

            for(int i = 1000; i < 2000; ++i) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }

                fibonacciHeap.deleteMin();
            }

            if (!fibonacciHeap.isEmpty()) {
                bugFound(test);
            }

        }
    }

    static void test12() {
        String test = "test12";
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(5000);
        fibonacciHeap.decreaseKey(h, 4000);

        int i;
        for(i = 0; i < 2; ++i) {
            if (1000 != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        for(i = 1001; i < 2000; ++i) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        if (!fibonacciHeap.isEmpty()) {
            bugFound(test);
        }

    }

    static void test13() {
        String test = "test13";
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(9000);
        fibonacciHeap.decreaseKey(h, 4000);

        for(int i = 1000; i < 2000; ++i) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        if (5000 != fibonacciHeap.findMin().getKey()) {
            bugFound(test);
        } else {
            fibonacciHeap.deleteMin();
            if (!fibonacciHeap.isEmpty()) {
                bugFound(test);
            }

        }
    }

    static void test14() {
        String test = "test14";
        fibonacciHeap = new FibonacciHeap();
        addKeys(1000);
        addKeysReverse(7000);
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(9000);
        fibonacciHeap.decreaseKey(h, 4000);

        int i;
        for(i = 1000; i < 2000; ++i) {
            if (i != fibonacciHeap.findMin().getKey()) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        if (5000 != fibonacciHeap.findMin().getKey()) {
            bugFound(test);
        } else {
            fibonacciHeap.deleteMin();

            for(i = 7000; i < 8000; ++i) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }

                fibonacciHeap.deleteMin();
            }

            if (!fibonacciHeap.isEmpty()) {
                bugFound(test);
            }

        }
    }

    static void test15() {
        String test = "test15";
        fibonacciHeap = new FibonacciHeap();

        for(int i = 1000; i < 10000; i += 1000) {
            addKeys(i);
        }

        fibonacciHeap.deleteMin();
        FibonacciHeap.HeapNode h = fibonacciHeap.insert(99999);
        fibonacciHeap.decreaseKey(h, 99999);
        if (fibonacciHeap.findMin().getKey() != 0) {
            bugFound(test);
        } else {
            fibonacciHeap.deleteMin();

            for(int i = 1001; i < 10000; ++i) {
                if (i != fibonacciHeap.findMin().getKey()) {
                    bugFound(test);
                    return;
                }

                fibonacciHeap.deleteMin();
            }

            if (!fibonacciHeap.isEmpty()) {
                bugFound(test);
            }

        }
    }

    static void test16() {
        String test = "test16";
        fibonacciHeap = new FibonacciHeap();
        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();
        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        if (fibonacciHeap.potential() != 3 || FibonacciHeap.totalCuts() - cuts != 0 || FibonacciHeap.totalLinks() - links != 0 || fibonacciHeap.countersRep()[0] != 3) {
            bugFound(test);
        }

    }

    static void test17() {
        String test = "test17";
        fibonacciHeap = new FibonacciHeap();
        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();
        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();
        if (fibonacciHeap.potential() != 1 || FibonacciHeap.totalCuts() - cuts != 0 || FibonacciHeap.totalLinks() - links != 1 || fibonacciHeap.countersRep()[0] != 0 || fibonacciHeap.countersRep()[1] != 1) {
            bugFound(test);
        }

    }

    static void test18() {
        String test = "test18";
        fibonacciHeap = new FibonacciHeap();
        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();
        fibonacciHeap.insert(4);
        fibonacciHeap.insert(5);
        fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();
        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();
        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();
        if (fibonacciHeap.potential() != 1 || FibonacciHeap.totalCuts() - cuts != 0 || FibonacciHeap.totalLinks() - links != 3 || fibonacciHeap.countersRep()[0] != 0 || fibonacciHeap.countersRep()[1] != 0 || fibonacciHeap.countersRep()[2] != 1) {
            bugFound(test);
        }

    }

    static void test19() {
        String test = "test19";
        fibonacciHeap = new FibonacciHeap();
        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();
        fibonacciHeap.insert(4);
        fibonacciHeap.insert(5);
        FibonacciHeap.HeapNode node = fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();
        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();
        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();
        fibonacciHeap.decreaseKey(node, 2);
        if (fibonacciHeap.potential() != 4 || FibonacciHeap.totalCuts() - cuts != 1 || FibonacciHeap.totalLinks() - links != 3) {
            bugFound(test);
        }

    }

    static void test20() {
        String test = "test20";
        fibonacciHeap = new FibonacciHeap();
        fibonacciHeap.insert(4);
        FibonacciHeap.HeapNode node5 = fibonacciHeap.insert(5);
        FibonacciHeap.HeapNode node6 = fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();
        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();
        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();
        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();
        fibonacciHeap.decreaseKey(node6, 2);
        fibonacciHeap.decreaseKey(node5, 1);
        if (fibonacciHeap.potential() != 4 || FibonacciHeap.totalCuts() - cuts != 1 || FibonacciHeap.totalLinks() - links != 0) {
            bugFound(test);
        }

    }

    static void test21() {
        String test = "test21";
        fibonacciHeap = new FibonacciHeap();
        int treeSize = '耀';
        int sizeToDelete = 1000;
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList();

        int i;
        for(i = treeSize; i < treeSize * 2; ++i) {
            nodes.add(fibonacciHeap.insert(i));
        }

        for(i = 0; i < sizeToDelete; ++i) {
            fibonacciHeap.insert(i);
        }

        for(i = 0; i < sizeToDelete; ++i) {
            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() != 1) {
            bugFound(test);
        }

    }

    static void test22() {
        String test = "test22";
        fibonacciHeap = new FibonacciHeap();
        int treeSize = '耀';
        int sizeToDelete = 1000;
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList();

        int totalCuts;
        for(totalCuts = treeSize; totalCuts < treeSize * 2; ++totalCuts) {
            nodes.add(fibonacciHeap.insert(totalCuts));
        }

        for(totalCuts = 0; totalCuts < sizeToDelete; ++totalCuts) {
            fibonacciHeap.insert(totalCuts);
        }

        for(totalCuts = 0; totalCuts < sizeToDelete; ++totalCuts) {
            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() != 1) {
            bugFound(test);
        }

        totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();
        boolean noCascading = true;
        Collections.shuffle(nodes);

        for(int i = 0; i < treeSize; ++i) {
            int iterationCuts = FibonacciHeap.totalCuts();
            fibonacciHeap.decreaseKey((FibonacciHeap.HeapNode)nodes.get(i), ((FibonacciHeap.HeapNode)nodes.get(i)).getKey() - (treeSize - i));
            if (FibonacciHeap.totalCuts() - iterationCuts > 1) {
                noCascading = false;
            }
        }

        if (fibonacciHeap.potential() != treeSize || FibonacciHeap.totalCuts() - totalCuts != treeSize - 1 || FibonacciHeap.totalLinks() - links != 0 || fibonacciHeap.countersRep()[0] != treeSize || noCascading) {
            bugFound(test);
        }

    }

    static void test23() {
        String test = "test23";
        fibonacciHeap = new FibonacciHeap();
        int size = 1000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for(int i = size; i > 0; --i) {
            fibonacciHeap.insert(i);
        }

        if (fibonacciHeap.potential() != size || FibonacciHeap.totalCuts() - totalCuts != 0 || FibonacciHeap.totalLinks() - links != 0) {
            bugFound(test);
        }

    }

    static void test24() {
        String test = "test24";
        fibonacciHeap = new FibonacciHeap();
        int size = 2000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for(int i = size; i > 0; --i) {
            fibonacciHeap.insert(i);
        }

        if (fibonacciHeap.potential() != size || FibonacciHeap.totalCuts() - totalCuts != 0 || FibonacciHeap.totalLinks() - links != 0) {
            bugFound(test);
        }

    }

    static void test25() {
        String test = "test25";
        fibonacciHeap = new FibonacciHeap();
        int size = 3000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        for(int i = size; i > 0; --i) {
            fibonacciHeap.insert(i);
        }

        if (fibonacciHeap.potential() != size || FibonacciHeap.totalCuts() - totalCuts != 0 || FibonacciHeap.totalLinks() - links != 0) {
            bugFound(test);
        }

    }

    static void test26() {
        String test = "test26";
        fibonacciHeap = new FibonacciHeap();
        int size = 1000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        int i;
        for(i = size; i > 0; --i) {
            fibonacciHeap.insert(i);
        }

        for(i = 0; i < size / 2; ++i) {
            if (fibonacciHeap.findMin().getKey() != i + 1) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() > 100 || FibonacciHeap.totalCuts() - totalCuts != 0 || FibonacciHeap.totalLinks() - links < size - 100) {
            bugFound(test);
        }

    }

    static void test27() {
        String test = "test27";
        fibonacciHeap = new FibonacciHeap();
        int size = 2000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        int i;
        for(i = size; i > 0; --i) {
            fibonacciHeap.insert(i);
        }

        for(i = 0; i < size / 2; ++i) {
            if (fibonacciHeap.findMin().getKey() != i + 1) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() > 100 || FibonacciHeap.totalCuts() - totalCuts != 0 || FibonacciHeap.totalLinks() - links < size - 100) {
            bugFound(test);
        }

    }

    static void test28() {
        String test = "test28";
        fibonacciHeap = new FibonacciHeap();
        int size = 3000;
        int totalCuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        int i;
        for(i = size; i > 0; --i) {
            fibonacciHeap.insert(i);
        }

        for(i = 0; i < size / 2; ++i) {
            if (fibonacciHeap.findMin().getKey() != i + 1) {
                bugFound(test);
                return;
            }

            fibonacciHeap.deleteMin();
        }

        if (fibonacciHeap.potential() > 100 || FibonacciHeap.totalCuts() - totalCuts != 0 || FibonacciHeap.totalLinks() - links < size - 100) {
            bugFound(test);
        }

    }

    static void test29() {
        fibonacciHeap = new FibonacciHeap();

        for(int i = 0; i < 33; ++i) {
            fibonacciHeap.insert(i);
        }

        fibonacciHeap.deleteMin();
        int[] kmin = FibonacciHeap.kMin(fibonacciHeap, 10);

        for(int i = 0; i < kmin.length; ++i) {
            if (kmin[i] != i + 1) {
                grade -= 8.0D;
                return;
            }
        }

    }

    static void test30() {
        FibonacciHeap firstFibonacciHeap = new FibonacciHeap();
        FibonacciHeap secondFibonacciHeap = new FibonacciHeap();

        int i;
        for(i = 0; i < 100; ++i) {
            firstFibonacciHeap.insert(i);
            secondFibonacciHeap.insert(i + 100);
        }

        for(i = 0; !firstFibonacciHeap.isEmpty(); ++i) {
            FibonacciHeap.HeapNode min = firstFibonacciHeap.findMin();
            int minValue = min.getKey();
            if (minValue != i) {
                grade -= 8.0D;
            }

            firstFibonacciHeap.deleteMin();
        }

    }

    static void bugFound(String test) {
        System.out.println("Bug found in " + test);
        grade -= testScore;
    }

    static void addKeys(int start) {
        for(int i = 0; i < 1000; ++i) {
            heap.insert(start + i);
            fibonacciHeap.insert(start + i);
        }

    }

    static void addKeysReverse(int start) {
        for(int i = 999; i >= 0; --i) {
            heap.insert(start + i);
            fibonacciHeap.insert(start + i);
        }

    }
}
