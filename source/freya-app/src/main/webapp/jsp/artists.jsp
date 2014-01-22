<jsp:include page="../includes/header.jsp"></jsp:include>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtistCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtistCollection artists = null;
	try {
		artists = freya.artists().list().execute();
	} catch (IOException e) {
		
	}
%>

<script type="text/javascript">

	// Builds the HTML Table out of myList.
	var items = <%=artists.getItems().toString()%>
	
	var input = {
			"aaData" : items,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id", "sWidth" : "0%"},
			  { "sTitle" : "Artist name", "mData" : "name", },
			  { "sTitle" : "Artworks", "mData" : "name"},
			  { "sTitle" : "Photos", "mData" : "name"},
			]
		};

	$(document).ready(function() {
		var oTable = $('#dtable').dataTable(input);
		$('.DataTables_sort_wrapper').each(function() {
			$(this).attr("title", $(this).text());
		});
	
		$('#dtable tr').each(function() {
			var id = $(this).find('td').eq(0).text();
			$(this).find('td').eq(2).attr("data-id", id);
			$(this).find('td').eq(3).attr("data-id", id);


			$(this).find('td').eq(2).html("<a href='artworks.jsp?artist=" + id + "'>link</a>");
			$(this).find('td').eq(3).html("<a href='photos.jsp?artist=" + id + "'>link</a>");

		});

		// Hide ID column
		oTable.fnSetColumnVis(0, false);
	});
	
	
</script>
</head>

<body>
	<table id='dtable' class='dtable' border='1'></table>
</body>
</html>