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
	     
	     for(i = 0; i < pages.length; i++)
	     {
	       
	       // Placeholder, if everything's 4s then something went wrong
	       String title = "fore";
	       // find the title
	      
	       Pattern pat = Pattern.compile("<title>(.*)</title>", Pattern.CASE_INSENSITIVE);
	       Matcher mat = pat.matcher(pages[i].get_content()); // create the matcher object
	       
	       
	       if(mat.find()) {
	    	   // find the keyword in the title
	    	   title = mat.group(1);
	    	  
	    	   pat = Pattern.compile(pages[i].get_keyword(), Pattern.CASE_INSENSITIVE);
	    	   mat = pat.matcher(title);
	    	   if(mat.find())
	    	   {
	    		   results[i] = true;
	    		  
	    		   
	    	   } else {
	    	      
	    	      results[i] = false;
	    	      for(int j = 0; j<pages[i].get_keytokens().length; j++){
	                String pattern2 = pages[i].get_keytokens()[j];
	                
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
             
            results[i] = false;
          } 
	       
        }
	    
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
