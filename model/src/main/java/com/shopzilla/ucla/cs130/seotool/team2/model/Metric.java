package com.shopzilla.ucla.cs130.seotool.team2.model;

public class Metric {
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
}