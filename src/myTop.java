/**
 * Patrick Houlihan Candidate exercise for data
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import util.spotifyUtils;

public class myTop {
//phrase and clause
    public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParseException {
    	
    	spotifyUtils searcher = new spotifyUtils();
    	
      	Scanner scanner = new Scanner(System.in);
    	System.out.print("Enter some lyrics: ");
    	String lyrics = scanner.nextLine();
    	//String lyrics = "if i can't let it go out of my mind";
    	lyrics = lyrics.replaceAll("[^A-Za-z0-9 ]", "");
    	scanner.close();
    	
    	List<String> finalStr = new LinkedList<String>();
    	
    	finalStr = searcher.PhraseFinder(lyrics);
    	searcher.PlayList(finalStr);
    }
}

