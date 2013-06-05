package com.shopzilla.ucla.cs130.seotool.team2.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInPage extends Metric {
	WebPage[] pages;
	public int[] results;
	
	
	public void run(WebPage[] webpages) {
	     int i;
	     results = new int[webpages.length];
	     pages = webpages;
	     for(i = 0; i < webpages.length; i++)
	     {
	       //pages = webpages;
	       String pattern = webpages[i].get_keyword(); // the pattern is the keyword
	       Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE); // create the pattern object
	       Matcher mat = pat.matcher(webpages[i].get_content()); // create the matcher object
	       int count = 0; // keyword count
	       int count2 = 0;
	       while(mat.find())
	       {
	         count++;
	       }
	       // get the count of the keywords
	       
	       for(int j = 0; j<webpages[i].get_keytokens().length; j++){
	          String pattern2 = webpages[i].get_keytokens()[j];
	          Pattern pat2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
	          Matcher mat2 = pat2.matcher(webpages[i].get_content());
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
}
