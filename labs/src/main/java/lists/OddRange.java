package lists;


import java.util.Iterator;

public class OddRange implements Iterable<Integer> {
    private final int start;
    private final int end;

    public OddRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new OddRangeIterator(start, end);
    }

    private static class OddRangeIterator implements Iterator<Integer> {
        private int current;
        private final int end;

        public OddRangeIterator(int start, int end) {
            if (start % 2 != 0) {
                this.current = start;
            }
            else {
                this.current = ++start;
            }
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return current < end;
        }

        @Override
        public Integer next() {
            current+=2;
            return current - 2;
        }
    }

    public static void main(String[] args) {
        OddRange range = new OddRange(0, 10);
        for (int num : range) {
            System.out.println(num);
        }
    }
}
