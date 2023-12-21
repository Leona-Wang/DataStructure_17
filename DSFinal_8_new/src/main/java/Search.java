import org.htmlunit.BrowserVersion;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

public class Search{
	
	private String searchKeyword;
	private String encodeKeyword;
	
	private String momoUrl;
	private String booksUrl;
	private String rakutenUrl;
	private String yahooUrl;
	
	private final WebClient webClient=new WebClient(BrowserVersion.CHROME);
	private HtmlPage page=null;	
	private String pageXml="";
	private int timeOut=20000;
	private int waitForBackgroundJavaScript = 20000;
	
	public Search(String searchKeyword) {
		
		this.searchKeyword=searchKeyword;
		
		try {
			
			encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			
			this.momoUrl="https://www.momoshop.com.tw/search/searchShop.jsp?keyword="+encodeKeyword+"&searchType=6&cateLevel=0&cateCode=&curPage=1&_isFuzzy=0&showType=chessboardType&isBrandCategory=N&serviceCode=MT01";
			this.booksUrl="https://search.books.com.tw/search/query/key/"+encodeKeyword+"/cat/all";
			this.rakutenUrl="https://www.rakuten.com.tw/search/"+encodeKeyword+"/";
			this.yahooUrl="https://tw.bid.yahoo.com/search/auction/product?p="+encodeKeyword;
			
			System.out.println(momoUrl+"\n"+booksUrl+"\n"+rakutenUrl+"\n"+yahooUrl);
			
			
			
		}catch(Exception e){
			
			System.out.println(e.getMessage());
			
		}
		
		webClientSettings();
		
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
	
	
	
	
	
	
	
	
	
	
	
	
}