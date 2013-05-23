package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInPage extends Metric {
	WebPage[] pages;
	public int[] results;
	
	public KeywordInPage() {
		results = new int[4];
	}
	
	public void run(WebPage[] webpages) {
	     int i;
	     for(i = 0; i < 3+1; i++)
	     {
	       pages = webpages;
	       String pattern = webpages[i].get_keyword(); // the pattern is the keyword
	       Pattern pat = Pattern.compile(pattern); // create the pattern object
	       Matcher mat = pat.matcher(webpages[i].get_content()); // create the matcher object
	       int count = 0; // keyword count
	       while(mat.find())
	       {
	         count++;
	       }
	       results[i] = count;
	     }
	}
	
	public String returnResults() {
		String output ="<ul><li><h3>KeywordFrequency</h3>";
		int i;
		for(i = 0; i < 4; i++) {
			output += results[i] + " occurances in result #" + i + " (" + pages[i].get_url() +")<br>";
		}
		output += "</li></ul>";
		return output;
	}
}