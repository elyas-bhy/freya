<%@page import="com.dev.freya.model.ArtSupport"%>
<%@page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.appspot.freya_app.freya.model.Reproduction"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	Reproduction reproduction = null;
	ArtworkCollection artworks = null;
	String reproductionId = request.getParameter("id");
	Freya freya = new Freya.Builder(new UrlFetchTransport(),
			new GsonFactory(), null).build();
	try {
		if (reproductionId != null) {
			// Reproduction id was set, this is an edit
			reproduction = freya.reproductions().get(reproductionId)
					.execute();
			if (reproduction == null) {
				// No reproductions correspond to that id, store data
				reproduction = new Reproduction();
				artworks = freya.artworks().list().execute();
				String artwork = request.getParameter("artowkr");
				String stock = request.getParameter("stock");
				String price = request.getParameter("price");
				String support = request.getParameter("support");
				if (artwork != null && stock != null && price != null
						&& support != null) {
					reproduction.setArtworkId(artwork);
					reproduction.setPrice(Double.valueOf(price));
					reproduction.setStock(Integer.valueOf(stock));
					reproduction.setSupport(support);
					freya.artworks().addReproductionToArtwork(artwork, reproduction);
					
					response.sendRedirect("list.jsp");
					return;
				}
			}
		}
		reproduction = new Reproduction();
	} catch (Exception e) {
		response.sendRedirect("../404.jsp");
		return;
	}
%>

<div id="container">
	<form action="edit.jsp" method="POST">
		<input type="text" name="id" style="display: none;"
			value="<%=(reproduction.getId() == null) ? "" : reproduction
					.getId()%>" />
		Artwork: <select name="artwork">
			<option value="">Select a value</option>
			<%
				for (Artwork a : artworks.getItems()) {
			%>
			<option value="<%=a.getId()%>"><%=a.getTitle()%></option>
			<%
				}
			%>
		</select> <br /> Stock: <input type="number" name="stock" /> <br /> Price: <input
			type="number" name="price" /> <br /> Support:<select name="support">
			<option value="">Select a value</option>
			<%
				for (ArtSupport sup : ArtSupport.values()) {
			%>
			<option value="<%=sup.toString()%>"
				<%if (reproduction.getSupport() != null) {
					if (sup.toString().equals(reproduction.getSupport())) {%>
				selected="selected" <%}%> <%}%>>
				<%=sup.toString()%></option>
			<%
				}
			%>
		</select> <input type="submit" value="Submit" />
	</form>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>