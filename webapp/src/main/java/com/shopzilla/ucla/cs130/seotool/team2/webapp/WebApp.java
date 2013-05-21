package com.shopzilla.ucla.cs130.seotool.team2.webapp;

import static java.lang.System.out;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.shopzilla.ucla.cs130.seotool.team2.model.*;

public class WebApp {
   private static final int num_top_pages = 3; // only use top three pages
   public static void main(String args[])
   {
     String keyword = args[0]; // the keyword
     WebPage[] webpages = new WebPage[num_top_pages + 1];
     int i;
     for(i = 0; i < num_top_pages+1; i++)
     {
       webpages[i] = new WebPage();
       webpages[i].set_keyword(keyword);
     }
     //================================================
     // call webservice function to get the webpages:
     // webservice(webpages)
     //================================================
     /*TESTING*/ webpages[0].set_content("purple is purple hippos"); // for testing
     /*TESTING*/ webpages[1].set_content("purple purple more purple");
     /*TESTING*/ webpages[2].set_content("hello candy world");
     keyword_in_page(webpages);
     keyword_in_tags(webpages);
     keyword_in_url(webpages);
     /*TESTING*/ out.printf("Keys in Page0: %d\n", webpages[0].keys_in_page); // for testing
     /*TESTING*/ out.printf("Keys in Page1: %d\n", webpages[1].keys_in_page); // for testing
     /*TESTING*/ out.printf("Keys in Page2: %d\n", webpages[2].keys_in_page); // for testing

   }

   //========== Each metric has one function =================
   public static void keyword_in_page(WebPage[] webpages)
   {
     int i;
     for(i = 0; i < num_top_pages+1; i++)
     {
       String pattern = webpages[i].get_keyword(); // the pattern is the keyword
       Pattern pat = Pattern.compile(pattern); // create the pattern object
       Matcher mat = pat.matcher(webpages[i].get_content()); // create the matcher object
       int count = 0; // keyword count
       while(mat.find())
       {
         count++;
       }
       webpages[i].keys_in_page = count;
     }
   }
   public static void keyword_in_tags(WebPage[] webpages)
   {
     ;
   }
   public static void keyword_in_url(WebPage[] webpages)
   {
     ;
   }
   public static void keyword_in_title(WebPage[] webpages)
   {
     int i;
    boolean inTitle = false;
     for(i = 0; i < num_top_pages+1; i++)
     {
       String title = "<title>";
       Pattern titlePat = Pattern.compile(title);
       Matcher titleMat = titlePat.matcher(webpages[i].get_content());
       
       String endTitle = "</title>";
       Pattern endTitlePat = Pattern.compile(endTitle);
       Matcher endTitleMat = endTitlePat.matcher(webpages[i].get_content());
       
       String pattern = webpages[i].get_keyword(); // the pattern is the keyword
       Pattern pat = Pattern.compile(pattern); // create the pattern object
       Matcher mat = pat.matcher(webpages[i].get_content()); // create the matcher object
       
       int count = 0; // keyword count
       if(titleMat.find()) inTitle = true;
       if(endTitleMat.find()) break;
       while(mat.find() && inTitle == true)
       {
         count++;
       }
       webpages[i].keys_in_title = count;
     }
   }

}


