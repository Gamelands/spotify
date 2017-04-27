package util;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class spotifyUtils {
	
	public List<String> PhraseFinder(String lyrics) throws ParseException
	{
    	String[] tagWords = new String[] {"CC","IN", "NN", "VB"}; //common conjunction or prepositions
    	Set<String> tags = new HashSet<String>();
    	for (int i=0; i < tagWords.length;i++)
    	{
    		tags.add(tagWords[i]);
    	}
    	
  
    	
    	MaxentTagger tagger = new MaxentTagger("C:/Users/xilin/Google Drive/SQUANT/INFO/CODE/JARFILES/javaJars/stanford-postagger-full-2016-10-31/models/english-left3words-distsim.tagger");

    	String tagged = tagger.tagString(lyrics);
    	String[] splitArray = tagged.split("\\s+");
    	String[] lyricsArr = lyrics.split("\\s+");
    	
    	//find positions of start words per common POS tags for starting words
    	Set<Integer> pos = new TreeSet<>();
    	pos.add(0);
    	pos.add(splitArray.length-1);
    	for (int i=0;i<splitArray.length;i++)
    	{
    		String temp = splitArray[i];
    		String[] tempPos = temp.split("_");
    		boolean present = tags.contains(tempPos[1]);
    		if (present == true)
    		{
    			pos.add(i);
    		}
    	}

    	Iterator<Integer> iterator = pos.iterator(); //"if i can't let it go out of my mind fuck jerk"
    	List<String> finalStr = new LinkedList<String>();
		int beg = 0;
		int end = iterator.next();
		String tempStr = null;
		while(iterator.hasNext())
		{
			while(end-beg < 3)
			{
				end++;
			}
			if(end < lyrics.length())
			{
				try
				{
					List<String> subArray = new LinkedList<String>(Arrays.asList(Arrays.copyOfRange(lyricsArr, beg, end)));
		
					int flag = 0;
					while(subArray.indexOf(null) != -1)
					{
						subArray.remove(null);  
						flag = 1;
					}
					if (flag != 0)
					{
						finalStr.set(finalStr.size()-1, String.join(" ",finalStr.get(finalStr.size()-1)) + ' ' + String.join(" ",subArray));
					}
					else if (flag == 0)
					{
						tempStr = String.join(" ",subArray);
						finalStr.add(tempStr);
					}
					beg = end;
				}
				catch( Exception e )
				{
					break;
				}
			}
		}
		
		
		return finalStr;
	}
	
	public void PlayList(List<String> finalStr) throws ParseException, IOException
	{
		
    	//finalStr = searcher.PhraseFinder(lyrics);
    	
		//spotify api search request
		System.out.println("Your Play List is Below:");
		List<String> PlayList = new LinkedList<String>();
		for(int i=0;i<finalStr.size();i++)
		{
			String temp = finalStr.get(i);
			String[] splitTemp = temp.split("\\s+");
			String query = String.join("+",splitTemp);
			String theURL = "https://api.spotify.com/v1/search?q=" + query + "&type=track&limit=1";
			URL wikiRequest = new URL(theURL);
			 
			Scanner scannerUrl = new Scanner(wikiRequest.openStream());
			String response = scannerUrl.useDelimiter("\\Z").next();

			Gson gson = new GsonBuilder().create();
	
			JsonObject job = gson.fromJson(response, JsonObject.class);
			JsonElement entry=job.get("tracks");
			job = gson.fromJson(entry, JsonObject.class);
			entry=job.get("items");
			
			JsonArray jobArray = gson.fromJson(entry, JsonArray.class);
			try
			{
				entry = jobArray.get(0);
		
				job = gson.fromJson(entry, JsonObject.class);
				entry = job.get("external_urls");
				
				job = gson.fromJson(entry, JsonObject.class);
				entry = job.get("spotify");
				
				temp = entry.toString();
				temp = temp.replaceAll("^\"|\"$", "");
				
				PlayList.add(temp);
				
				System.out.println(temp);
			}
			catch( Exception e )
			{
				System.out.println("Sorry but the following lyrics dont exist: " + query);
			}
		}
			//return sentiment;
       }
     
}

