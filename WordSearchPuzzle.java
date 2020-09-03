/**
 * @author
 * Dawid Kocik      19233116
 * Tomás Crowley    19263546
 * @version         06/04/2020
 */
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Arrays;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordSearchPuzzle {
    private char[][] puzzle;
    private List<String> puzzleWords;
    private int puzzleLen;
    private int[][] posTable;
    private String dirArrows;
    private final String ARROWS = "\u2197\u2193\u2190\u2192\u2198\u2196\u2197\u2199";
    
    public WordSearchPuzzle(List<String> userSpecifiedWords) {
        userSpecifiedWords.replaceAll(String::toUpperCase); // lambda function application, works in java 8
        //convert words to upper case, works in java 8
        this.puzzleWords = userSpecifiedWords;
        generateWordSearchPuzzle();
    }
    public WordSearchPuzzle(String wordFile, int wordCount, int shortest, int longest) {
        String aWord;
        int listIndex;
        this.puzzleWords = new ArrayList<String>(wordCount); 
        LinkedList<String> suitableWords =  new LinkedList<String>();
        try {
            FileReader aFileReader = new FileReader(wordFile); //get the read access
            BufferedReader aBufferReader = new BufferedReader(aFileReader); // memory buffer
            aWord = aBufferReader.readLine();
            while (aWord != null) {
                if (aWord.length() <= longest && aWord.length() >= shortest) {
                    suitableWords.add(aWord.toUpperCase());
                }
                aWord = aBufferReader.readLine();
            }
            aBufferReader.close();
            aFileReader.close();
            while ( wordCount > 0) {
                listIndex = (int)(Math.random()*suitableWords.size());
                puzzleWords.add(suitableWords.remove(listIndex));
                wordCount--;
            }
            generateWordSearchPuzzle();
        } catch (IOException x) {
            System.out.println(x);
            System.out.printf("File %s not found, please try again.\n", wordFile);
        }
    }
    public char[][] getPuzzleAsGrid() {
        return puzzle;
    }
    public List<String> getWordSearchList() {
        return puzzleWords;
    }
    public String getPuzzleAsString() {
        String puzzleString ="";
        for(int row = 0; row < puzzle.length; row++) {
            for(int col = 0; col <puzzle[0].length; col++){
                puzzleString = puzzleString +puzzle[row][col];
            }
            puzzleString += "\n";
        }
        return puzzleString;
    }
    private void generateWordSearchPuzzle() {
        String alphabet ="ABCDEFGHIJKLMNOPQRSTUVWYXZ";
        int sum = 0; 
        int direction=0; 
        boolean flag; //when flag is true, program will keep trying to place a word
        int len, row, col; // word length, row, column
        int k=0, l=0, q=0;
        dirArrows = ""; // String for storing directions of each word
        this.posTable = new int[puzzleWords.size()][2]; //for storing starting positions
        // Arrange words by descending length,easier to fit
        Collections.sort(puzzleWords, new Comparator<String>() 
        {
            public int compare(String x, String y) {
                return y.length() - x.length();
            }
        }
        );
        int longest = puzzleWords.get(0).length();
        for(String word : puzzleWords){
            sum+=word.length();
        }
        sum = (int)(Math.sqrt(sum*1.5));
        puzzleLen = Math.max(longest+2,sum); 
        // set grid size to the longest word+2 or the square root of sum of letters of words multiplied by 1.5, whichever bigger
        this.puzzle = new char[puzzleLen][puzzleLen];
        for(String word : puzzleWords) {
            flag = true;
            len = word.length();
            row = (int) (Math.random()*puzzleLen);
            col = (int) (Math.random()*puzzleLen);
            while (flag) {
                direction = (int)(Math.random()*8);
                switch (direction) {
                    case 0:     k = -1;
                                l = 0; //up
                                if(row-len > -2) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                    case 1:     k = 1; //down
                                l = 0;
                                if(row+len < puzzleLen+1) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                    case 2:     l = -1; //left
                                k = 0;
                                if(col-len > -2) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                    case 3:     l = 1; //right
                                k = 0;
                                if(col+len < puzzleLen+1) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                                
                    case 4:     l = 1; //downright
                                k = 1;
                                if(col+len < puzzleLen+1 && row+len < puzzleLen+1) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                                
                    case 5:     l = -1; //upleft
                                k = -1;
                                if(col-len > -2 && row-len > -2) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                                
                    case 6:     l = 1; //upright
                                k = -1;
                                if(col+len < puzzleLen+1 && row-len > -2) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                                
                    case 7:     l = -1; //downleft
                                k = 1;
                                if(col-len > -2 && row+len < puzzleLen+1) {
                                    flag = false;
                                    break;
                                }
                                direction++;
                                
                    default:    row = (int) (Math.random()*puzzleLen); 
                                col = (int) (Math.random()*puzzleLen);
                                direction = (int)(Math.random()*8);
                                flag = true;
                                break;
                    
                }
                for(q=0; q<word.length() && flag==false; q++) {
                   if (puzzle[row][col] != ('\u0000') && puzzle[row][col] != word.charAt(q)) {
                       flag = true;
                       row-=(k*q);
                       col-=(l*q);
                   }
                   row +=k;
                   col +=l;
                }
            }
            row = row - (q*k);
            col = col - (q*l);
            dirArrows = dirArrows+ARROWS.charAt(direction);
            posTable[puzzleWords.indexOf(word)][0] = row;
            posTable[puzzleWords.indexOf(word)][1] = col;
            for(q=0;q<word.length();q++) {
                puzzle[row][col]= word.charAt(q);
                row+=k;
                col+=l;
            }
            k = 0;
            l = 0;
        }
        for(int i=0; i<puzzle.length; i++){
            for(int j=0; j<puzzle[0].length; j++) {
                if(puzzle[i][j] == '\u0000') {
                    puzzle[i][j] = alphabet.charAt((int)(Math.random()*alphabet.length()));
                }
            }
        }
    }
    public void showWordSearchPuzzle(boolean hide) {
        System.out.println(getPuzzleAsString());
        if(hide) {
            for(String word : puzzleWords) {
                System.out.println(word);
            }
        } else {
            for(int j=0; j < puzzleWords.size(); j++) {
                System.out.println(puzzleWords.get(j) +" ["+ Integer.toString(posTable[j][0])+","+Integer.toString(posTable[j][1])+"] "+dirArrows.charAt(j));
            }
        }
    }
}


