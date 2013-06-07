package com.shopzilla.ucla.cs130.seotool.team2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
         JSONObject results = new JSONObject(build.toString());
         
         // get the JSON element items
         JSONObject nouns = results.getJSONObject("noun");
         JSONArray syns = results.getJSONArray("syn");
         int size = syns.length();
         
         
         for(int i = 0; i < size; i++){
            // grab each element in the array
            String related = syns.getString(i);
           System.out.println(related);
            
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
   
   public Thread get_thread() {return t;}
   public String get_name(){return "RelatedWords";}
}
