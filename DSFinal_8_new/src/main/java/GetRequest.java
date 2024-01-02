import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import org.htmlunit.javascript.JavaScriptErrorListener;
import org.htmlunit.WebClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetRequest
 */
@WebServlet("/GetRequest")
public class GetRequest extends HttpServlet{
	private static final long serialVersionUID = 1L;
  
	public GetRequest() {
		super();
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		if (request.getParameter("keyword") == null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri",requestUri);
			request.getRequestDispatcher("/SearchPage.jsp").forward(request, response);
			return;
		}
		
		System.out.println(request.getParameter("keyword"));
		GoogleQuery google = new GoogleQuery(request.getParameter("keyword"));
		HashMap<String, String> query = google.query();
		
		String[][] s = new String [query.size()][2];
		request.setAttribute("query", s);
		
		int num = 0;
		for(Entry<String, String> entry: query.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			s[num][0] = key;
			s[num][1] = value;
			num++;
		}
		request.getRequestDispatcher("/ResultPage.jsp").forward(request, response);
		
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
	
}