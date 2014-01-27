<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.PhotoCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	PhotoCollection photos = null;
	String artistId_ph = null;
	String artworkId = null;
	try {
		artistId_ph = request.getParameter("artist");
		artworkId = request.getParameter("artwork");
		if(artistId_ph != null)
			photos = freya.artists().getPhotosByArtist(artistId_ph).execute();
		else if (artworkId != null){
			photos = new PhotoCollection();
			Artwork a = freya.artworks().get(artworkId).execute();
			if (a!= null)
			photos.setItems(a.getPhotos());
		}
	if(photos == null){
		response.sendRedirect("../404.jsp");
		return;		
	}
	} catch (IOException e) {
		response.sendRedirect("../404.jsp");
		return;		
	}
%>
<script type="text/javascript">
	// Builds the HTML Table out of myList.
	<%if( photos != null){%>
	var items_ph = <%=photos.getItems().toString()%>
	<%}else{%>
	console.log("null pointer at items initilization")
	<%}%>
	var input_ph = {
			"aaData" : items_ph,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id", "sWidth" : "0%"},
			  { "sTitle" : "Description", "mData" : "desc"},
			  { "sTitle" : "URL", "mData" : "url", "mRender" : function(data, type, val){return "<img src='" + data + "' />";}},
			  { "sTitle" : "Actions"}
		]
	};
	$(document).ready(function() {
		var oTable_ph = $('#dtable_photos').dataTable(input_ph);
		$('.DataTables_sort_wrapper').each(function() {
			$(this).attr("title", $(this).text());
		});
	
		$('#dtable_photos tr').each(function() {
			var id = $(this).find('td').eq(0).text();
			var link = $(this).find('td').eq(2).text();
			var currentURL = encodeURIComponent(window.location);
			$(this).find('td').eq(2).attr("data-id", link);
			$(this).find('td').eq(3).attr("data-id", link);
			$(this).find('td').eq(2).html("<a href='" + link + "'>link</a>");
			$(this).find('td').eq(3).html(
					 "<a class='button' href='"+pwd+"jsp/photos/view.jsp?id=" +id+ "'><img class='btn' src='../../resources/view.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/photos/edit.jsp?id=" +id+ "'><img class='btn' src='../../resources/edit.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/photos/view.jsp?id=" +id+ "&del="+currentURL+"'><img class='btn' src='../../resources/delete.png' alt='' /></a>"
			);
		});

		// Hide ID column
		oTable_ph.fnSetColumnVis(0, false);
	});
	
	
</script>
	<table id='dtable_photos' class='dtable' border='1'></table>