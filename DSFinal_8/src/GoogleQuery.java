import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery 
{
	public String searchKeyword;
	public String url;
	public String content;
	Document doc=null;
	
	
	public GoogleQuery(String searchKeyword)
	{
		this.searchKeyword = searchKeyword;
		try 
		{
			
			String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
			
			// this.url = "https://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void fetchContent() throws IOException
	{
		
        // Connect to the Google search page
        try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public HashMap<String, String> query() throws IOException
	{
		
		fetchContent();
		HashMap<String, String> retVal = new HashMap<String, String>();
		
		Elements titles=doc.select("h3.LC20lb.MBeuO.DKV0Md");
		Elements citeUrls = doc.select("div.yuRUbf a");
        for (int i=0;i<titles.size();i++) {

        	System.out.println("Title: " +titles.get(i).text()+ " \n url: " + citeUrls.get(i).attr("href")+"\n--------------------");
        	retVal.put(titles.get(i).text(), citeUrls.get(i).attr("href"));

        }
		
		
		return retVal;
	}
}