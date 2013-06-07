package com.shopzilla.ucla.cs130.seotool.team2.service;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;

import java.lang.String;
import java.net.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class WebService implements Runnable{
   private static final int numResults = 3;
   private static final String key = "AIzaSyB8JAz0MHfwz7s5e5Nv8jf-Ku_WlZbrpPM";
   // API key backup
   private static final String key2 = "AIzaSyA5wNH5LlOiSvUHxZHku7PLIw1pV7v5-yc";
   //private static final String bizSearchID = "013100502047583691894:1dyk11jghmi";
   private static final String liveSearchID = "013036536707430787589:_pqjad5hr1a";
   //private static final String shopzillaSearchID = "013100502047583691894:9ncazeorv5y";
   private static WebPage[] pages;
   private Thread t;
   
   WebService(int i){
      t = new Thread(this, Integer.toString(i));
      t.start();
   }
   
   public Thread get_thread() {return t;}
   
   // method for them to call
   public static WebPage[] service(String query, String targetsite, String targeturl){
      //Jonathan's code here
      /*
      System.out.println(query);
      System.out.println(targetsite);
      System.out.println(targeturl);
      */
      //query.replaceAll("\\s", "+");
      String delims = "\\s";
      String[] tokens = query.split(delims);
      String urlquery = "";
      for(int i = 0; i < tokens.length; i++){
         urlquery += tokens[i];
         if((i+1) < tokens.length)
            urlquery += "+";
      }
      //System.out.println(urlquery);
      
      pages = new WebPage[numResults + 1];
      for(int i = 0; i < numResults + 1; i++){
         pages[i] = new WebPage();
         pages[i].set_keyword(query);
         pages[i].set_keytokens(tokens);
      }
      // boolean for if the target url has been set yet
      boolean done;
      if(targeturl == "optional" || targeturl == null || targeturl== ""){
         done = false;
      }
      else{
         if(!targeturl.contains(".com") && 
               !targeturl.contains(".org")&& 
               !targeturl.contains(".net")&& 
               !targeturl.contains(".gov")){
            done = false;
         }
         else{
            if(!(targeturl.contains("www."))){
               targeturl = "www." + targeturl;
            }
            if(!(targeturl.contains("http://")))
               targeturl = "http://" + targeturl;
            pages[0].set_url(targeturl);
            done = true;
         }
      }
     
      try{
         String line;
         StringBuilder build = new StringBuilder();
         // form the url for the google query
         URL url = new URL("https://www.googleapis.com/customsearch/v1?key="
            + key2 +"&cx=" + liveSearchID + "&q="
            + urlquery+ "&alt=json");
         
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
         JSONArray items = results.getJSONArray("items");
         int size = items.length();
         
         // array to store the links
         int j = 1;
         JSONObject temp;
         
         for(int i = 0; i < size; i++){
            // grab each element in the array
            temp = items.getJSONObject(i);
            String tempurl = temp.getString("link");
            
            // check if the link belongs to amazon or ebay
            if (tempurl.contains("amazon") || 
                tempurl.contains("ebay") ||
                tempurl.contains("wikipedia")){
               continue;
            }
            else if(tempurl.contains(targetsite)){
               pages[0].set_url(tempurl);
               pages[0].set_rank(i+1);
               done = true;
               continue;
            }
            if (j > numResults){
               continue;
            }
            // grab the link and store into the array of links
            pages[j].set_url(tempurl);
            pages[j].set_rank(i+1);
            // links size counter
            j++;
            
         }
         
         // ------now get the target link link------------
         if(!done){
       
            if(targetsite.contains("bizrate")){
               String l = "http://www.bizrate.com/classify?search_box=1&keyword=" + urlquery;
               pages[0].set_url(l);
            }
            else if(targetsite.contains("shopzilla")){
               String l = "http://www.shopzilla.com/search?seach_box=1&sfsk=0&cat_id=1&keyword=" + urlquery;
               pages[0].set_url(l);
            }
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
      
      
      
   //---------------------------------------------------------------------------------
      //Albert's code here
      /*
       * add handling of cases where <100% of the pages are GET-able
       * add handling of more than top 3 pages
       * 
       */
      //final long startTime = System.currentTimeMillis();
      try {
        
         WebService[] t = new WebService[pages.length-1];
    	   for(int i = 0; i < (pages.length-1); i++) {
    	      t[i] = new WebService(i+1);
    	   }
    	   URL url = new URL(pages[0].get_url());
    	   HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    	   conn.setRequestMethod("GET");
    	   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       
    	   String content = "";
    	   String temp;
       
    	   //read the contents of the page
    	   while( (temp = rd.readLine()) != null) {
    	      content += temp;
    	   }
       
    	   //close buffered reader
    	   rd.close();
       
    	   //fill our the WebPage object with content, keyword, and size
       
    	   pages[0].set_content(content);
    	   //pages[0].set_keyword(query);
    	   pages[0].set_size(content.length());
    	  
    	  
    	  // Synchronize Threads
    	  
    	  for(int i = 0; i < pages.length-1; i++) {
    	     t[i].get_thread().join();
    	  }
    	  
    	  
      } catch (Exception e) { //refine the possible error messages
    	  System.err.println("Error during webpage crawling");
    	  e.printStackTrace();
      }
      
      //final long endTime = System.currentTimeMillis();
      //System.out.println("Time taken: " + (endTime - startTime));
      return pages;
   }
   
   public void run() {
      // TODO Auto-generated method stub
      try {
    
          int j = Integer.parseInt(Thread.currentThread().getName());
            //setup connection
          URL url = new URL(pages[j].get_url());
          HttpURLConnection conn = (HttpURLConnection)url.openConnection();
          conn.setRequestMethod("GET");
          BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
          String content = "";
          String temp;
            
            //read the contents of the page
          while( (temp = rd.readLine()) != null) {
             content += temp;
          }
            
          //close buffered reader
          rd.close();
            
          //fill our the WebPage object with content, keyword, and size
          pages[j].set_content(content);
          //pages[j].set_keyword(query);
          pages[j].set_size(content.length());
          //add ranking, however need to clarify which ranking it is.
          //Thread.currentThread().join();
        
       } catch (Exception e) { //refine the possible error messages
         System.err.println("Error during webpage crawling");
         e.printStackTrace();
       }
   }
   

}
