<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<html>
<head>
<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtworkCollection artworks = null;
	try {
		String id = request.getParameter("id");
		artworks = freya.artists().getArtworksByArtist(id).execute();
	} catch (IOException e) {
		
	}
%>

<script type="text/javascript" src="../../js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.dataTables.js"></script>
<link type="text/css" rel="stylesheet" href="../../css/smoothness/jquery-ui.css"/>

<script type="text/javascript">

	// Builds the HTML Table out of myList.
	var items = <%=artworks.getItems().toString()%>
	var artist = items[0].artist.name;
	var input = {
			"aaData" : items,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id"},
			  { "sTitle" : "Title", "mData" : "title"},
			  { "sTitle" : "Summary", "mData" : "summary"},
			  { "sTitle" : "Support", "mData" : "support"},
			  { "sTitle" : "Technique", "mData" : "technique"},
			  { "sTitle" : "Date", "mData" : "date"},
			  { "sTitle" : "Dimensions", "mData" : "dimension", "mRender" : function (data, type, val ) {
			  	return "(" + data.x + ", " + data.y + ", " + data.z + ")";
		          }
		      },
		      { "sTitle" : "Reproductions", "mData" : "reproductions"}
		]
	};

	$(document).ready(function() {
		$('#dtable').dataTable(input);
		$('#artistname').text('Artist: ' + artist);
	});
	
	
</script>
</head>

<body>
	<p id='artistname'></p>
	<table id='dtable' class='dtable' border='1'></table>
</body>
</html>