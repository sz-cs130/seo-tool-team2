
package com.shopzilla.ucla.cs130.seotool.team2.model;

public class WebPage
{
  protected String content; // the content of the webpage
  protected int size; // the length of the webpage
  protected int rank; // the ranking
  protected String keyword; // the keyword
  protected String[] keytokens;
  protected String url;

  //============ METRICS ==================
  public int keys_in_page;
  public int keys_in_url;
  public int keys_in_tags;
  public int keys_in_title;
  //=======================================
  public WebPage()
  {
    this.content = "";
    this.size = 0;
    this.rank = 0;
    this.keyword = "";
    this.url = null;
    this.keytokens = null;
  }
  public WebPage(String content, int size, int rank, String keyword, String url)
  {
    this.content = content;
    this.size = size;
    this.rank = rank;
    this.keyword = keyword;
    this.url = url;
    this.keytokens = null;
  }

  public String get_content() { return content; }
  public int get_size() { return size; }
  public int get_rank() { return rank; }
  public String get_keyword() { return keyword; }
  public String get_url() {return url;}
  public String[] get_keytokens(){ return keytokens;}
  
  public void set_content( String in_content )
  {
    content = in_content;
  }
  public int set_size(int in_size)
  { 
    if(0 > in_size)
      return -1;

    size = in_size;
    return 0; 
  }

  public int set_rank(int in_rank)
  {
    if(0 > in_rank)
      return -1;
    rank = in_rank;
    return 0;
  }
  public int set_keyword(String in_key)
  {
    keyword = in_key;
    return 0;
  }
  public void set_url(String url){
     this.url = url;
  }
  public void set_keytokens(String[] tokens){
     keytokens = tokens;
  }
}

