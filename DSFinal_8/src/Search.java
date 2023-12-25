import java.util.ArrayList;
import java.util.regex.Pattern;

import org.htmlunit.BrowserVersion;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
	
	private ProductList list=new ProductList();
	private ArrayList<Product> productList=new ArrayList<Product>();
	
	String productName="";
    String stringPrice="";
    int productPrice=0;
    String productUrl="";
    String regex="[^0-9]";
	
	public Search(String searchKeyword) {
		
		this.searchKeyword=searchKeyword;
		
		try {
			
			encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			
			this.momoUrl="https://www.momoshop.com.tw/search/searchShop.jsp?keyword="+encodeKeyword+"&searchType=6&cateLevel=0&cateCode=&curPage=1&_isFuzzy=0&showType=chessboardType&isBrandCategory=N&serviceCode=MT01";
			this.booksUrl="https://search.books.com.tw/search/query/key/"+encodeKeyword+"/cat/all";
			this.rakutenUrl="https://www.rakuten.com.tw/search/"+encodeKeyword+"/";
			this.yahooUrl="https://tw.bid.yahoo.com/search/auction/product?p="+encodeKeyword;
			
		}catch(Exception e){
			
			System.out.println(e.getMessage());
			
		}
		
		webClientSettings();
		
		momoSearch();
		booksSearch();
		rakutenSearch();
		yahooSearch();
		refreshList();
		
		
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

	
	public int fetchContent(String url) {
		
		try {
			page=webClient.getPage(url);
	      	webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	      	Thread.sleep(10000);
	      	pageXml=page.asXml();
	      	//System.out.println(pageXml);
	      	Document parse=Jsoup.parse(pageXml);
		}catch(Exception e) {
			
		}
		
		int retVal=0;
		int fromIdx=0;
		int found=-1;
		
		while ((found = pageXml.indexOf("洗衣機", fromIdx)) != -1)
		{
			retVal++;
			fromIdx = found + 3;
		}
		
		return retVal;
		
		
	}
	
	
	public void momoSearch() {
		try {
	   		 page=webClient.getPage(momoUrl);
	      	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	      	 Thread.sleep(10000);
	      	 pageXml=page.asXml();
	      	 Document parse=Jsoup.parse(pageXml);
	      	 
	      	 Elements divs = parse.select("h3.prdName");
		     Elements spans=parse.select("span.price");
		     Elements as=parse.select("a.goodsUrl");
		     
		     

		       for (int i=0;i<3;i++) {
		    	   
		    	   productName=divs.get(i).text();
		    	   
		    	   stringPrice=spans.get(i).text();
		    	   stringPrice=Pattern.compile(regex).matcher(stringPrice).replaceAll("").trim();
		    	   productPrice=Integer.parseInt(stringPrice);
		    	   
		    	   productUrl="https://www.momoshop.com.tw/"+as.get(i).attr("href").replace("/url?q=", "");
		    	   
		    	   Product p=new Product(productName,productPrice,productUrl);
		    	   
		    	   list.addProduct(p);
		        }
		       
 	 }catch(Exception e) {
 		 
 	 }
	}

	public void booksSearch() {
		
		try {
			page=webClient.getPage(booksUrl);
			webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
			Thread.sleep(10000);
			pageXml=page.asXml();
			Document parse=Jsoup.parse(pageXml);
			 
			
			Elements bs=parse.select("ul.price.clearfix");
			Elements as=parse.select("div.table-td a");
			           
			// Get td Iterator
			         
			for (int i=0;i<3;i++) {
			    
				productName=as.get(2*i).attr("title");
				
				stringPrice=bs.get(i).text();
				if (stringPrice.indexOf(",")!=-1) {
					stringPrice=stringPrice.substring(stringPrice.indexOf(","));
				}
				
				stringPrice=Pattern.compile(regex).matcher(stringPrice).replaceAll("").trim();
				productPrice=Integer.parseInt(stringPrice);
				
				productUrl ="https:"+ as.get(2*i).attr("href");
				
				Product p=new Product(productName,productPrice,productUrl);
				list.addProduct(p);
			}
			
			   	
		}catch(Exception e) {
		   		 
		}        
	}
	
	public void rakutenSearch() {
		try {
			page=webClient.getPage(rakutenUrl);
			webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
			Thread.sleep(10000);
			pageXml=page.asXml();
		}catch(Exception e) {
		   		 
		}

		int nameIdx=0;
		int urlIdx=0;
		int priceIdx=0;
		int minIdx=0;
		int maxIdx=0;
		int itemListPriceIdx=0;
		
		String min="";
		String max="";
				 
		for (int i=0;i<3;i++) {
					 
			nameIdx=pageXml.indexOf("itemName");
			urlIdx=pageXml.indexOf("itemUrl");
			priceIdx=pageXml.indexOf("itemPrice");
			minIdx=pageXml.indexOf("\"min\"");
			maxIdx=pageXml.indexOf("\"max\"");
			itemListPriceIdx=pageXml.indexOf("\"itemListPrice\"");
						 
			productName=pageXml.substring(nameIdx+11, urlIdx-3);
			
			productUrl=pageXml.substring(urlIdx+10, priceIdx-3);
			
			min=pageXml.substring(minIdx+6, maxIdx-1);
			
			max=pageXml.substring(maxIdx+6, itemListPriceIdx-2);
			
			int minPrice=(int)Double.parseDouble(min);
			int maxPrice=(int)Double.parseDouble(max);
			productPrice=(minPrice+maxPrice)/2;
			
			Product p=new Product(productName,productPrice,productUrl);
			list.addProduct(p);
			
			pageXml=pageXml.substring(itemListPriceIdx+1);
			nameIdx=pageXml.indexOf("itemName");
			pageXml=pageXml.substring(nameIdx-1);
		}
		
		
		
	}
	
	public void yahooSearch() {
		try {
	   		 page=webClient.getPage(yahooUrl);
	       	 webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	       	 Thread.sleep(10000);
	   		 pageXml=page.asXml();
	   		 Document parse=Jsoup.parse(pageXml);
	   		
	   		 //System.out.println(parse);
	            // Get first table
	         Elements divs = parse.select("span.sc-1d7r8jg-0.sc-dp9751-0.sc-1drl28c-5.czfCFU.fUBIAU.biZSHp");
	            
	         Elements spans=parse.select("span.sc-1d7r8jg-0.sc-dp9751-0.eLSRyH.eEsfHX");
	         Elements as=parse.select("a.sc-1drl28c-1.frLXbD");

	         for (int i=0;i<3;i++) {
	          	  
	   	       	  productName=divs.get(i).text();
	   	       	  stringPrice=spans.get(i).text();
	   	       	  productUrl = as.get(i).attr("href");
	   	       	  
	   	       	  stringPrice=Pattern.compile(regex).matcher(stringPrice).replaceAll("").trim();
	   	       	  
	   	       	  int price=Integer.parseInt(stringPrice);
	   	       	  
	   	       	  Product p=new Product(productName,productPrice,productUrl);
	   	       	  list.addProduct(p);
	          	  
	            }
	       	 
	   	 }catch(Exception e) {
	   		 
	   	 } 
	}

	public void refreshList() {
		
		productList=list.sortProduct();
		
		int i=1;
		for (Product p:productList) {
			
			System.out.println(i+"\n"+p.getProductName()+"\n"+p.getProductPrice()+"\n"+p.getProductUrl()+"\n------------------------");
			i++;
		}
	}
	
	

}