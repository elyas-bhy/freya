<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtworkCollection artworks = null;
	String artistId_aw = null;
	String collection =null;
	try {
		artistId_aw = request.getParameter("artist");
		collection = request.getParameter("collection");
		if (collection != null){
			Long collectionId = Long.parseLong(collection);
			artworks = freya.artcollections().getArtworksByArtCollection(collectionId).execute();
		}
		else if(artistId_aw != null)
			artworks = freya.artists().getArtworksByArtist(artistId_aw).execute();
		else 
			artworks = freya.artworks().list().execute();
	} catch (IOException e) {
		
	}
%>
<body>
<script type="text/javascript">

	// Builds the HTML Table out of myList.
	<%if( artworks != null){%>
	var items_aw = <%=artworks.getItems().toString()%>
	<%}else{%>
	console.log("null pointer at items initilization")
	<%}%>
	var input_aw = {
			"aaData" : items_aw,
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
		var oTable_aw = $('#dtable_artworks').dataTable(input_aw);
		$('.DataTables_sort_wrapper').each(function() {
			$(this).attr("title", $(this).text());
		});
	
		$('#dtable_artworks tr').each(function() {
			var id_aw = $(this).find('td').eq(0).text();
			$(this).find('td').eq(7).attr("data-id", id_aw);
			$(this).find('td').eq(7).html("<a href='../artworks/view.jsp?id=" + id_aw + "'>link</a>");
		});

		// Hide ID column
		oTable_aw.fnSetColumnVis(0, false);
	});
	
	
</script>
	<table id='dtable_artworks' class='dtable' border='1'></table>
</body>
</html>