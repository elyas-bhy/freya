<%@page import="com.dev.freya.model.ArtTechnique"%>
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
	Artwork artwork= null;
	ArtworkCollection artworks = null;
	String reproductionId = request.getParameter("id");
	Freya freya = new Freya.Builder(new UrlFetchTransport(),
			new GsonFactory(), null).build();
	try {
		artworks = freya.artworks().list().execute();
		reproduction = new Reproduction();
		if (reproductionId != null) {
			// Reproduction id was set, this is an edit
			reproduction = freya.reproductions().get(reproductionId)
					.execute();
			if (reproduction == null) {
				// No reproductions correspond to that id, store data
				reproduction = new Reproduction();
			}
			
			String artworkId = request.getParameter("artwork");
			
			if(artworkId != null) {
				artwork = freya.artworks().get(artworkId).execute();
			}
			
			String stock = request.getParameter("stock");
			String price = request.getParameter("price");
			String support = request.getParameter("support");
			String technique = request.getParameter("technique");
			if (artwork != null && stock != null && price != null
					&& support != null && technique != null) {
				reproduction.setArtworkId(artworkId);
				reproduction.setPrice(Double.valueOf(price));
				reproduction.setStock(Integer.valueOf(stock));
				reproduction.setSupport(support);
				reproduction.setTechnique(technique);

				freya.artworks().addReproductionToArtwork(artwork.getId(), reproduction).execute();
				
				response.sendRedirect("../artworks/view.jsp?id=" + artwork.getId() + "#reproductions");
				return;
			}
		}
	} catch (Exception e) {
		System.out.println(e);
		response.sendRedirect("../404.jsp");
		return;
	}
%>

<div id="container">
	<form action="edit.jsp" method="POST">
		<input type="text" name="id" style="display: none;"
			value="<%=(reproduction.getId() == null) ? "" : reproduction
					.getId()%>" />
		Artwork: <select class="chosen" name="artwork">
			<option value="">Select a value</option>
			<%if(artworks != null) {
				for (Artwork a : artworks.getItems()) {
			%>
			<option value="<%=a.getId()%>"><%=a.getTitle()%></option>
			<%}
			}
			%>
		</select> <br /> Stock: <input type="number" name="stock" value=<%=(reproduction.getStock() != null) ? 0 : reproduction.getStock() %> />
			<br /> Price: <input
			type="number" name="price" value=<%=(reproduction.getPrice() != null) ? 0 : reproduction.getPrice() %>/> <br /> Support:<select class="chosen" name="support">
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
		</select>
		<br />
		Technique:<select class="chosen" name="technique">
			<option value="">Select a value</option>
			<%
				for (ArtTechnique tech : ArtTechnique.values()) {
			%>
			<option value="<%=tech.toString()%>"
				<%if (reproduction.getTechnique() != null) {
					if (tech.toString().equals(reproduction.getTechnique())) {%>
				selected="selected" <%}%> <%}%>>
				<%=tech.toString()%></option>
			<%
				}
			%>
		</select>
		<br /> <input type="submit" value="Submit" />
	</form>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>