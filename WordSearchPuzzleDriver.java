/**
 * @author
 * Dawid Kocik      19233116
 * Tomás Crowley    19263546
 * @version         06/04/2020
 */
import java.util.ArrayList;
import java.util.Arrays;
public class WordSearchPuzzleDriver {
    public static void main(String[] args) {
        System.out.println("First we generate a 2d array containing 8 words no more than 20 characters long from a word file. Unused positions are filled with random characters. ");
        WordSearchPuzzle test1 = new WordSearchPuzzle("BasicEnglish.txt", 8, 0, 20);
        System.out.println("We could obtain the puzzle as grid and then display it");
        System.out.println(Arrays.deepToString(test1.getPuzzleAsGrid()).replace("], ", "]\n").substring(1).replace("]]", "]"));
        System.out.println("\nor we can display it as a string.");
        System.out.println(test1.getPuzzleAsString());
        System.out.println("The list of words used can be displayed");
        
        System.out.println(test1.getWordSearchList().toString().substring(1).replace("]", ""));
        System.out.println("And the words can also be displayed with the position and and direction shown");
        test1.showWordSearchPuzzle(false);
        System.out.println("\nor hidden.\n");
        test1.showWordSearchPuzzle(true);
        
        System.out.println("\nThe puzzle words can also be supplied by the user or the programme itself.");
        ArrayList<String> animals = new ArrayList<String>(Arrays.asList("tiger", "elephant","monkey"));
        WordSearchPuzzle test2 = new WordSearchPuzzle(animals);
        test2.showWordSearchPuzzle(false);
        
        
        System.out.println("\nWe can also add words to this ArrayList to expand the program");
        animals.add("kangaroo");
        animals.add("koala");
        WordSearchPuzzle test3 = new WordSearchPuzzle(animals);
        System.out.println(test3.getPuzzleAsString());
        System.out.println(test3.getWordSearchList().toString().substring(1).replace("]", ""));
        System.out.println("\nOr we can remove them.");
        animals.remove("tiger");
        animals.remove("elephant");
        animals.remove(1);
        WordSearchPuzzle test4 = new WordSearchPuzzle(animals);
        System.out.println(test4.getWordSearchList().toString().substring(1).replace("]", ""));
        System.out.println(test4.getPuzzleAsString());
        
        
    }
}