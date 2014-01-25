<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artist"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
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
		if (artistId == null) {
			// No artist id, no edit redirect
			response.sendRedirect("../404.jsp");
			return;
		}
		// Artist id was set, this is and edit
		artist = freya.artists().get(artistId).execute();
		if (artist == null) {
			// No artist corresponds to that id, redirect to 404
			response.sendRedirect("../404.jsp");
			return;
		}
		String name = request.getParameter("name");
		if(name != null) {
			artist.setName(name);
		}
	} catch (Exception e) {
		response.sendRedirect("../404.jsp");
		return;
	}
%>

<div id="container">
	<form action="edit.jsp" method="POST">
		<input type="text" name="id" style="display:none;" value="<%=(artist.getId() == null) ? "" : artist.getId() %>"/>
		Name: <input type="text" name="name" value="<%=(artist.getName() == null)? "" : artist.getName() %>" placeholder="please input a name"/>
		
		<input type="submit"value="Submit" />
	</form>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>