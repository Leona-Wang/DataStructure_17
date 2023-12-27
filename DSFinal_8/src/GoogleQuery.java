import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
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

import com.sun.source.tree.Tree;

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
	public ArrayList<Keyword> keywordList=new ArrayList<Keyword>();
	public ArrayList<Integer> scores=new ArrayList<Integer>();
	
	private final WebClient webClient=new WebClient(BrowserVersion.CHROME);
	private HtmlPage page=null;	
	private int timeOut=20000;
	private int waitForBackgroundJavaScript = 20000;
	private String pageXml="";
	private String parentContent;
	
	
	public GoogleQuery(String searchKeyword)
	{
		
		this.searchKeyword = searchKeyword;
		
		try 
		{
			
			String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			String shop=java.net.URLEncoder.encode("網購","utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+shop+"&oe=utf8&num=5";
			
			// this.url = "https://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		webClientSettings();
		keywordList();
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
		
		System.out.println(titles.size());
        for (int i=0;i<titles.size();i++) {

        	
        	title=titles.get(i).text();
        	
        	citeUrl=citeUrls.get(i).attr("href");
        	
        	parentContent=takeContent(citeUrl);
        	
        	
        	WebPage root=new WebPage(citeUrl,title,parentContent);
        	WebTree tree=new WebTree(root);
        	
        	
        	/*if (citeUrl.indexOf("momoshop")!=-1) {
        		WebPage w=momoSearch(parentContent);
        		
        		tree.root.addChild(new WebNode(w));
        	}*/
        	
        	if (citeUrl.indexOf("books")!=-1) {
        		tree.root.addChild(new WebNode(booksSearch(parentContent)));
        	}
        	else if (citeUrl.indexOf("rakuten")!=-1) {
        		tree.root.addChild(new WebNode(rakutenSearch(parentContent)));
        	}
        	else if (citeUrl.indexOf("bid.yahoo")!=-1) {
        		tree.root.addChild(new WebNode(yahooSearch(parentContent)));
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
	
	public String takeContent(String contentUrl) {
		
		try {
			 page=webClient.getPage(contentUrl);
	     	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	     	 Thread.sleep(10000);
	     
		}catch(Exception e) {
			
		}
		pageXml=page.asXml();
		
		return pageXml;
	}
	
	
	public WebPage momoSearch(String parentContent) {
		
    	Document parse=Jsoup.parse(parentContent);
    	Element childName=parse.selectFirst("h3.prdName");
		Element childUrl=parse.selectFirst("div.TabContentD.selected a");
		String pageContent=takeContent("https://www.momoshop.com.tw/"+childUrl.attr("href"));
		
		return (new WebPage("https://www.momoshop.com.tw/"+childUrl.attr("href"),childName.text(),pageContent));
	}

	public WebPage booksSearch(String parentContent) {
		
    	Document parse=Jsoup.parse(parentContent);
    	Element child=parse.selectFirst("h4 a");
		return (new WebPage(child.attr("href"),child.text(),takeContent(child.attr("href"))));

	}

	public WebPage rakutenSearch(String parentContent) {
		
		
		int nameIdx=parentContent.indexOf("itemName");
		int urlIdx=parentContent.indexOf("itemUrl");
		int priceIdx=parentContent.indexOf("itemPrice");
		
		return (new WebPage(parentContent.substring(urlIdx+10, priceIdx-3),parentContent.substring(nameIdx+11, urlIdx-3),takeContent(parentContent.substring(urlIdx+10, priceIdx-3))));
	}
	
	public WebPage yahooSearch(String parentContent) {
		
    	Document parse=Jsoup.parse(parentContent);
    	Element childName=parse.selectFirst("span.sc-1d7r8jg-0.sc-dp9751-0.sc-1drl28c-5.czfCFU.fUBIAU.biZSHp");
    	Element childUrl=parse.selectFirst("a.sc-1drl28c-1.frLXbD");
		return (new WebPage(childUrl.attr("href"),childName.text(),takeContent(childUrl.attr("href"))));

	}
	
	
	public void keywordList(){
		
		keywordList.add(new Keyword(searchKeyword,1));
		keywordList.add(new Keyword("蝦皮",1));
		keywordList.add(new Keyword("shopee",1));
		keywordList.add(new Keyword("momo",2));
		keywordList.add(new Keyword("pchome",2));
		keywordList.add(new Keyword("rakuten",1));
		keywordList.add(new Keyword("樂天",1));
		keywordList.add(new Keyword("books",1));
		keywordList.add(new Keyword("博客來",1));
		keywordList.add(new Keyword("維基百科",-100));
		keywordList.add(new Keyword("百度百科",-100));
		
		
	}
	
	public void printResult() throws IOException {
		
		
		for (WebTree t:trees) {
			t.setPostOrderScore(keywordList);
			if (t.root.nodeScore>=0) {
				scores.add(t.root.nodeScore);
			}
		}
		Sort s=new Sort(scores);
		
		Collections.reverse(scores);
		for (int score:scores) {
			for (WebTree t:trees) {
				if (t.root.nodeScore==score) {
					t.eularPrintTree();
					break;
				}
			}
		}
		
		
		
		
		
	}
	
	
	
	
	
	
}