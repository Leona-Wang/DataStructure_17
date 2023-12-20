import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.htmlunit.BrowserVersion;
import org.htmlunit.IncorrectnessListener;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.javascript.DefaultJavaScriptErrorListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;





public class YahooSearch {

     public void search() {
    	 int timeOut=20000;
    	 int waitForBackgroundJavaScript = 20000;
    	 
    	 final WebClient webClient=new WebClient(BrowserVersion.CHROME);
    	 webClient.getOptions().setCssEnabled(false);
    	 webClient.getOptions().setJavaScriptEnabled(true);
    	 
    	 webClient.getOptions().setThrowExceptionOnScriptError(false);
    	 webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    	 webClient.getOptions().setActiveXNative(false);
    	 webClient.getOptions().setTimeout(timeOut);
    	 
    	 webClient.setJavaScriptErrorListener(new DummyJavascriptErrorListener());
    	 webClient.setIncorrectnessListener(new DummyIncorrectnessListener());
    	 
    	 webClient.getOptions().setDownloadImages(false);
    	 
    	 webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    	 
    	 
    	 webClient.setJavaScriptTimeout(timeOut);
    	 
    	 HtmlPage page = null;
    	 String pageXml="";
    	 
    	 
    	 try {
    		 page=webClient.getPage("https://tw.bid.yahoo.com/search/auction/product?p=%E6%8B%96%E9%9E%8B");
        	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
        	 Thread.sleep(10000);
    		 pageXml=page.asXml();
    		 Document parse=Jsoup.parse(pageXml);
    		
    		 //System.out.println(parse);
             // Get first table
             Elements divs = parse.select("span.sc-1d7r8jg-0.sc-dp9751-0.sc-1drl28c-5.czfCFU.fUBIAU.biZSHp");
             
             Elements spans=parse.select("span.sc-1d7r8jg-0.sc-dp9751-0.eLSRyH.eEsfHX");
             Elements as=parse.select("a.sc-1drl28c-1.frLXbD");
             
             // Get td Iterator
             System.out.println(divs.size());
             System.out.println(as.size());
             System.out.println(spans.size());
            
            for (int i=0;i<10;i++) {
           	  
            	
    	       	  String a=divs.get(i).text();
    	       	  String b=spans.get(i).text();
    	       	  String citeUrl = as.get(i).attr("href");
    	       	  
    	       	  System.out.println(a+" "+b);
    	       	  if (citeUrl.equals("")) {
    	       		  System.out.println("no");
    	       	  }
    	       	  else {
    	       		  System.out.println(citeUrl);
    	       		  
    	       	  }
    	       	System.out.println("----------------------------\n\n"); 
           	  
             }
        	 
        	 
        	 webClient.close();
    	 }catch(Exception e) {
    		 
    	 }
    	 
    	 
     }
	 

	
}
