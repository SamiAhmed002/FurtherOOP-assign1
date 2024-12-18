package reflection.gson;

import com.google.gson.Gson;

class MyObject {
    MyObject ref;
    String name;

    MyObject(String name) {
        this.name = name;
    }

    void setRef(MyObject ref) {
        this.ref = ref;
    }
}

public class CyclicGsonExample {

    public static class CyclicGraphException extends RuntimeException {
        public CyclicGraphException() {
            super("Gson cannot serialize cyclic graph");
        }
    }

    public static String serializeObject(MyObject obj) {
        if (hasCycle(obj, obj)) {
            throw new CyclicGraphException();
        }
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    private static boolean hasCycle(MyObject start, MyObject current) {
        if (current == null) return false;
        if (current.ref == start) return true;
        return hasCycle(start, current.ref);  
    }

    public static void main(String[] args) {
        // Non-cyclic case
        MyObject obj1 = new MyObject("obj1");
        MyObject obj2 = new MyObject("obj2");
        obj1.setRef(obj2);

        try {
            String json = serializeObject(obj1);
            System.out.println("Non-cyclic serialization: " + json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Cyclic case
        obj2.setRef(obj1);

        try {
            String json = serializeObject(obj1);
            System.out.println("Cyclic serialization: " + json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
