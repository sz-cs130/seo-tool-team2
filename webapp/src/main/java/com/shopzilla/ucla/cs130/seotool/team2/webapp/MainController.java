package com.shopzilla.ucla.cs130.seotool.team2.webapp;

import com.shopzilla.ucla.cs130.seotool.team2.service.WebService;
import com.shopzilla.ucla.cs130.seotool.team2.model.*;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
//@RequestMapping("/form")
public class MainController {
   
   WebApp app;
   WebPage [] webpages;
	
   @RequestMapping("*")
   public String welcome(){    
      return "index";
   }
   
   @RequestMapping(value="/optimize", method=RequestMethod.GET)
   public String mirrorGet(String query, String targetsite, String targeturl, Model model){
      // calls the webservice
      webpages = WebService.service(query, targetsite, targeturl);
      
      // WebApp gets called here
	  app = new WebApp(webpages); 
	  
	  app.run();
	  String output = app.returnResults(); // Change this to returnBoth for easier debugging on a single page
	   
      model.addAttribute("query", query);
      model.addAttribute("output", output);
 
     
      return "results";
   }
   
   @RequestMapping(value="/recommend")
   public String recommend(Model model){
      // WebApp gets called here
	  String output = app.returnRecomendations(); // Change this to returnBoth for easier debugging on a single page 
     String query = webpages[0].get_keyword();
	  
      model.addAttribute("query", query);
      model.addAttribute("output", output);
	  
      return "recommendations";
   }
   
   //@RequestMapping(method = RequestMethod.GET)

}
