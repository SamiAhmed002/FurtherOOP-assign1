package lists;

class IntLinkedList implements IntList {
    IntNode head;
    int len;

    public IntLinkedList() {
        head = null;
        len = 0;
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
            IntNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
        len += 1;
    }

    public int length() {
        return len;
    }

    public String getListClassName() {
        return this.getClass().getName();  // Returns "lists.IntArrayList"
    }

    public static void main(String[] args) {
        IntLinkedList list = new IntLinkedList();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list.contains(2));
        System.out.println(list.contains(4));
        System.out.println(list.length());
    }
}

// a IntNode for each element in LinkedList
class IntNode {
    int value;
    IntNode next;

    public IntNode(int value) {
        this.value = value;
        this.next = null;
    }
}
