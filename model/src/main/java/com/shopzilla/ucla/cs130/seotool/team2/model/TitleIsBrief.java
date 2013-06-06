package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleIsBrief extends Metric {
	public int[] results;
	WebPage[] pages;
	
	
	public void run(WebPage[] webpages) {
	     int i;
	     pages = webpages;
	     results = new int[webpages.length];
	     for(i = 0; i < webpages.length; i++)
	     {
	       String title = "four";
	       Pattern pat = Pattern.compile(".*<head>.*<title>(.*)</title>.*</head>.*");
	       Matcher mat = pat.matcher(webpages[i].get_content()); // create the matcher object
	       while(mat.find())
	       {
	    	   title = mat.group(1);
	       }
	       results[i] = title.length();
	     } 
	}
	
	public String returnResults() {
		String output ="<li><h3>Title Brevity</h3>";
		output += "<table border=\"1\"><tr><th>Site</th><th>Title Length (characters)</th></tr>";

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
		int weight = 5;
			
		for(int i = 1; i < pages.length; i++)
		{
			average += results[i];
		}
		average = average / (pages.length -1);
			
		String output ="<li><h3>Title Brevity</h3>";
		if(average < results[0] - weight) {
			output += "<p><b>Top search results had shorter titles on average.  Try using a shorter page title.</b></p>";
		} else if(average > results[0] + weight) {
			output += "<p><b>Top search results had longer titles on average.  Try using a longer page title.</b></p>";
		} else {
			output += "<p>No recommendations for this metric.</p>";
		}
		output += "</li>";
		return output;
	}
}
