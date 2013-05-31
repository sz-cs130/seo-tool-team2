package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInTitle extends Metric {
	public boolean[] results;
	WebPage[] pages;
	
	public KeywordInTitle() {
		results = new boolean[4];
	}
	
	public void run(WebPage[] webpages) {
	     int i;
	     for(i = 0; i < 3+1; i++)
	     {
	       pages = webpages;
	       String title = "four";
	       // find the title
	       Pattern pat = Pattern.compile(".*<head>.*<title>(.*)</title>.*</head>.*", Pattern.CASE_INSENSITIVE);
	       Matcher mat = pat.matcher(webpages[i].get_content()); // create the matcher object
	       if(mat.find()) {
	    	   // find the keyword in the title
	    	   title = mat.group(1);
	    	   pat = Pattern.compile(webpages[i].get_keyword(), Pattern.CASE_INSENSITIVE);
	    	   mat = pat.matcher(title);
	    	   if(mat.find())
	    	   {
	    		   results[i] = true;
	    	   } else {
	    		   results[i] = false;
	    	   }
	       } else {
	    	   results[i] = false;
	       } 
	     }
	}
	
	public String returnResults() {
		String output = "<li><h3>Ketword in Title</h3>";
		int i;
		for(i = 0; i < 4; i++) {
			if(results[i]) {
				output += "result #" + i + " (" + pages[i].get_url() +") keyword is PRESENT.<br>";
			} else {
				output += "result #" + i + " (" + pages[i].get_url() +") keyword is MISSING.<br>";
			}
		}
		output += "</li>";
		return output;
	}
	
}