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
   
   @RequestMapping("*")
   public String welcome(){    
      return "index";
   }
   
   @RequestMapping(value="/optimize", method=RequestMethod.GET)
   public String mirrorGet(String query, String targetsite, Model model){
      // calls the webservice
      WebPage [] webpages = WebService.service(query, targetsite);
      // WebApp gets called here
	  WebApp app = new WebApp(webpages); 
	  
	  app.run();
	  String output = app.returnResults();
	   
      model.addAttribute("query", query);
      model.addAttribute("output", output);
      
      return "results";
   }
   
   //@RequestMapping(method = RequestMethod.GET)

}
