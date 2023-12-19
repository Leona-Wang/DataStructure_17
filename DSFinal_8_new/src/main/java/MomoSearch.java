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





public class MomoSearch {

     
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
	   		 page=webClient.getPage("https://www.momoshop.com.tw/search/searchShop.jsp?keyword=%E6%B4%97%E8%A1%A3%E6%A9%9F&searchType=1&cateLevel=0&cateCode=&curPage=1&_isFuzzy=0&showType=chessboardType&isBrandCategory=N&serviceCode=MT01&osm=googleKw&utm_source=google&utm_medium=cpc6&utm_content=keyword&gclid=CjwKCAiA-P-rBhBEEiwAQEXhH8zprthG9w7MaixZ3qB9bLDHfj26kl4VIdF2g3CDQSoqzuWssfA0GhoCVr4QAvD_BwE");
	      	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	      	 Thread.sleep(10000);
	      	 pageXml=page.asXml();
	      	 Document parse=Jsoup.parse(pageXml);
	      	 
	      	 Elements divs = parse.select("h3.prdName");
		     Elements spans=parse.select("span.price");
		     Elements as=parse.select("a.goodsUrl");
	        
	        // Get td Iterator
	         System.out.println(divs.size());
	         System.out.println(spans.size());
	       
		       for (int i=0;i<divs.size();i++) {
		    	   String a=divs.get(i).text();
			       String b=spans.get(i).text();
			       String citeUrl = as.get(i).attr("href").replace("/url?q=", "");
			       	  
			       	  
			       if (a.equals("")) {
			    	   System.out.println("no");
			       }
			       else {
			       	   System.out.println(a+" price "+b+"\n"+"https://www.momoshop.com.tw/"+citeUrl);  
			       	  }
			       System.out.println("----------------------------\n\n"); 
		      	  
		        }
		   	 
		   	 
		   	 webClient.close();
   	 }catch(Exception e) {
   		 
   	 }
   	 
	}
	 

	
	
}
