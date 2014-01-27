<%@page import="org.apache.taglibs.standard.tag.common.core.CatchTag"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>
<%
	Artwork artwork = null;
	String artworkId = request.getParameter("id");
	Freya freya = new Freya.Builder(new UrlFetchTransport(),
	new GsonFactory(), null).build();
	try {
	artwork = freya.artworks().get(artworkId).execute();
	if (artwork == null) {
		response.sendRedirect("../404.jsp");
		return;
	}
	}catch(Exception e) {
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
			var url = decodeURIComponent(del);
			window.location = url;
		}
	}
});
</script>

<div id="container">
<div class="content_listing">
<h1>
	Artwork :
	<%=artwork.getTitle()%></h1>

<p><%=artwork.getSummary()%></p>

<h2>Date of creation:</h2>
<p><%=artwork.getDate()%></p>

<h2>Support Used:</h2>
<p><%=artwork.getSupport()%></p>

<h2>Technique:</h2>
<p><%=artwork.getTechnique()%></p>

<h2>Dimensions:</h2>
<p>
	Width:
	<%=artwork.getDimension().getX()%></p>
<p>
	Height:
	<%=artwork.getDimension().getY()%></p>
<p>
	Depth:
	<%=artwork.getDimension().getZ()%></p>
</div>

<div id="tabs">
<ul>
			<li><a href="#photos">Photos</a></li>
			<li><a href="#reproductions">Reproductions</a></li>
		</ul>
<div id="photos">
<a class="button new" href="../photos/edit.jsp?id=<%=artwork.getId() %>">New Photo</a>
<jsp:include page="../includes/photos.jsp">
	<jsp:param name="artist" value="${param.id}" />
</jsp:include>
</div>

<div id="reproductions">
<a class="button new" href="../reproductions/edit.jsp">New Reproduction</a>
<jsp:include page="../includes/reproductions.jsp">
	<jsp:param name="artist" value="${param.id}" />
</jsp:include>
</div>
</div>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>