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
  public BriefDescription()
  {
    results = new String[4];
  }

  public void run(Webpage[] webpages)
  {
    int i;
    pages = webpages;
    for(i = 0; i < 3+1; i++)
    {
      String start_pattern = "<meta name=\"description\" content=\""; // the pattern is the description tag
      Pattern start_pat = Pattern.compile(start_pattern); // create the pattern object
      Matcher start_mat = start_pat.matcher(webpages[i].get_content()); // create the matcher object

      String end_pattern = "\">"; // the description tag ends with this
      Pattern end_pat = Pattern.compile(end_pattern); // create the end pattern object
      Matcher end_mat = end_pat.matcher(webpages[i].get_content()); // create the end matcher object

      int start, end; // the start and end index of the description
      start = start_mat.end(); // the description starts right after the content="
      end = end_mat.start() - 1; // the description ends right before the ">

      int length = end - start; // the length of the description

      if(15 >= length) // if length of description is less than or equal to 15
        results[i] = "is"; // the description is brief
      else
        results[i] = "is not"; // the description is not brief
    }
  }

  public String returnResults()
  {
    String output = "<ul><li><h3>DescriptionBriefness</h3>";
    int i;
    for(i = 0; i < 4; i++)
    {
      output += "The description in result #" + i + " " + results[i] + " brief (" + pages[i].get_url() +")<br>";
    }

    output += "</li></ul>";
    return output;
  } 
}
