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
	ArtistCollection artists = null;
	try {
		artists = freya.artists().list().execute();
	} catch (IOException e) {
		
	}
%>

<script type="text/javascript" src="../../js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.dataTables.js"></script>
<link type="text/css" rel="stylesheet" href="../../css/smoothness/jquery-ui.css"/>

<script type="text/javascript">

	// Builds the HTML Table out of myList.
	var items = <%=artists.getItems().toString()%>
	
	var input = {
		"aaData" : items,
		"bJQueryUI": true,
        "sPaginationType": "full_numbers",
		"aoColumns" : [ 
		  { "sTitle" : "ID", "mData": "id"},
		  { "sTitle" : "Artist name", "mData" : "name"},
		  { "sTitle" : "Artworks", "mData" : "name", "sClass" : "aartworks"},
		  { "sTitle" : "Photos", "mData" : "name", "sClass" : "aphotos"},
		]
	};

	$(document).ready(function() {
		$('#dtable').dataTable(input);
		
		$('#dtable tr').each(function() {
			var id = $(this).find('td').eq(0).text();
			$(this).find('td').eq(2).attr("data-id", id);
			$(this).find('td').eq(3).attr("data-id", id);
		});

		$('.aartworks').click(function() {
			var id = $(this).data("id");
			window.location.href = "artworks.jsp?id=" + id;
		});

		$('.aphotos').click(function() {
			alert("Redirecting to photos: " + $(this).data("id"));
		});
	});
	
	
</script>
</head>

<body>
	<p>liste des artistes tableau:</p>
	<table id='dtable' class='dtable' border='1'></table>
	<p>liste des artistes json:</p>
	<p><%=artists.getItems().toString()%></p>
</body>
</html>