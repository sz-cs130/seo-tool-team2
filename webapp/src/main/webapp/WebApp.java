import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Object;
import java.lang.String;
import static java.lang.System.out;

public class WebApp
{
  public static void main(String args[])
  {
    String keyword = args[0]; // the keyword
    int num_top_pages = 3; // only use top three pages
    WebPage[] webpages = new WebPage[num_top_pages + 1];
    int i;
    for(i = 0; i < num_top_pages+1; i++)
    {
      webpages[i] = new WebPage();
    }
    //============================================= 
    // call webservice function to get the webpages
    //=============================================
    webpages[0].set_content("purple is purple hippos");
    webpages[0].set_keyword(keyword);
    for(i = 0; i < num_top_pages+1; i++)
    {
      keyword_in_page(webpages[i]);
      keyword_in_tags(webpages[i]);
      keyword_in_url(webpages[i]);
    }
    out.printf("Keys in Page: %d\n", webpages[0].keys_in_page);
  }
  public static void keyword_in_page(WebPage webpage)
  {
    String pattern = webpage.get_keyword(); // the pattern is the keyword
    Pattern pat = Pattern.compile(pattern); // create the pattern object
    Matcher mat = pat.matcher(webpage.get_content()); // create the matcher object

    int count = 0; // keyword count
    while(mat.find())
    {
      count++;
    }
    webpage.keys_in_page = count;
  }
  public static void keyword_in_tags(WebPage webpage)
  {
    ;
  }
  public static void keyword_in_url(WebPage webpage)
  {
    ;
  }
}
