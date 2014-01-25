<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.util.List"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	List<String>comments = null;
	String artistId_com = null;
	String collectionId_com = null;
	try {
		artistId_com = request.getParameter("artist");
		collectionId_com = request.getParameter("collection");
		if (collectionId_com != null){
			Long collectionId = Long.parseLong(collectionId_com);
			comments = freya.artcollections().get(collectionId).execute().getComments();
		}
		else if(artistId_com != null)
			comments = freya.artworks().get(artistId_com).execute().getComments();
		if(comments == null) {
			response.sendRedirect("../404.jsp");
			return;
		}
	} catch (IOException e) {
		response.sendRedirect("../404.jsp");
		return;
	}
%>

<body>
<%
out.println("<div class='comment'>");
for (String s : comments){
	out.println("<p>" + s + "</p>");
}
out.println("</div>");%>
</body>