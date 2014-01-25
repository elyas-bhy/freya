<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artist"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%	
	Artist artist = null;
	String artistId = request.getParameter("id");
	Freya freya = new Freya.Builder(new UrlFetchTransport(),
			new GsonFactory(), null).build();
	try {
	artist = freya.artists().get(artistId).execute();
	if(artist == null) {
		response.sendRedirect("../404.jsp");
		return;
	}
	} catch (Exception e) {
		response.sendRedirect("../404.jsp");
		return;
	}
%>
<div id="container">
	<h1>
		Artist:
		<%=artist.getName()%></h1>

	<p id='artistname'></p>

	<div id="tabs">
		<ul>
			<li><a href="#artworks">Artworks</a></li>
			<li><a href="#photos">Photos</a></li>
		</ul>
		<div id="artworks">
			<jsp:include page="../includes/artworks.jsp">
				<jsp:param name="artist" value="${param.id}" />
			</jsp:include>
		</div>
		<div id="photos">
			<jsp:include page="../includes/photos.jsp">
				<jsp:param name="artist" value="${param.id}" />
			</jsp:include>
		</div>
	</div>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>