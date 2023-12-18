import java.net.MalformedURLException;

import org.htmlunit.ScriptException;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.javascript.JavaScriptErrorListener;
import org.htmlunit.javascript.host.URL;

public class DummyJavascriptErrorListener implements JavaScriptErrorListener {

	@Override
	public void scriptException(HtmlPage page, ScriptException scriptException) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadScriptError(HtmlPage page, java.net.URL scriptUrl, Exception exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
		// TODO Auto-generated method stub
		
	}

  
}