import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.htmlunit.BrowserVersion;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
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
	WordCounter wordCounter;
	
	private String title;
	private String citeUrl;
	
	public ArrayList<WebTree> trees=new ArrayList<WebTree>();
	
	private final WebClient webClient=new WebClient(BrowserVersion.CHROME);
	private HtmlPage page=null;	
	private int timeOut=20000;
	private int waitForBackgroundJavaScript = 20000;
	
	
	public GoogleQuery(String searchKeyword)
	{
		webClientSettings();
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

        	title=titles.get(i).text();
        	citeUrl=citeUrls.get(i).attr("href");
        	System.out.println("Title: " +titles.get(i).text()+ " \n url: " + citeUrls.get(i).attr("href")+"\n--------------------");
        	
        	WebPage root=new WebPage(citeUrls.get(i).attr("href"),titles.get(i).text());
        	WebTree tree=new WebTree(root);
        	
        	if (citeUrl.indexOf("momoshop")!=-1) {
        		tree.root.addChild(new WebNode(momoSearch(citeUrl)));
        	}
        	else if (citeUrl.indexOf("books")!=-1) {
        		tree.root.addChild(new WebNode(booksSearch(citeUrl)));
        	}
        	else if (citeUrl.indexOf("rakuten")!=-1) {
        		tree.root.addChild(new WebNode(rakutenSearch(citeUrl)));
        	}
        	else if (citeUrl.indexOf("bid.yahoo")!=-1) {
        		tree.root.addChild(new WebNode(yahooSearch(citeUrl)));
        	}
        	
        	trees.add(tree);
        	
        	retVal.put(titles.get(i).text(), citeUrls.get(i).attr("href"));

        }
		
		return retVal;
	}
	
	
	public void webClientSettings() {
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
	}
	
	public WebPage momoSearch(String contentUrl) {
		
		try {
			 page=webClient.getPage(contentUrl);
	     	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	     	 Thread.sleep(10000);
	     
		}catch(Exception e) {
			
		}
		String pageXml=page.asXml();
    	Document parse=Jsoup.parse(pageXml);
    	Element childName=parse.selectFirst("h3.prdName");
		Element childUrl=parse.selectFirst("a.goodsUrl");
		return (new WebPage("https://www.momoshop.com.tw/"+childUrl.text(),childName.text()));

	}

	public WebPage booksSearch(String contentUrl) {
		
		try {
			 page=webClient.getPage(contentUrl);
	     	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	     	 Thread.sleep(10000);
	     
		}catch(Exception e) {
			
		}
		String pageXml=page.asXml();
    	Document parse=Jsoup.parse(pageXml);
    	Element child=parse.selectFirst("div.table-td a");
		return (new WebPage("https:"+child.attr("href"),child.attr("title")));

	}

	public WebPage rakutenSearch(String contentUrl) {
		
		try {
			 page=webClient.getPage(contentUrl);
	     	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	     	 Thread.sleep(10000);
	     
		}catch(Exception e) {
			
		}
		String pageXml=page.asXml();
		int nameIdx=pageXml.indexOf("itemName");
		int urlIdx=pageXml.indexOf("itemUrl");
		int priceIdx=pageXml.indexOf("itemPrice");
		
		return (new WebPage(pageXml.substring(urlIdx+10, priceIdx-3),pageXml.substring(nameIdx+11, urlIdx-3)));
	}
	
	public WebPage yahooSearch(String contentUrl) {
		
		try {
			 page=webClient.getPage(contentUrl);
	     	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	     	 Thread.sleep(10000);
	     
		}catch(Exception e) {
			
		}
		String pageXml=page.asXml();
    	Document parse=Jsoup.parse(pageXml);
    	Element childName=parse.selectFirst("span.sc-1d7r8jg-0.sc-dp9751-0.sc-1drl28c-5.czfCFU.fUBIAU.biZSHp");
    	Element childUrl=parse.selectFirst("a.sc-1drl28c-1.frLXbD");
		return (new WebPage(childUrl.attr("href"),childName.text()));

	}
	
	
}