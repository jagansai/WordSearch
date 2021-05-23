package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Grid {

    // class Coordinate that represents 2 coordinates in the grid.
    static class Coordinate {
        final int x;
        final int y;

        public Coordinate( int x, int y ) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    enum Directions {
        Horizontal ( "Horizontal" ),
        Vertical ( "Vertical" ),
        Diagonal( "Diagnol" ),
        ReverseHorizontal( "ReverseHorizontal" ),
        ReverseVertical( "ReverseVertical" ),
        ReverseDiagonal( "ReverseDiagonal" );

        private final String _name;

        Directions( final String name ) {
            _name = name;
        }

        public static Directions getValue( final Directions direction ) {
            switch ( direction._name.charAt( 0 ) ) {
                case 'V': return Directions.Vertical;
                default: return Directions.Horizontal;
            }
        }
    }

    private final int gridSize;
    private final char[][] _contents;
    private final List<Coordinate> _coordinates;
    private static final char UNDER_SCORE = '_';
    private final List<WordDirection> _wordsUsed = new ArrayList<>();


    public Grid( int gridSize ) {
        this.gridSize = gridSize;
        _contents = new char[gridSize][gridSize];
        _coordinates = new ArrayList<>( gridSize * 2 );
        for ( int i = 0; i < gridSize; ++i ) {
            for ( int j = 0; j < gridSize; ++j ) {
                _contents[i][j] = UNDER_SCORE;
                _coordinates.add( new Coordinate( i, j ) );
            }
        }
    }

    public void display( final List<String> words ) {

        fillGrid( words );
        // print data.
        for ( int i = 0; i < gridSize; ++i ) {
            for ( int j = 0; j < gridSize; ++j ) {
                System.out.print( _contents[i][j] + " " );
            }
            System.out.println();
        }
        // Now display the words that are in the grid.
        System.out.println("\n\n");
        System.out.println( _wordsUsed );
    }

    private void fillGrid( final List<String> words ) {
        Collections.shuffle( words );
        _wordsUsed.clear();
        if ( !words.isEmpty() ) {
            for (String word : words ){
                Collections.shuffle( _coordinates );
                if ( word.length() > gridSize )
                    continue;
                fillGridByLoopingThroughCoordinates( word );
            }
        }
    }

    private void fillGridByLoopingThroughCoordinates( final String word ) {
        final List<Directions> directions = Arrays.asList(Directions.values());
        Collections.shuffle( directions );
        for ( var direction : directions ) {
            for (Coordinate coordinate : _coordinates) {
                if ( hasEnoughSpace( direction, coordinate.x, coordinate.y, word.length() ) ) {
                    fillInTheDirection( direction, coordinate.x, coordinate.y, word );
                    _wordsUsed.add( new WordDirection( direction, coordinate, word ) );
                    return;
                }
            }
        }
    }

    private void fillInTheDirection(final Directions direction, final int x, final int y, final String word ) {
        switch ( direction ) {
            case Horizontal -> fillInHorizontalDirection( x, y, word );
            case Vertical -> fillInVerticalDirection( x, y, word );
            case Diagonal -> fillInDiagonalDirection( x, y, word );
            case ReverseHorizontal -> fillInReverseHorizontalDirection( x, y, word );
            case ReverseVertical -> fillInReverseVerticalDirection( x, y, word );
            case ReverseDiagonal -> fillInReverseDiagonalDirection( x, y, word );
        }
    }

    private void fillInReverseHorizontalDirection( final int x, int y, final String word ) {
        for ( char ch : word.toCharArray() ) {
            _contents[x][y--] = ch;
        }
    }

    private void fillInHorizontalDirection( final int x, int y, final String word ) {
        for ( char ch : word.toCharArray() ) {
            _contents[x][y++] = ch;
        }
    }

    private void fillInReverseVerticalDirection( int x, final int y, final String word ) {
        for ( char ch : word.toCharArray() ) {
            _contents[x--][y] = ch;
        }
    }

    private void fillInVerticalDirection( int x, final int y, final String word ) {
        for ( char ch : word.toCharArray() ) {
            _contents[x++][y] = ch;
        }
    }

    private void fillInReverseDiagonalDirection( int x, int y, final String word ) {
        for ( char ch : word.toCharArray() ) {
            _contents[x--][y--] = ch;
        }
    }

    private void fillInDiagonalDirection( int x, int y, final String word ) {
        for ( char ch : word.toCharArray() ) {
            _contents[x++][y++] = ch;
        }
    }

    private boolean hasEnoughSpace( final  Directions direction, final int x, final int y, final int wordSize ) {

        return switch ( direction ) {
            case Horizontal -> hasEnoughSpaceForHorizontal(x, y, wordSize);
            case Vertical ->   hasEnoughSpaceForVertical( x, y, wordSize );
            case Diagonal ->   hasEnoughSpaceForDiagonal( x, y , wordSize );
            case ReverseHorizontal -> hasEnoughSpaceForReverseHorizontal( x, y, wordSize );
            case ReverseVertical -> hasEnoughSpaceForReverseVertical( x, y, wordSize );
            case ReverseDiagonal -> hasEnoughSpaceForReverseDiagonal( x, y, wordSize );
        };
    }

    private boolean hasEnoughSpaceForReverseDiagonal( int x, int y, int wordSize ) {
        final int requiredSizeToFit = Math.max( x , y );
        if ( wordSize > requiredSizeToFit )
            return false;
        int i = x;
        int j = y;
        for ( ; i >= 0 && j >= 0; --i, --j, --wordSize ) {
            if ( _contents[i][j] != UNDER_SCORE ) {
                return false;
            }
        }
        return wordSize == 0; // this confirms that the word fits.
    }

    private boolean hasEnoughSpaceForDiagonal( int x, int y, int wordSize ) {
        final int requiredSizeToFit = Math.max( x + wordSize, y + wordSize );
        if ( requiredSizeToFit > gridSize )
            return false;
        int i = x;
        int j = y;
        for ( ; i < requiredSizeToFit && j < requiredSizeToFit; ++i, ++j, --wordSize ) {
            if ( _contents[i][j] != UNDER_SCORE ) {
                return false;
            }
        }
        return wordSize == 0; // this confirms that the word fits.
    }

    private boolean hasEnoughSpaceForReverseVertical( final int x, final int y, int wordSize ) {
        if ( wordSize > x ) // if x has to traverse to top, then size of the word should be less than index of x.
            return false;
        int i = x;
        for ( ; i > 0; --i, --wordSize ) {
            if ( _contents[i][y] != UNDER_SCORE ) {
                return false;
            }
        }
        return wordSize == 0; // this confirms that the word fits.
    }

    private boolean hasEnoughSpaceForVertical( int x, int y, int wordSize ) {
        final int requiredSizeToFit = x + wordSize;
        if ( requiredSizeToFit > gridSize )
            return false;
        int i = x;
        for ( ; i < requiredSizeToFit; ++i, --wordSize ) {
            if ( _contents[i][y] != UNDER_SCORE ) {
                return false;
            }
        }
        return wordSize == 0; // this confirms that the word fits.
    }

    private boolean hasEnoughSpaceForReverseHorizontal( final int x, final int y, int wordSize ) {
        if ( wordSize > y ) // y should travese to the left and hence y should be able to traverse till the size of the word.
            return false;
        int i = y;
        for ( ; i > 0; --i, --wordSize ) {
            if ( _contents[x][i] != UNDER_SCORE ) {
                return false;
            }
        }
        return wordSize == 0; // this confirms that the word fits.
    }

    private boolean hasEnoughSpaceForHorizontal(int x, int y, int wordSize) {
        final int requiredSizeToFit = y + wordSize;
        if ( requiredSizeToFit > gridSize )
            return false;
        int i = y;
        for ( ; i < requiredSizeToFit; ++i, --wordSize ) {
            if ( _contents[x][i] != UNDER_SCORE ) {
                return false;
            }
        }
        return wordSize == 0; // this confirms that the word fits.
    }
}
