package utils;

public class Pair< T1 extends Object, T2 extends Object > {

    private final T1 _field1;
    private final T2 _field2;

    public Pair( final T1 field1, final T2 fields2 ) {
        _field1 = field1;
        _field2 = fields2;
    }

    public T1 getFirst() {
        return _field1;
    }

    public T2 getSecond() {
        return _field2;
    }
}
