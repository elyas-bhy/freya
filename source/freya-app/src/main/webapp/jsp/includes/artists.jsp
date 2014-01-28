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
	<%if(artists != null) {%>
	var items = <%=artists.getItems().toString()%>;
	<%}else{%>
	var items = {};
	<%}%>
	var input = {
			"aaData" : items,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id", "sWidth" : "0%"},
			  { "sTitle" : "Artist name", "mData" : "name", },
			  { "sTitle" : "Artworks", "mData" : "name"},
			  { "sTitle" : "Photos", "mData" : "name"},
			  { "sTitle" : "Actions"},
			]
		};

	$(document).ready(function() {
		var oTable = $('#dtable').dataTable(input);
		$('.DataTables_sort_wrapper').each(function() {
			$(this).attr("title", $(this).text());
		});
	
		$('#dtable tr').each(function() {
			var id = $(this).find('td').eq(0).text();
			var currentURL = encodeURIComponent(window.location);
			$(this).find('td').eq(2).attr("data-id", id);
			$(this).find('td').eq(3).attr("data-id", id);
			$(this).find('td').eq(4).attr("data-id", id);


			$(this).find('td').eq(2).html("<a href='view.jsp?id=" + id + "'>link</a>");
			$(this).find('td').eq(3).html("<a href='view.jsp?id=" + id + "'>link</a>");
			$(this).find('td').eq(4).html(
					 "<a class='button' href='"+pwd+"jsp/artists/view.jsp?id=" +id+ "'><img class='btn' src='../../resources/view.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artists/edit.jsp?id=" +id+ "'><img class='btn' src='../../resources/edit.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artists/view.jsp?id=" +id+ "&del="+currentURL+"'><img class='btn' src='../../resources/delete.png' alt='' /></a>"
			);

		});

		// Hide ID column
		oTable.fnSetColumnVis(0, false);
	});
	
	
</script>
	<table id='dtable' class='dtable' border='1'></table>