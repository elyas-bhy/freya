<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtistCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<html>
<head>
<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtistCollection a = null;
	try {
		a = freya.artists().list().execute();
	} catch (IOException e) {
		
	}
%>

<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="../js/jquery.dataTables.js"></script>
<link type="text/css" rel="stylesheet" href="../css/smoothness/jquery-ui.css"/>
<script type="text/javascript">

	// Builds the HTML Table out of myList.
	// var myList = 
	<%/*=a.getItems().toString()*/%>
	var myList=[{"name" : "abc", "age" : 50},
	            {"age" : "25", "hobby" : "swimming"},
	            {"name" : "xyz", "hobby" : "programming"}];
	//var myList = jQuery.parseJSON(str);
	var input = {
		"aaData" : myList,
		"bJQueryUI": true,
        "sPaginationType": "full_numbers",
		"aoColumns" : [ 
		  { "sTitle" : "Name", "mData": "name" },
		  { "sTitle" : "Age", "mData" : "age"},
		  { "sTitle" : "Hobby", "mData" : "hobby"}
		]
	};

	$(document).ready(function() {
		$('#example').dataTable(input);
	});
</script>
</head>

<body>
	<p>liste des artistes tableau:</p>
	<table id='example' class='example' border='1'></table>
	<p>liste des artistes json:</p>
	<p><%=a.getItems().toString()%></p>
</body>
</html>