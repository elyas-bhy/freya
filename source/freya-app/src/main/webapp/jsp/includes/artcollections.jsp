<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollectionCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtCollectionCollection artCollection = null;
	try {
		artCollection = freya.artcollections().list().execute();
	} catch (IOException e) {
		
	}
%>

<script type="text/javascript">
	// Builds the HTML Table out of myList.
	<%if(artCollection != null) {%>
	var items = <%=artCollection.getItems().toString()%>;
	<%}else{%>
	console.log("null pointer at items initialization");
	<%}%>
	var input = {
			"aaData" : items,
			"bJQueryUI": true,
		    "sPaginationType": "full_numbers",
			"aoColumns" : [ 
			  { "sTitle" : "ID", "mData": "id", "sWidth" : "0%"},
			  { "sTitle" : "Public", "mData" : "public", "mRender" : function(data, type, val) {return (data == false) ? "No" : "Yes";}},
			  { "sTitle" : "Artworks", "mData" : "artworks"},
			  { "sTitle" : "Comments", "mData" : "comments"},
			  { "sTitle" : "Tags", "mData" : "tags"},
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
			$(this).find('td').eq(4).html("<a href='view.jsp?id=" + id + "'>link</a>");
			$(this).find('td').eq(5).html(
					 "<a class='button' href='"+pwd+"jsp/artcollections/view.jsp?id=" +id+ "'><img class='btn' src='../../resources/view.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artcollections/edit.jsp?id=" +id+ "'><img class='btn' src='../../resources/edit.png' alt='' /></a>"
					+"<a class='button' href='"+pwd+"jsp/artcollections/view.jsp?id=" +id+ "&del="+currentURL+"'><img class='btn' src='../../resources/delete.png' alt='' /></a>"
			);

		});

		// Hide ID column
		oTable.fnSetColumnVis(0, false);
	});
	
	
</script>
	<table id='dtable' class='dtable' border='1'></table>