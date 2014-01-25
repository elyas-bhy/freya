<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>
<%	
String artworkId = request.getParameter("id");
Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
Artwork artwork = freya.artworks().get(artworkId).execute();
%>

<html>
<h1>Artwork : <%=artwork.getTitle()%></h1>

<p><%=artwork.getSummary() %></p>

<h2> Date of creation: </h2>
<p><%=artwork.getDate() %></p>

<h2> Support Used: </h2>
<p><%=artwork.getSupport() %></p>

<h2> Technique: </h2>
<p><%=artwork.getTechnique() %></p>

<h2> Dimensions: </h2>
<p>Width : <%=artwork.getDimension().getX() %></p>
<p>Height : <%=artwork.getDimension().getY() %></p>
<p>Depth : <%=artwork.getDimension().getZ() %></p>
</html>

<h2> Reproductions: </h2>
<jsp:include page="../includes/reproductions.jsp">
	<jsp:param name="artist" value="${param.id}" />
</jsp:include>
