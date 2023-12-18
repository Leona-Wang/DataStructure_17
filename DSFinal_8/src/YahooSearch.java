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

     
	 

	public static void main(String[] args) throws Exception {
    	 
		
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
    	 
    	 page=webClient.getPage("https://tw.bid.yahoo.com/item/100429386950");
    	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
    	 
		 pageXml=page.asXml();
		 Document parse=Jsoup.parse(pageXml);
		 
         // Get first table
         Elements divs = parse.select("em.price__IiPh4");
         
         
         // Get td Iterator
         
         System.out.println(divs.size());
        for (Element div:divs) {
       	  
       	  String a=div.text();
       	  if (a.equals("")) {
       		  System.out.println("no");
       	  }
       	  else {
       		  System.out.println(a);
       		  System.out.println("----------------------------\n\n");
       	  }
       	  
       	  
         }
    	 
    	 
    	 webClient.close();
    	 
    	 /*try {
    		 
    	 }catch(Exception e) {
    		 //e.printStackTrace();
    	 }finally {
    		 
    	 }*/
    	 
    	 
    	 
    	 
    	 
    	 
          // URL
    	/*URL url = new URL("https://shopee.tw/product/26856452/2994252379?gad_source=1&gclid=Cj0KCQiAj_CrBhD-ARIsAIiMxT9imhKKfjPlHipRWVhNxWuVNzsY6IxEETAE4micIC0_PjUluuRd1IAaAhB8EALw_wcB");
 		URLConnection conn = url.openConnection();
 		InputStream in = conn.getInputStream();
 		BufferedReader br = new BufferedReader(new InputStreamReader(in));
 		
 		
 		String retVal = "";

 		String line = null;

 		while ((line = br.readLine()) != null)
 		{
 			retVal = retVal + line + "\n";
 		}

 		System.out.println(retVal);
   		System.out.println("-------------------------");
 		
          // Create the Document Object
          Document doc = Jsoup.parse(retVal);
          // Get first table
          Elements divs = doc.select("div");
          // Get td Iterator
          
         /* for (Element div:divs) {
        	  
        	  String a=div.text();
        	  if (a.equals("")) {
        		  System.out.println("no");
        	  }
        	  else {
        		  System.out.println(a);
        	  }
        	  
        	  
          }*/
          
          // Print content*/
                         
     }
}
