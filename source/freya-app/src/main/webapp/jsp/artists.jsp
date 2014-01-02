<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtistCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<html>
<body>
	<%
		Freya freya = new Freya.Builder(new UrlFetchTransport(),new GsonFactory(), null).build();
		ArtistCollection collec = freya.artists().list().execute();
	%>
	<p><%= collec.toPrettyString() %></p>


</body>
</html>