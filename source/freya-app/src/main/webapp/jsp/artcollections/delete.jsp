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
freya.artcollections().delete(collectionId).execute();
String dest = request.getParameter("del");
response.sendRedirect(dest);

%>