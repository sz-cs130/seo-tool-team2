package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BriefDescription extends Metric
{
  WebPage[] pages;
  public String[] results;

  // constructor
 

  public void run(WebPage[] webpages)
  {
    int i;
    pages = webpages;
    results = new String[webpages.length];
    for(i = 0; i < webpages.length; i++)
    {
      int start = 0; 
      int end = 0; // the start and end index of the description
      String start_pattern = "<meta name=\"description\" content=\""; // the pattern is the description tag
      Pattern start_pat = Pattern.compile(start_pattern); // create the pattern object
      Matcher start_mat = start_pat.matcher(webpages[i].get_content()); // create the matcher object
      
      if(start_mat.find())
         start = start_mat.end(); // the description starts right after the content="

      String end_pattern = "\""; // the description tag ends with this
      Pattern end_pat = Pattern.compile(end_pattern); // create the end pattern object
      Matcher end_mat = end_pat.matcher(webpages[i].get_content()); // create the end matcher object

      if(end_mat.find(start))
         end = end_mat.start() - 1; // the description ends right before the ">

      int length = end - start; // the length of the description
      // above yields negative results when printed, see below method for count
      //String desc = webpages[i].get_content().substring(start, end+1);

      if(length == 0)
       results[i] = "N/A";
      else
        results[i] = Integer.toString(length); // the description is not brief
    }
  }

  public String returnResults()
  {
    String output = "<li><h3>Description Brevity</h3>";
    output += "<table border=\"1\"><tr><th>Site</th><th>Description Length (characters)</th></tr>";
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
			average += Integer.parseInt(results[i]);
		}
		average = average / (pages.length -1);
		
		String output ="<li><h3>Decription Brevity</h3>";
		if(average < Integer.parseInt(results[0]) - weight) {
			output += "<p><b>Top search results had shorter descriptions on average.  Try using a shorter page description.</b></p>";
		} else if(average > Integer.parseInt(results[0]) + weight) {
			output += "<p><b>Top search results had longer meta descriptions on average.  Try using a longer page description.</b></p>";
		} else {
			output += "<p>No recommendations for this metric.</p>";
		}
		output += "</li>";
		return output;
	}
}
