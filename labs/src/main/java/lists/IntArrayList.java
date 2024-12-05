package lists;

public class IntArrayList implements IntList {
    int[] values;
    int len;

    public IntArrayList() {
        values = new int[0];
        len = 0;
    }

    public boolean contains(int value) {
        for (int i = 0; i < len; i++) {
            if (values[i] == value) {
                return true;
            }
        }
        return false;
    }

    public void append(int value) {
        // this is inefficient but leave as is for now
        int[] newValues = new int[len + 1];
        for (int i = 0; i < len; i++) {
            newValues[i] = values[i];
        }
        newValues[len] = value;
        values = newValues;
        len++;
    }

    public int length() {
        return len;
    }

    public String getListClassName() {
        return this.getClass().getName();  // Returns "lists.IntArrayList"
    }

    public static void main(String[] args) {
        IntArrayList list = new IntArrayList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2));
        System.out.println(list.contains(4));
        System.out.println(list.length());
    }
}
