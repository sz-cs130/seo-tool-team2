package com.shopzilla.ucla.cs130.seotool.team2.model;

public class Metric implements Runnable{
	public Metric () {
		
	}
	
	public void run(WebPage[] webpages) {
		
	}
	
	public String returnResults() {
		return "this metric doesn't implement results!";
	}
	
	public String returnRecommendations() {
		return "this metric doesn't implement recomendations!";
	}
	
	public String returnBoth() {
		String results = returnResults();
		results += returnRecommendations();
		return results;
	}

   public void run() {
      
   }
   
   public Thread get_thread(){
      return null;
   }
   
   public String get_name(){
      return "Name not implemented";
   }
   
}