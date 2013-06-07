package com.shopzilla.ucla.cs130.seotool.team2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RelatedWords extends Metric{
   private WebPage[] pages;
   private int[] results;
   Thread t;
   
   public RelatedWords(WebPage[] webpages){
      pages = webpages;
      results = new int[webpages.length];
      t = new Thread(this, "RelatedWords");
      t.start();
   }
   
   public void run(){
      try{
         String line;
         StringBuilder build = new StringBuilder();
         // form the url for the google query
         URL url = new URL("http://words.bighugelabs.com/api/2/78f44aebb1e133af075e477893f5faea/"
               +pages[0].get_keyword()+ "/json");
         
         // create the connection
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Accept",  "application/json");
         
         // grab the reply
         BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
         while((line = br.readLine()) != null){
            build.append(line);
         }
         
         // close the reader
         br.close();
         
         // create the JSONObject
         JSONObject res = new JSONObject(build.toString());
         
         // get the JSON element noun
         JSONObject noun = res.getJSONObject("noun");
         
         // get the JSON element syn
         JSONArray syns = noun.getJSONArray("syn");
         int size = syns.length();
         
         // for each page
         for(int j = 0; j<pages.length; j++){
            int count = 0;
            
            // for each synonym
            for(int i = 0; i < size; i++){
            // grab each element in the array
               String related = syns.getString(i);
               Pattern pat = Pattern.compile(related, Pattern.CASE_INSENSITIVE); // create the pattern object
               Matcher mat = pat.matcher(pages[j].get_content()); // create the matcher object
               while(mat.find())
               {
                  count++;
               }
            }
            results[j] = count;
         }
         
         // works on local machine up to here
         
      } catch (IOException e){
         System.err.println("Error during REST invocation of API!");
         System.err.println("Exception Thrown: " + e.getMessage());
         e.printStackTrace();
      } catch (JSONException e){
         System.err.println("Error during JSON construction!");
         System.err.println("Exception Thrown: " +e.getMessage());
         e.printStackTrace();
      }
   }
   
   public String returnResults() {
      String output ="<li><h3>Related Words</h3>";
      output += "<table border=\"1\"><tr><th>Site</th><th>Related Word Count</th></tr>";

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
         average += results[i];
      }
      average = average / (pages.length -1);
         
      String output ="<li><h3>Related Word</h3>";
      if(average < results[0] - weight) {
         output += "<p><b>Top search results had fewer related words on average. Try varying the related words used.</b></p>";
      } else if(average > results[0] + weight) {
         output += "<p><b>Top search results had more related words on average. Try using more related words.</b></p>";
      } else {
         output += "<p>No recommendations for this metric.</p>";
      }
      output += "</li>";
      return output;
   }
   
   public Thread get_thread() {return t;}
   public String get_name(){return "RelatedWords";}
}
