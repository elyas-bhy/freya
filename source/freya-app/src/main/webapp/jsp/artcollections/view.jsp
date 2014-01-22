<%@page import="com.google.appengine.repackaged.com.google.protobuf.ByteString.Output"%>
<%@page import="java.io.Console"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtCollection collection = null;
	ArtworkCollection artworks = null;
	try {
		String collectionString = request.getParameter("id");
		Long collectionId = Long.parseLong(collectionString);
		collection = freya.artcollections().get(collectionId).execute();
	} catch(Exception e) {
	}
%>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$('#tabs').tabs();
	});
</script>
<body>
	<h2>Collection <%=request.getParameter("id") %></h2>
	<div id="tabs">
		<ul>
			<li><a href="#artworks">Artworks</a></li>
			<li><a href="#comments">Comments</a></li>
			<li><a href="#tags">Tags</a></li>
		</ul>
		<div id="artworks">
			<jsp:include page="../includes/artworks.jsp">
    		<jsp:param name="collection" value="${param.id}"/>
    		</jsp:include>
		</div>
		<div id="comments">
			<%
			if(collection.getComments() != null) {
				for(String comment : collection.getComments()) {
					out.println("<p>" + comment + "</p>");
				}
			}
			%>
		</div>
		<div id="tags">
			<%
			if(collection.getTags() != null) {
				for(String tag : collection.getTags()) {
					out.println("<p>" + tag + "</p>");
				}
			}	
			%>
		</div>
	</div>
</body>
</html>