package model;

public class WordDirection {
    private final Grid.Directions _directions;
    private final Grid.Coordinate _cordinate;
    private final String _word;

    public WordDirection( final Grid.Directions direction, final Grid.Coordinate cordinate, final String word ) {
        _directions = direction;
        _cordinate = cordinate;
        _word = word;
    }

    @Override
    public String toString() {
        return "WordDirection{" +
                "directions=" + _directions +
                ", cordinate=" + _cordinate +
                ", word='" + _word + '\'' +
                "}\n";
    }
}
