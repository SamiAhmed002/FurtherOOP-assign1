package lists;

public class EfficientIntArrayList implements IntList {
    int[] values;
    int len;
    int maxLen;

    public EfficientIntArrayList() {
        values = new int[10];
        len = 0;
        maxLen = 10;
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
        if (len < maxLen) {
            values[len] = value;
        }
        else {
            int[] newValues = new int[len * 2];
            for (int i = 0; i < len; i++) {
                newValues[i] = values[i];
            }
            newValues[len] = value;
            values = newValues;
            maxLen *= 2;
        }
        len++;
    }

    public int length() {
        return len;
    }

    public String getListClassName() {
        return this.getClass().getName();  // Returns "lists.IntArrayList"
    }

    public static void main(String[] args) {
        EfficientIntArrayList list = new EfficientIntArrayList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2));
        System.out.println(list.contains(4));
        System.out.println(list.length());
    }
}
