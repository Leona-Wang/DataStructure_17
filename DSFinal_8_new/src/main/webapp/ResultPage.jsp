<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RESULT</title>
<style type="text/css">

#padding{
	padding:5px 5px 5px 5px
}

.fontp{
	font-family:georgia;
	font-size:32px;
	margin-left:20px;
	margin-top:20px;
}

.text-border{
	border-style:double;
	border-width:5px;
	padding:20px;
}

.search-border{
	border-radius: 75px/300px;
}

.button{
	postion:absolute;
	left:75%;
	margin-left:1075px;
	width:150px;
	height:50px;
}

</style>
</head>
<body style='background-color:#e0ceb8'>
<form action='${requestUri}' method='get'>

<div class='fontp'>

	<p><img alt="ShoppingBag" src="Images/ShoppingBag.png"
	style='width: 100px; height:100px'>Searching Result</p>
	
</div>

<div class='text-border' style='font-size:150%; font-family:courier;
position:absolute; margin-top:50px; margin-left:150px'>

<%
		String[][] orderList = (String[][]) request.getAttribute("query");
		for (int i = 0; i < orderList.length; i++) {
			String s=orderList[i][1];
			//s=s.substring(7);
		%>
		<ol style='list-style-type:square;' >
		<li><a href='<%=s%>'><%=orderList[i][0]%> </a></li>
		</ol>
		<%
}
%>


</div>

<div>

	<input type='text' name='keyword' class='search-border' id='padding'
	style='font-size:120%; font-family:courier; position:absolute; left:15%; top:10%;
	margin-top:10px; margin-left:275px; width:500px; height:25px' 
	placeholder= 'Input the keyword' value='<%=request.getParameter("keyword")%>'/> 
	
	<a href ='http://localhost:8080/DSFinal_8_new/GetRequest'><input type='submit' class ='search-border' value='Search!'
	style='position:absolute; top:10%; margin-left:1050px; margin-top:13px; width:100px; height:30px; 
	background-color:#fcfaf5; font-family:courier' accesskey='enter'></a>
	
</div>

</form>
</body>
</html>