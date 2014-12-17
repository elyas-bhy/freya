<%@page import="com.appspot.freya_app.freya.model.Response"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.appspot.freya_app.freya.model.Photo"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
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
		if (artworkId == null) {
			// No artwork id, no edit redirect
			response.sendRedirect("../404.jsp");
			return;
		}
		// Artwork id was set, this is and edit
		artwork = freya.artworks().get(artworkId).execute();
		if (artwork == null) {
			// No artwork corresponds to that id
			response.sendRedirect("../404.jsp");
			return;
		}
		String desc = request.getParameter("desc");
		String uri = request.getParameter("uri");
		if(uri != null) {
			Photo p = new Photo();
			p.setUrl(uri);
			if(desc != null) {
				p.setDesc(desc);
			}
			Response r = freya.artworks().addPhotoToArtwork(artwork.getId(), p).execute();
			response.sendRedirect("../artworks/view.jsp?id=" + artworkId + "#photos");
			return;
		}
	} catch (Exception e) {
		System.out.println(e);
		response.sendRedirect("../404.jsp");
		return;
	}
%>

<div id="container">
	<form action="edit.jsp" method="POST">
		<input type="text" name="id" style="display:none;" value="<%=(artwork.getId() == null) ? "" : artwork.getId() %>"/>
		Description: <textarea rows="5" cols="20" name="desc"></textarea><br />
		URL: <input type="text" name="uri" /><br />
		
		<input type="submit"value="Submit" />
	</form>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>