package com.shopzilla.ucla.cs130.seotool.team2.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInURL extends Metric {
	public boolean[] results;
	WebPage[] pages;
	
	
	public void run(WebPage[] webpages) {
	     int i;
	     pages = webpages;
	     results = new boolean[webpages.length];
	     for(i = 0; i < webpages.length; i++)
	     {
	       //pages = webpages;
	       Pattern pat = Pattern.compile(pages[i].get_keyword(), Pattern.CASE_INSENSITIVE);
	       Matcher mat = pat.matcher(pages[i].get_url()); // create the matcher object
	       if(mat.find()) {
	    	   results[i] = true;
	    	   continue;
	       } else {
	    	   results[i] = false;
	       }
	       for(int j = 0; j<pages[i].get_keytokens().length; j++){
             String pattern2 = pages[i].get_keytokens()[j];
             Pattern pat2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
             Matcher mat2 = pat2.matcher(pages[i].get_content());
             // get count of each token word
             if(mat2.find()){
                results[i] = true;
                break;
             }
          }
	     } 
	}
	
	public String returnResults() {
		String output ="<li><h3>Keyword in URL</h3>";
		output += "<table border=\"1\"><tr><th>Site</th><th>Keyword present in URL?</th></tr>";

		int i;
		for(i = 0; i < pages.length; i++) 
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
