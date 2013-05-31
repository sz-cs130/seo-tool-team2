package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleIsBrief extends Metric {
	public int[] results;
	WebPage[] pages;
	
	public TitleIsBrief() {
		results = new int[4];
	}
	
	public void run(WebPage[] webpages) {
	     int i;
	     for(i = 0; i < 3+1; i++)
	     {
	       pages = webpages;
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
		String output ="<li><h3>Title Is Brief</h3>";
		int i;
		for(i = 0; i < 4; i++) {
			output += "result #" + i + " (" + pages[i].get_url() +") had title length " + results[i] + "<br>";
		}
		output += "</li>";
		return output;
	}
	
}