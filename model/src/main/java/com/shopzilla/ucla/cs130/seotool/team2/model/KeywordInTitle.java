package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInTitle extends Metric {
	public boolean[] results;
	WebPage[] pages;
	Thread t;
	
	 public KeywordInTitle(WebPage[] webpages){
	      pages = webpages;
	      results = new boolean[webpages.length];
	      t = new Thread(this, "TitleMatchContent");
	      t.start();
	   }
	
	public void run() {
	     int i;
	     System.out.println("Will go through this loop: " + Integer.toString(pages.length));
	     for(i = 0; i < pages.length; i++)
	     {
	       System.out.println("Checkpoint beginning of iteration");
	       // Placeholder, if everything's 4s then something went wrong
	       String title = "fore";
	       // find the title
	       //Pattern pat = Pattern.compile(".*<head>.*<title>(.*)</title>.*</head>.*", Pattern.CASE_INSENSITIVE);
	       Pattern pat = Pattern.compile("<title>(.*)</title>", Pattern.CASE_INSENSITIVE);
	       Matcher mat = pat.matcher(pages[i].get_content()); // create the matcher object
	       
	       System.out.println("Created the matcher for the title");
	       if(mat.find()) {
	    	   // find the keyword in the title
	    	   title = mat.group(1);
	    	  System.out.println("found the title: " +title);
	    	   pat = Pattern.compile(pages[i].get_keyword(), Pattern.CASE_INSENSITIVE);
	    	   mat = pat.matcher(title);
	    	   if(mat.find())
	    	   {
	    		   results[i] = true;
	    		  System.out.println("Found keyword in title.");
	    		   
	    	   } else {
	    	      System.out.println("Using tokens to search the title: " + title);
	    	      results[i] = false;
	    	      for(int j = 0; j<pages[i].get_keytokens().length; j++){
	                String pattern2 = pages[i].get_keytokens()[j];
	                //System.out.println(pattern2);
	                Pattern pat2 = Pattern.compile(pattern2, Pattern.CASE_INSENSITIVE);
	                Matcher mat2 = pat2.matcher(title);
	                // get count of each token word
	                if(mat2.find()){
	                   results[i] = true;
	                   break;
	                }
	            }
	    	   }
          } else {
             System.out.println("Did not find the title");
            results[i] = false;
          } 
	       System.out.println("Checkpoint: end of iteration");
        }
	     System.out.println("Done with KeywordInTitle");
	}
	
	public String returnResults() {
		String output ="<li><h3>Keyword in Title</h3>";
		output += "<table border=\"1\"><tr><th>Site</th><th>Keyword present in title?</th></tr>";

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
	
	public String returnRecommendations() {
		String output = "<li><h3>Keyword in Title</h3>";
		if(!results[0]) {
			output += "<p><b>The target keyword doesn't appear in the page title.  Try adding it there.</b></p>";
		} else {
			output += "<p>No recommendations for this metric.</p>";
		}
		output += "</li>";
		
		return output;
	}
	 public Thread get_thread() {return t;}
	 
	 public String get_name(){return "KeywordInTitle";}
}
