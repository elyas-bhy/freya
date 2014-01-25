<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artist"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
String artistId = request.getParameter("id");
Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
Artist artist = freya.artists().get(artistId).execute();
%>

<html>
<h1>Artist: <%=artist.getName()%></h1>
</html>

<p id='artistname'></p>

<html>
<h2>Artworks</h2>
</html>
<p id='artworks'></p>
<jsp:include page="../includes/artworks.jsp">
	<jsp:param name="artist" value="${param.id}" />
</jsp:include>

<html>
<h2>Photos</h2>
</html>
<p id='photos'></p>
<jsp:include page="../includes/photos.jsp">
	<jsp:param name="artist" value="${param.id}" />
</jsp:include>