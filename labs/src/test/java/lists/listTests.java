package lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

record GraphDataResultSet(
        List<Integer> xValues,
        Map<String, List<Double>> results
) {}

public class listTests {
    static long time_n_appends(IntList list, int n) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list.append(i);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        IntList[] lists = {
                //new IntArrayList(),
                //new IntLinkedList(),
                new EfficientIntLinkedList(),
                //new EfficientIntArrayList()
        };

        List<Supplier<IntList>> listMakers = new ArrayList<>();
        //listMakers.add(IntArrayList::new);
        //listMakers.add(IntLinkedList::new);
        listMakers.add(EfficientIntLinkedList::new);
        listMakers.add(EfficientIntArrayList::new);
        listMakers.add(() -> new GenIntListWrapper(new GenericLinkedList<Integer>()));
        listMakers.add(() -> new GenIntListWrapper(new GenericArrayList<Integer>()));
        //listMakers.add(() -> new GenIntListWrapper(new GenericLinkedListRecord<Integer>()));

        int initial_n = 2200000;
        int n_step = 200000;
        int n_max = 4000000;
        List<Integer> xValues = new ArrayList<>();
        for (int n = initial_n; n <= n_max; n += n_step) {
            xValues.add(n);
        }

        Map<String, List<Double>> results = new HashMap<>();
        for (Supplier<IntList> listMaker : listMakers) {
            IntList listInstance = listMaker.get();
            String seriesName = listInstance.getListClassName();
            List<Double> series = new ArrayList<>();
            System.out.println("List class: " + seriesName);
            for (int n = initial_n; n <= n_max; n += n_step) {
                System.out.println(n);
                long t = time_n_appends(listMaker.get(), n);
                series.add((double) t);
                System.out.println(t);
            }
            results.put(seriesName, series);
        }
        System.out.println(new GraphDataResultSet(xValues, results));
    }
}
