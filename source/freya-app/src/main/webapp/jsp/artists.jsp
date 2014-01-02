<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException" %>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtistCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<html>
<body>
	<%
		Freya freya = new Freya.Builder(new UrlFetchTransport(),new GsonFactory(), null).build();
		Artwork a = null;
		try {
			a = freya.artworks().get("aglmcmV5YS1hcHByFAsSB0FydHdvcmsYgICAgICAwAgM").execute();
		} catch (IOException e) {
	%>
			Internal server error:
			<p><%= a.toString()%></p>
	<%
		}
	%>
	<p><%= a.toString()%></p>


</body>
</html>