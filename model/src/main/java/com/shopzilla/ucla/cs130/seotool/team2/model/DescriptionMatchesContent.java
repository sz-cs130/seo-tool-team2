package com.shopzilla.ucla.cs130.seotool.team2.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescriptionMatchesContent extends Metric{
   
   private WebPage[] pages;
   private int[] results;
   Thread t;
   
   public DescriptionMatchesContent(WebPage[] webpages){
      pages = webpages;
      results = new int[webpages.length];
      t = new Thread(this, "DescriptionMatchesContent");
      t.start();
   }
   
   // for only checking words
   private boolean isAlpha(String word){
      return word.matches("[a-zA-Z]+");
   }
   
   public void run(){
      
      for(int i = 0; i < pages.length; i++)
      {
        int start = 0; 
        int end = 0; // the start and end index of the description
        String start_pattern = "<meta name=\"description\" content=\"";
        Pattern start_pat = Pattern.compile(start_pattern); // create the pattern object
        Matcher start_mat = start_pat.matcher(pages[i].get_content()); // create the matcher object
        
        if(start_mat.find())
           start = start_mat.end(); // the description starts right after the content="

        String end_pattern = "\"";  // the description tag ends with this
        Pattern end_pat = Pattern.compile(end_pattern); // create the end pattern object
        Matcher end_mat = end_pat.matcher(pages[i].get_content()); // create the end matcher object

        if(end_mat.find(start))
           end = end_mat.start() - 1; // the description ends right before the ">
        
        String title = pages[i].get_content().substring(start, end);
        String delims = "\\s";
        String[] tokens = title.split(delims);

        results[i] = 0;
        int temp = 0;
        if(0 == start)
         results[i] = -1;
        else{
           for(int j=0; j<tokens.length; j++){
              
              //System.out.println("This word was split into: " + tokens[j]);
              if(!(isAlpha(tokens[j])))
                 continue;
              
              Pattern token = Pattern.compile(tokens[j]); // create the pattern object
              Matcher token_mat = token.matcher(pages[i].get_content()); // create the matcher object
              
              if(token_mat.find(end)){
                 temp++;
              }
              else{
                 temp--;
              }
           }
           if (temp > 0)
              results[i] = 1;
           else
              results[i] = 0;
        }
      }
   }
   
   public String returnResults() {
      String output ="<li><h3>Description Relevance</h3>";
      output += "<table border=\"1\"><tr><th>Site</th><th>Description Relevant?</th></tr>";

      int i;
      for(i = 0; i < pages.length; i++)
      {
         output += "<tr><td style=\"text-align:left;\">" + pages[i].get_url() + "</td>";
         switch(results[i]){
            case 0: output += "<td>No</td>";
                    break;
            case -1: output += "<td>N/A</td>";
                     break;
            default: output += "<td>Yes</td>";
         }
      }
      output += "</table></li>";
      return output;
   }
   
   public String returnRecommendations() {
      String output ="<li><h3>Description Relevance</h3>";
      
      if(0 == results[0]) {
         output += "<p><b>Make the page description more relevant to the page content.</b></p>";
      } else if (-1 == results[0]){
         output += "<p><b>Add a description to the page.</b></p>";
      }else{
         output += "<p>No recommendations for this metric.</p>";
      }
      output += "</li>";
      return output;
   }
   
   public Thread get_thread() {return t;}
   public String get_name(){return "DescriptionMatchesContent";}
}
