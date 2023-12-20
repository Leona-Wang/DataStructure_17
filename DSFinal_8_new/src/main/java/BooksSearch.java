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





public class BooksSearch {
	
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
			page=webClient.getPage("https://search.books.com.tw/search/query/key/%E6%8B%96%E9%9E%8B/cat/all");
			webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
			Thread.sleep(10000);
			pageXml=page.asXml();
			Document parse=Jsoup.parse(pageXml);
			 
			Elements divs = parse.select("div.table-td");
			 
			Elements as=parse.select("div.table-td a");
			           
			// Get td Iterator
			System.out.println(divs.size());
			System.out.println(as.size());
			//System.out.println(spans.size());
			          
			for (int i=0;i<10;i++) {
			         	  
				String a=divs.get(i).text();
				//String b=spans.get(i).text();
				String citeUrl = as.get(2*i).attr("href");
				   	       	  
				System.out.println(a);
				if (citeUrl.equals("")) {
					System.out.println("no");
				}
				else {
					System.out.println("https:"+citeUrl);
				   	       		  
				}
				System.out.println("----------------------------\n\n"); 
				         	  
			}
			      	 
			      	 
			webClient.close();
			   	
		}catch(Exception e) {
		   		 
		}
		   	 
	}

}
