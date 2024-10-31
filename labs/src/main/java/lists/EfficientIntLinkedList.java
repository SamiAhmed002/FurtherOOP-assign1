package lists;

class EfficientIntLinkedList implements IntList {
    IntNode head;
    int len;
    IntNode tail;

    public EfficientIntLinkedList() {
        head = null;
        len = 0;
        tail = null;
    }

    public boolean contains(int value) {
        IntNode current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void append(int value) {
        IntNode node = new IntNode(value);
        if (head == null) {
            head = node;
        }
        else {
            tail.next = node;
        }
        tail = node;
        len += 1;
    }

    public int length() {
        return len;
    }

    public String getListClassName() {
        return this.getClass().getName();  // Returns "lists.IntArrayList"
    }

    public static void main(String[] args) {
        EfficientIntLinkedList list = new EfficientIntLinkedList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2));
        System.out.println(list.contains(4));
        System.out.println(list.length());
    }
}
