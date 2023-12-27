import java.io.IOException;
import java.util.ArrayList;

public class WebTree
{
	public WebNode root;
	public ArrayList<Keyword> keywords;

	public WebTree(WebPage rootPage)
	{
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException
	{
		this.keywords=keywords;
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException
	{
		// YOUR TURN
		// 3. compute the score of children nodes via post-order, then setNodeScore for
		// startNode
		startNode.webPage.setScore(keywords);
		startNode.nodeScore+=startNode.webPage.score;
		root.setNodeScore(keywords);
		
	}

	public void eularPrintTree()
	{
		eularPrintTree(root);
	}

	private void eularPrintTree(WebNode startNode)
	{
		
		System.out.println(startNode.webPage.name+"\n"+startNode.webPage.url+"\n"+startNode.nodeScore+"\n------------");
		
		/*int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1) {
			System.out.print("\n" + repeat("\t", nodeDepth - 1));
		}
		
		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);
		
		// YOUR TURN
		// 4. print child via pre-order

		for (WebNode w:startNode.children) {
			
			
			eularPrintTree(w);
			
		}
		
		System.out.print(")");

		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));*/
		
	}

	private String repeat(String str, int repeat)
	{
		String retVal = "";
		for (int i = 0; i < repeat; i++)
		{
			retVal += str;
		}
		return retVal;
	}
}