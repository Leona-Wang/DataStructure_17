<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GOSHOPPING</title>
<style type="text/css">

#padding{
	padding: 10px 10px 10px 10px;
}

.button{
	postion:absolute;
	left:75%;
	top:25%;
	margin-top:250px;
	margin-left:975px;
	width:150px;
	height:50px;	
}

.img{
	postion:absolute;
	top:25%;
	left:50%;
	margin-top:100px;
	margin-left:150px;
	width:50px;
	height:25px;
}

.border-style {
	border-radius: 75px/300px;
}

.fontp{
	font-family:courier;
	font-size:24px;
	position:absolute;
	margin-top:225px;
	margin-left:600px;
}

</style>
</head>
<body style='background-color:#e0ceb8'>
<form action='${requestUri}' method='get'>

<div class='img'>
	<img src='Images/ShoppingCart.gif' alt='ShoppingCart'>
</div>

<div>
	<input type='text' class='border-style' id='padding' name='keyword'
	style='font-size:120%; font-family:courier; position:absolute; left:20%; top:25%;
	margin-top:100px; margin-left:275px; width:500px; height:25px' 
	placeholder= 'Input the keyword' required/> 
</div>

<div class='fontp'>
	<p>~Find something perfect~</p>
</div>

<div class='button'>
	<a href ='http://localhost:8080/DSFinal_8_new/GetRequest'><input type='submit' class ='border-style' value='Search!'
	style='width:100px; height:30px; background-color:#fcfaf5; font-family:courier' accesskey='enter'></a>
</div>

</form>
</body>
</html>