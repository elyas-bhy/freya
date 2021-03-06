<%@page	import="com.google.appengine.repackaged.com.google.protobuf.ByteString.Output"%>
<%@page import="java.io.Console"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtCollection collection = null;
	ArtworkCollection artworks = null;
	Long collectionId;
	try {
		String collectionString = request.getParameter("id");
		collectionId = Long.parseLong(collectionString);
		collection = freya.artcollections().get(collectionId).execute();
		if(collection == null) {
			response.sendRedirect("../404.jsp");
			return;
		}
	} catch(Exception e) {
		response.sendRedirect("../404.jsp");
		return;
	}
%>
<script>
$(document).ready(function(){
	var del = "";
	<% if(request.getParameter("del") != null) { %>
		del = del + "${param.del}";
	<%}%>
	if(del != ""){
		var answer = confirm("Are you sure you want to delete this item?");
		if(answer){
			var url = "delete.jsp?id="+"${param.id}"+"&del="+"${param.del}";
			window.location = url;
		}
	}
});
</script>
<div id="container">
	<h2>
		Collection
		<%=request.getParameter("id")%></h2>
	<div id="tabs">
		<ul>
			<li><a href="#artworks">Artworks</a></li>
			<li><a href="#comments">Comments</a></li>
			<li><a href="#tags">Tags</a></li>
		</ul>
		<div id="artworks">
			<jsp:include page="../includes/artworks.jsp">
				<jsp:param name="collection" value="${param.id}" />
			</jsp:include>
		</div>
		<div id="comments">
			<jsp:include page="../includes/comments.jsp">
				<jsp:param name="collection" value="${param.id}" />
			</jsp:include>
		</div>
		<div id="tags">
			<%
				if (collection.getTags() != null) {
					for (String tag : collection.getTags()) {
						out.println("<p>" + tag + "</p>");
					}
				}
			%>
		</div>
	</div>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>