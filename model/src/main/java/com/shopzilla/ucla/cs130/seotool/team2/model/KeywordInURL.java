package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInURL extends Metric {
	public boolean[] results;
	WebPage[] pages;
	
	public KeywordInURL() {
		results = new boolean[4];
	}
	
	public void run(WebPage[] webpages) {
	     int i;
	     for(i = 0; i < 3+1; i++)
	     {
	       pages = webpages;
	       Pattern pat = Pattern.compile(webpages[i].get_keyword(), Pattern.CASE_INSENSITIVE);
	       Matcher mat = pat.matcher(webpages[i].get_url()); // create the matcher object
	       if(mat.find()) {
	    	   results[i] = true;
	       } else {
	    	   results[i] = false;
	       }
	     } 
	}
	
	public String returnResults() {
		String output ="<li><h3>Keyword in URL</h3>";
		output += "<table border=\"1\"><tr><th>Site</th><th>Keyword present in URL?</th></tr>";

		int i;
		for(i = 0; i < 4; i++) 
		{
			output += "<tr><td style=\"text-align:left;\">" + pages[i].get_url() + "</td>";
			if (results[i])
			{
				output += "<td>Yes</td>";
			}
			else
			{
				output += "<td>No</td>";
			}
		}
		output += "</table></li>";
		return output;
	}
	
}
