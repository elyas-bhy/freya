<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ReproductionCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<html>
<head>
<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ReproductionCollection reproductions = null;
	String artworkId = null;
	try {
		artworkId = request.getParameter("artwork");
		if(artworkId != null)
			reproductions = freya.artworks().getReproductionsByArtwork(artworkId).execute();
		else
			reproductions = freya.reproductions().list().execute();
	} catch (IOException e) {
		
	}
%>


<script type="text/javascript">

	// Builds the HTML Table out of myList.
	<%if( reproductions != null){%>
	var items = <%=reproductions.getItems().toString()%>
	var input = {
			"aaData" : items,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id", "sWidth" : "0%"},
			  { "sTitle" : "Stock", "mData" : "stock"},
			  { "sTitle" : "Price", "mData" : "price"},
			  { "sTitle" : "Support", "mData" : "support"}
		]
	};
	$(document).ready(function() {
		var oTable = $('#dtable').dataTable(input);
		$('.DataTables_sort_wrapper').each(function() {
			$(this).attr("title", $(this).text());
		});

		// Hide ID column
		oTable.fnSetColumnVis(0, false);
	});
	<%}else{%>
	console.log("null pointer at items initilization")
	<%}%>
	
</script>
</head>

<body>
	<table id='dtable' class='dtable' border='1'></table>
</body>
</html>