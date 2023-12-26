import java.io.IOException;
import java.util.ArrayList;

public class WebPage
{
	public String url;
	public String name;
	public String pageXml;
	public WordCounter counter;
	public double score;

	public WebPage(String url, String name,String pageXml)
	{
		this.url = url;
		this.name = name;
		this.pageXml=pageXml;
		this.counter = new WordCounter(url,pageXml);
	}

	public void setScore(ArrayList<Keyword> keywords) throws IOException
	{
		score = 0;
		// YOUR TURN
//		1. calculate the score of this webPage
		
		for (Keyword k:keywords) {
			score+=counter.countKeyword(k.name)*k.weight;
		}
		

	}
}