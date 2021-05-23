import model.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WordSearch {

    public static void main(String[] args) throws IOException {

        final Grid grid = new Grid( 10 );
        // grid.display(Arrays.asList( "This", "Is", "A", "Grid" ) );
        final String pathString = String.valueOf(WordSearch.class.getResource("words_alpha.txt")).substring(6);
        final Path path = Paths.get(pathString);
        final List<String> words = Files.readAllLines(path);
        grid.display( words );
    }
}
