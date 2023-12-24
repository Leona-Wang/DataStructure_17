import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.htmlunit.BrowserVersion;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

public class WordCounter
{
	private String urlStr;
	private String content;
	
	private final WebClient webClient=new WebClient(BrowserVersion.CHROME);
	private HtmlPage page=null;	
	private String pageXml="";
	private int timeOut=20000;
	private int waitForBackgroundJavaScript = 20000;

	public WordCounter(String urlStr)
	{
		this.urlStr = urlStr;
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
	
	private String fetchContent() throws IOException
	{
		URL url = new URL(this.urlStr);
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
		return retVal;
		
	}

	public int countKeyword(String keyword) throws IOException
	{
		if (content == null)
		{
			content = fetchContent();
			
		}

		// To do a case-insensitive search, we turn the whole content and keyword into
		// upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int retVal = 0;
		int fromIdx = 0;
		int found = -1;

		while ((found = content.indexOf(keyword, fromIdx)) != -1)
		{
			retVal++;
			fromIdx = found + keyword.length();
		}

		return retVal;
	}
}