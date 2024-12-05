package stats;

public class TestMostRecentObject {
    public static void main(String[] args) {
        MostRecentObject<String> mro = new MostRecentObject<>();
        System.out.println(mro.getMostRecentObject());
        mro.add("Hello");
        System.out.println(mro.getMostRecentObject());
        mro.add("World");
        System.out.println(mro.getMostRecentObject());
        // demonstrate that the type of the object is preserved
        String str = mro.getMostRecentObject();
        System.out.println(str);
        // but the line below will not compile
        // Integer x = mro.getMostRecentObject();
    }
}
