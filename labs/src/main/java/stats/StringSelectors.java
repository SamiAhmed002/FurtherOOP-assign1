package stats;

public class StringSelectors {

    public Selector<String> longestString() {
        return new Selector<>((a, b) -> a.length() - b.length());
    }

    public Selector<String> shortestString() {
        return new Selector<>((a, b) -> b.length() - a.length());
    }
}


