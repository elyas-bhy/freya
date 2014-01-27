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
		del = del + ${param.del};
	<%}%>
	if(del != ""){
		var answer = confirm("Are you sure you want to delete this item?");
		if(answer){
			var url = decodeURIComponent(del);
			console.log("going to " + url)
			window.location = url;
		}
	}
});
</script>

<div id="container">
<div class="content_listing">

	Artwork: <%=artwork.getTitle()%>
	<br />
	Summary: <%=artwork.getSummary()%>
	<br />
	Creation date: <%=artwork.getDate()%>
	<br />
	Support Used: <%=artwork.getSupport()%>
	<br />
	Technique: <%=artwork.getTechnique()%>
	<br />
	Dimensions:<br />
	&nbsp;Width: <%=artwork.getDimension().getX()%><br />
	&nbsp;Height: <%=artwork.getDimension().getY()%><br />
	&nbsp;Depth: <%=artwork.getDimension().getZ()%><br />
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