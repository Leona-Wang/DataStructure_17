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





public class RakutenSearch {
	
	
	
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
   		 page=webClient.getPage("https://www.rakuten.com.tw/search/%E6%B2%99%E7%99%BC/");
      	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
      	 Thread.sleep(10000);
   		 pageXml=page.asXml();
   	 }catch(Exception e) {
   		 
   	 }
   	 
   	 
		 //Document parse=Jsoup.parse(pageXml);
		 
		 int nameIdx=0;
		 int urlIdx=0;
		 int priceIdx=0;
		 int minIdx=0;
		 int maxIdx=0;
		 int itemListPriceIdx=0;
		 
		 String name="";
		 String url="";
		 String min="";
		 String max="";
		 
		 for (int i=0;i<10;i++) {
			 
			 nameIdx=pageXml.indexOf("itemName");
			 urlIdx=pageXml.indexOf("itemUrl");
			 priceIdx=pageXml.indexOf("itemPrice");
			 minIdx=pageXml.indexOf("\"min\"");
			 maxIdx=pageXml.indexOf("\"max\"");
			 itemListPriceIdx=pageXml.indexOf("\"itemListPrice\"");
			 
			 name=pageXml.substring(nameIdx+11, urlIdx-3);
			 System.out.println(name);
			 url=pageXml.substring(urlIdx+10, priceIdx-3);
			 System.out.println(url);
			 min=pageXml.substring(minIdx+6, maxIdx-1);
			 System.out.println(min);
			 max=pageXml.substring(maxIdx+6, itemListPriceIdx-2);
			 System.out.println(max+"\n------------------------");
			 
			 pageXml=pageXml.substring(itemListPriceIdx+1);
			 nameIdx=pageXml.indexOf("itemName");
			 pageXml=pageXml.substring(nameIdx-1);
		 }
		 
		 webClient.close();
	}
}
