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
	var items = <%=a.getItems().toString()%>
	
	var input = {
		"aaData" : items,
		"bJQueryUI": true,
        "sPaginationType": "full_numbers",
		"aoColumns" : [ 
		  { "sTitle" : "ID", "mData": "id" },
		  { "sTitle" : "Artist name", "mData" : "name"}
		]
	};

	$(document).ready(function() {
		$('#dtable').dataTable(input);
	});
</script>
</head>

<body>
	<p>liste des artistes tableau:</p>
	<table id='dtable' class='dtable' border='1'></table>
	<p>liste des artistes json:</p>
	<p><%=a.getItems().toString()%></p>
</body>
</html>