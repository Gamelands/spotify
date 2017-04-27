/**
 * Patrick Houlihan Candidate exercise for data
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import util.spotifyUtils;

public class myTop {
//phrase and clause
    public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParseException {
    	
    	
		spotifyUtils searcher = new spotifyUtils();
    	String lyrics = searcher.Sniffer();
    	searcher.PlayList(searcher.PhraseFinder(lyrics));
    }
}

