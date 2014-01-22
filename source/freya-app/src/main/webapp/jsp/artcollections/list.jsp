<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollectionCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtCollectionCollection collections = null;
	try {
		collections = freya.artcollections().list().execute();
	} catch(Exception e) {
	}
	
%>

<body>
<script type="text/javascript">

	$(document).ready(function() {
		var oTable = $('#dtable').dataTable();
		
		$('.collectionRow').click(function() {
			var elements = $('.' + $(this).data('id'));
			if(elements.first().is(":visible")) {
				$('.' + $(this).data('id')).show('slow');
			} else {
				$('.' + $(this).data('id')).hide('slow');
			}
		});
	});
	
	
</script>
	<table id="dtable">
		<thead>
			<tr>
				<th>id</th>
				<th>isPublic</th>
				<th>Artworks</th>
				<th>Comments</th>
				<th>Tags</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (ArtCollection ac : collections.getItems()) {
			%>
			<tr class="collectionRow" data-id="<%=ac.getId()%>">
				<td><%=ac.getId()%></td>
				<td><%=ac.getPublic()? "Yes" : "No" %></td>
				<td><a href="/jsp/artcollections/view.jsp?id=<%=ac.getId()%>&#artworks">Artworks</a></td>
				<td><a href="/jsp/artcollections/view.jsp?id=<%=ac.getId()%>&#comments">Comments</a></td>
				<td><a href="/jsp/artcollections/view.jsp?id=<%=ac.getId()%>&#tags">Tags</a></td>
				<td><a href="">Edit</a></td>
				<td><a href="">Delete</a></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>