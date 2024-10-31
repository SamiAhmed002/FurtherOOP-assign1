package lists;

interface GenericList<T> extends Iterable<T> {
    boolean contains(T value);

    void append(T value);

    int length();
}

