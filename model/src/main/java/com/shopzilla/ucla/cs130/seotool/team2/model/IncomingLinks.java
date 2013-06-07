package com.shopzilla.ucla.cs130.seotool.team2.model;

import com.shopzilla.ucla.cs130.seotool.team2.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncomingLinks extends Metric implements Runnable
{
  WebPage[] pages;
  public String[] aResults;
  public String[] bResults;
  public float[] estimatedIncomingLinks;
  
  public String[] aContents;
  public String[] bContents;
  
  private String[] alexaUrls;
  private String[] bingQueries;
  
  Thread[] t;


  public void run(WebPage[] webpages)
  {
    pages = webpages;
    aResults = new String[webpages.length];
    bResults = new String[webpages.length];
    
    aContents = new String[webpages.length]; //contents of alexa crawl
    bContents = new String[webpages.length]; //contents of bing crawl
    estimatedIncomingLinks = new float[webpages.length];
    
    
    //need to execute javascript to be able to crawl google.com itself
    //google custom search isn't accurate enough to estimate incoming links (also too many API requests from IP)
    
    //estimation based on bing and alexa
    
    //build url array
    

    
    alexaUrls = new String[webpages.length];
    bingQueries = new String[webpages.length];
    
    try{
    	for(int i = 0; i < webpages.length; i++)
    	{
    	
    		URI u = new URI( webpages[i].get_url() );
    	
    		String temp;
    		temp = u.getHost();
    	
    		if( temp.startsWith("www.") ) {
    			temp = temp.substring(4);
    		}
    	
    	
    		alexaUrls[i] = "http://www.alexa.com/site/linksin/" + temp;
    		bingQueries[i] = "http://www.bing.com/search?q=\""+ webpages[i].get_url() + "\"";
    	
    	}
    } catch (URISyntaxException e) {
		System.err.println("Problem building URI for incoming links metric");
		e.printStackTrace();
	}
    
    //crawl the alexa site and bing site using formed urls/queries
    
    //spawn threads to do the work
    
    t = new Thread[webpages.length];
    
    for(int i = 0; i < webpages.length; i++)
    {
    	t[i] = new Thread(this, Integer.toString(i) );
    	t[i].start();
    }
    
    //join the threads that did all the work
    
    for(int i = 0; i < webpages.length; i++) {
    		
    	try {
    		t[i].join();
    	} catch (InterruptedException e) {
    		/*
    		System.err.println("Problem joining threads: incoming links metric");
    		e.printStackTrace();
    		*/
    	}
	  }
    
    
    //System.out.println(bContents[0]);
    
    for(int i = 0; i < webpages.length; i++)
    {
      
      // create the pattern objects
      Pattern aPattern = Pattern.compile("<span class=\"font-5 float-right\">[0-9,]*</span>"); 
      Pattern bPattern = Pattern.compile("<span class=\"sb_count\" id=\"count\">[0-9,]* results</span>");
      Matcher aMatcher = null;
      Matcher bMatcher = null;
      if(aContents[i] != null)
         aMatcher = aPattern.matcher(aContents[i]); // create the matcher object
      
      if(bContents[i] != null)
         bMatcher = bPattern.matcher(bContents[i]); // create the matcher object
      
      
      if(aMatcher.find())
    	  aResults[i] = aMatcher.group();
    	  //System.out.println(aMatcher.group());
      
      if(bMatcher.find())
    	  bResults[i] = bMatcher.group();
    	  //System.out.println(bMatcher.group());
      
      // more string processing
      
      aResults[i] = aResults[i].replace("<span class=\"font-5 float-right\">", "");
      aResults[i] = aResults[i].replace("</span>", "");
      aResults[i] = aResults[i].replace(",", "");
      
      bResults[i] = bResults[i].replace("<span class=\"sb_count\" id=\"count\">", "");
      bResults[i] = bResults[i].replace(" results</span>", "");
      bResults[i] = bResults[i].replace(",", "");
      
      System.out.println(aResults[i]);
      System.out.println(bResults[i]);
      
      float value1 = Float.parseFloat(aResults[i]);
      float value2 = Float.parseFloat(bResults[i]);
      
      //magical formula for estimating the number of incoming links
      // num = (num from alexa + 2* num from bing) / 3
      // this weights the bing results twice as valuably as the alexa results
      // due to some problems with alexa not recognizing all domains
      // this formula can be adjusted as seen fit
      estimatedIncomingLinks[i] = (9*value1 + value2) / 10;
      
      
    }
  }

  public String returnResults()
  {
	
    String output = "<li><h3>Incoming Links</h3>";
    output += "<table border=\"1\"><tr><th>Site</th><th>Estimated Number of Incoming Links</th></tr>";
    int i;
    for(i = 0; i < pages.length; i++)
    {
       String temp = Float.toString(estimatedIncomingLinks[i]);
       output += "<tr><td style=\"text-align:left;\">" + pages[i].get_url() + "</td>";
       output += "<td>" + temp + "</td>";
    }

    output += "</table></li>";
    return output;
    
  } 
  
  public String returnRecommendations() {
	  
	
	  String output ="<li><h3>Estimated Number of Incoming Links</h3>";
	  output += "<p>No recommendations for this metric.</p>";
	  output += "</li>";
	  return output;

	}


  public void run() {
	
	  try {
		    
          int i = Integer.parseInt( Thread.currentThread().getName() );
          URL url;
          HttpURLConnection conn;
          BufferedReader rd;
          String content = "";
          String temp;
          
          
           
          //setup connection for alexa query
          url = new URL( alexaUrls[i] );
          conn = (HttpURLConnection)url.openConnection();
          conn.setRequestMethod("GET");
          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
          content = "";
            
            //read the contents of the page
          while( (temp = rd.readLine()) != null) {
             content += temp;
          }
            
          //close buffered reader
          rd.close();
            
          //fill out contents of alexa query
          aContents[i] = content;
          
       
          
          
          //setup connection for bing query
          url = new URL( bingQueries[i] );
          conn = (HttpURLConnection)url.openConnection();
          conn.setRequestMethod("GET");
          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
          content = "";
            
            //read the contents of the page
          while( (temp = rd.readLine()) != null) {
             content += temp;
          }
            
          //close buffered reader
          rd.close();
            
          //fill out contents of bing query
          bContents[i] = content;
          
        
       } catch (Exception e) { //refine the possible error messages
         System.err.println("Error during webpage crawling : Incoming Links Metric");
         e.printStackTrace();
       }
	
  }
  public String get_name(){return "IncomingLinks";}
}
