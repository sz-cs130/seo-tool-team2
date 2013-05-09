package com.shopzilla.ucla.cs130.seotool.team2.service;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;
//import com.google.api.services.customsearch.*;
import java.lang.String;
import java.net.*;
import java.io.*;
import org.json.JSONException;
import org.json.JSONObject;

public class WebService {
   private static final String key = "AIzaSyB8JAz0MHfwz7s5e5Nv8jf-Ku_WlZbrpPM";
   // method for them to call
   public static WebPage[] service(WebPage input){
      //Jonathan's code here
      
      String query = input.get_keyword();
      String line;
      StringBuilder build = new StringBuilder();
      
      try{
         // form the url for the google query
         URL url = new URL("https://www.google.apis.com/customsearch/v1?key="
            + key +"&cx=013036536707430787589:_pqjad5hr1a$q="
            + query+ "&alt=json");
         // create the connection
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Accept",  "applicaiton/json");
         // grab the reply
         BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
         while((line = br.readLine()) != null){
            build.append(line);
         }
         // close the reader
         br.close();
         // create the JSONObject
         JSONObject results = new JSONObject(build.toString());
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
      return null;
   }

}
