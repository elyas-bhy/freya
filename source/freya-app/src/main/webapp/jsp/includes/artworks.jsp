<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtworkCollection artworks = null;
	String artistId = null;
	String collection =null;
	try {
		artistId = request.getParameter("artist");
		collection = request.getParameter("collection");
		if (collection != null){
			Long collectionId = Long.parseLong(collection);
			artworks = freya.artcollections().getArtworksByArtCollection(collectionId).execute();
		}
		else if(artistId != null)
			artworks = freya.artists().getArtworksByArtist(artistId).execute();
		else 
			artworks = freya.artworks().list().execute();
	} catch (IOException e) {
		
	}
%>
<body>
<script type="text/javascript">

	// Builds the HTML Table out of myList.
	<%if( artworks != null){%>
	var items = <%=artworks.getItems().toString()%>
	<%}else{%>
	console.log("null pointer at items initilization")
	<%}%>
	var artist = items[0].artist.name;
	var input = {
			"aaData" : items,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id", "sWidth" : "0%"},
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
		var oTable = $('#dtable').dataTable(input);
		$('#artistname').text('Artist: ' + artist);
		$('.DataTables_sort_wrapper').each(function() {
			$(this).attr("title", $(this).text());
		});
	
		$('#dtable tr').each(function() {
			var id = $(this).find('td').eq(0).text();
			$(this).find('td').eq(7).attr("data-id", id);
			$(this).find('td').eq(7).html("<a href='../artworks/view.jsp?id=" + id + "'>link</a>");
		});

		// Hide ID column
		oTable.fnSetColumnVis(0, false);
	});
	
	
</script>
<%if (artistId != null){ %>
	<p id='artistname'></p>
<%} %>
	<table id='dtable' class='dtable' border='1'></table>
</body>
</html>