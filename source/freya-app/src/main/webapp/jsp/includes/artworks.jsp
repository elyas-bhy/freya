<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtworkCollection artworks = null;
	ArtCollection collec = null;
	String artistId_aw = null;
	String collection = null;
	try {
		artistId_aw = request.getParameter("artist");
		collection = request.getParameter("collection");
		if (collection != null){
			Long collectionId = Long.parseLong(collection);
			artworks = freya.artcollections().getArtworksByArtCollection(collectionId).execute();
			collec = freya.artcollections().get(collectionId).execute();
		}
		else if(artistId_aw != null)
			artworks = freya.artists().getArtworksByArtist(artistId_aw).execute();
		else 
			artworks = freya.artworks().list().execute();
	} catch (IOException e) {
		
	}
%>

<script type="text/javascript">
	// Builds the HTML Table out of myList.
	<%if( artworks != null){%>
	var items_aw = <%=artworks.getItems().toString()%>
	<%}else{%>
	var items_aw = {}
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
		      { "sTitle" : "Photos", "mData" : "photos"},
		      { "sTitle" : "Reproductions", "mData" : "reproductions"},
		      { "sTitle" : "Actions"}
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
			$(this).find('td').eq(8).attr("data-id", id_aw);
			$(this).find('td').eq(9).attr("data-id", id_aw);
			$(this).find('td').eq(7).html("<a href='../artworks/view.jsp?id=" + id_aw + "#photos'>link</a>");
			$(this).find('td').eq(8).html("<a href='../artworks/view.jsp?id=" + id_aw + "#reproductions'>link</a>");
			<%if (collection == null){%>
			var currentURL = encodeURIComponent(window.location);
			$(this).find('td').eq(9).html(
					 "<a class='button' href='"+pwd+"jsp/artworks/view.jsp?id=" +id_aw+ "'><img class='btn' src='../../resources/view.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artworks/edit.jsp?id=" +id_aw+ "'><img class='btn' src='../../resources/edit.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artworks/view.jsp?id=" +id_aw+ "&del="+currentURL+"'><img class='btn' src='../../resources/delete.png' alt='' /></a>"
			);
			<%}else{%>
			$(this).find('td').eq(9).html(
					 "<a class='button' href='"+pwd+"jsp/artworks/view.jsp?id=" +id_aw+ "'><img class='btn' src='../../resources/view.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artworks/edit.jsp?id=" +id_aw+ "'><img class='btn' src='../../resources/edit.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artworks/edit.jsp?id=" +id_aw+ "'><img class='btn' src='../../resources/no.png' alt='' /></a>"
			);
			<%}%>
		});

		// Hide ID column
		oTable_aw.fnSetColumnVis(0, false);
	});
	
	
</script>
	<table id='dtable_artworks' class='dtable' border='1'></table>