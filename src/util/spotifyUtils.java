package util;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class spotifyUtils {
	
	public String Sniffer() throws ParseException
	{	
      	Scanner scanner = new Scanner(System.in);
    	System.out.print("Enter some lyrics: ");
    	String lyrics = scanner.nextLine();
    	//String lyrics = "if i can't let it go out of my mind";
    	lyrics = lyrics.replaceAll("[^A-Za-z0-9 ]", "").toLowerCase();
    	scanner.close();
    	
    	if (lyrics.length() == 0)
    	{
    		System.out.println("Please enter in valid lyrics - TERMINATING PROGRAM");
			System.exit(0);
		}
		return lyrics;
	}
	
	public List<String> PhraseFinder(String lyrics) throws ParseException
	{
		List<String> tags = Arrays.asList("CC","IN","NN","VB"); //common conjunction or prepositions 
    	Set<String> hashtags = new HashSet<String>();
    	
    	for (int i=0; i < tags.size();i++)
    	{
    		hashtags.add(tags.get(i));
    	}
    	
    	MaxentTagger tagger = new MaxentTagger("C:/Users/xilin/Google Drive/SQUANT/INFO/CODE/JARFILES/javaJars/stanford-postagger-full-2016-10-31/models/english-left3words-distsim.tagger");
    	String tagged = tagger.tagString(lyrics);
    	List<String> posTemp = Arrays.asList(tagged.split("\\s+"));
    	ArrayList<String> pos = new ArrayList<String>();
    	List<String> lyricsArr = Arrays.asList(lyrics.split("\\s+"));
    	
    	for (int i=0;i<posTemp.size();i++)
    	{
    		String temp = posTemp.get(i);
    		String[] tempPos = temp.split("_");
    		pos.add(tempPos[1]);
    	}
    	 
    	PeekingIterator<String> iterator = Iterators.peekingIterator(pos.iterator());
    	List<String> finalStr = new LinkedList<String>();
    	List<String> tempS = new LinkedList<String>();
    	int i = 0;
		if(lyricsArr.size() > 3)
		{
			while(iterator.hasNext())
			{
				if(tempS.size() >= 3)
				{;
					if(tags.contains(iterator.peek()) == true)
					{
						finalStr.add(String.join(" ",tempS));
						tempS.removeAll(tempS);
					}
				}
				tempS.add(lyricsArr.get(i));
				iterator.next();
				i++;
			}
			if (tempS.size() != 0)
			{
				String backFill = String.join(" ",finalStr.get(finalStr.size()-1)) + " ";
				backFill = backFill.concat(String.join(" ",tempS));
				finalStr.set(finalStr.size()-1, backFill);
			}
		}
		else
		{
			finalStr.add(lyrics);
		}
		
		//validate lyrics added in full
		/*
		for(int j=0;j<finalStr.size();j++)
		{
			System.out.println(finalStr.get(j));
		}
		*/
		return finalStr;
	}
	
	public void PlayList(List<String> finalStr) throws ParseException, IOException
	{	
		//spotify api search request
		//https://developer.spotify.com/web-api/console/get-search-item/
		System.out.println("Your Play List is Below:");
		List<String> PlayList = new LinkedList<String>();
		for(int i=0;i<finalStr.size();i++)
		{
			String temp = finalStr.get(i);
			String[] splitTemp = temp.split("\\s+");
			String query = String.join("+",splitTemp);
			String theURL = "https://api.spotify.com/v1/search?q=%22" + query + "%22&type=track&limit=1";
			URL wikiRequest = new URL(theURL);
			//System.out.println(theURL);//validate URL
			@SuppressWarnings("resource")
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
	}
}