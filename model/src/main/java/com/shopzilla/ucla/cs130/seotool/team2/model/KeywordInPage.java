package com.shopzilla.ucla.cs130.seotool.team2.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInPage extends Metric {
	WebPage[] pages;
	public int[] results;
	Thread t;
	
	 public KeywordInPage(WebPage[] webpages){
	      pages = webpages;
	      results = new int[webpages.length];
	      t = new Thread(this, "KeywordInPage");
	      t.start();
	   }
	
	
	public void run() {
	     int i;
	     
	     for(i = 0; i < pages.length; i++)
	     {
	       //pages = webpages;
	       String pattern = pages[i].get_keyword(); // the pattern is the keyword
	       Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE); // create the pattern object
	       Matcher mat = pat.matcher(pages[i].get_content()); // create the matcher object
	       int count = 0; // keyword count
	       int count2 = 0;
	       while(mat.find())
	       {
	         count++;
	       }
	       // get the count of the keywords
	       count2 = count;
	       for(int j = 0; j<pages[i].get_keytokens().length; j++){
	          String pattern2 = pages[i].get_keytokens()[j];
	          Pattern pat2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
	          Matcher mat2 = pat2.matcher(pages[i].get_content());
	          // get count of each token word
	          while(mat2.find())
	             count2++;
	          // subtract the number of times keyword was gotten to reduce duplicates
	          count2 -= count;
	       }
	       
	       results[i] = count2;
	     }
	}
	
	public String returnResults() {

		String output ="<li><h3>Keyword Frequency</h3>";
		output += "<table border=\"1\"><tr><th>Site</th><th>Number of Occurances</th></tr>";

		int i;
		for(i = 0; i < pages.length; i++) 
		{
			output += "<tr><td style=\"text-align:left;\">" + pages[i].get_url() + "</td>";
			output += "<td>" + results[i] + "</td>";
		}
		output += "</table></li>";
		return output;
	}
	
	public String returnRecommendations() {
		int average = 0;
		int weight = 10;
		
		for(int i = 1; i < pages.length; i++)
		{
			average += results[i];
		}
		average = average / (pages.length -1);
		
		String output ="<li><h3>Keyword Frequency</h3>";
		if(average < results[0] - weight) {
			output += "<p><b>Top search results had fewer appearances of the target keyword.  Try limiting keyword appearences to key areas of the site.</b></p>";
		} else if(average > results[0] + weight) {
			output += "<p><b>Top search results had more appearances of the target keyword.  Try increasing the keyword appearences throughout the page.</b></p>";
		} else {
			output += "<p>No recommendations for this metric.</p>";
		}
		output += "</li>";
		return output;
	}
	 public Thread get_thread() {return t;}
	 public String get_name(){return "KeywordInPage";}
}
