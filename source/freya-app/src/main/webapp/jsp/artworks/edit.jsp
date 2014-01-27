<%@page import="com.appspot.freya_app.freya.model.Response"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.appspot.freya_app.freya.model.Dimension"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artist"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.appspot.freya_app.freya.model.Photo"%>
<%@ page import="com.appspot.freya_app.freya.model.Reproduction"%>
<%@ page import="com.dev.freya.model.ArtSupport"%>
<%@ page import="com.dev.freya.model.ArtTechnique"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	Artwork artwork = null;
	Artist artist = null;
	String artistName = null;
	String artworkId = request.getParameter("id");
	Freya freya = new Freya.Builder(new UrlFetchTransport(),
			new GsonFactory(), null).build();
	try {
		artwork = new Artwork();
		if (artworkId != null) {
			// Artwork id was set
			artwork = freya.artworks().get(artworkId).execute();
			if (artwork == null) {
				// No artwork corresponds to that id, create new artwork
				artwork = new Artwork();
			} else {
				artist = artwork.getArtist();
			}

			String title, support, technique, date, summary, tags, comments = null;
			artistName = request.getParameter("artist");
			title = request.getParameter("title");
			support = request.getParameter("support");
			technique = request.getParameter("technique");
			date = request.getParameter("date");
			summary = request.getParameter("summary");
			tags = request.getParameter("tags");
			comments = request.getParameter("comments");
			

			if (tags != null) {
				String tagArray[] = tags.split(";");
				ArrayList<String> strings = new ArrayList<String>();
				for (String s : tagArray) {
					strings.add(s);
				}
				artwork.setTags(strings);
			}
			
			if(comments != null) {
				String commentArray[] = comments.split(";");
				ArrayList<String> strings = new ArrayList<String>();
				for (String s : commentArray) {
					strings.add(s);
				}
				artwork.setComments(strings);
			}

			if (artistName != null && title != null && support != null
					&& technique != null && date != null
					&& summary != null) {
				Dimension d = new Dimension();
				d.setX(Float.valueOf(request
						.getParameter("dimension_x")));
				d.setY(Float.valueOf(request
						.getParameter("dimension_y")));
				d.setZ(Float.valueOf(request
						.getParameter("dimension_z")));
				artist = new Artist(artistName);
				artwork.setTitle(title);
				artwork.setSupport(support);
				artwork.setTechnique(technique);
				artwork.setDate(date);
				artwork.setDimension(d);
				artwork.setSummary(summary);

				Response r = freya.artworks().add(artwork).execute();
				freya.artworks()
						.addArtistToArtwork(r.getValue(), artist)
						.execute();
				response.sendRedirect("list.jsp");
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
		<input type="text" name="id" style="display: none"
			value="<%=(artworkId == null) ? "" : artworkId%>" /> Artist: <input
			type="text" name="artist"
			value="<%=(artist == null) ? "" : artist.getName()%>" /> <br />
		Title: <input type="text" name="title"
			value="<%=(artwork.getTitle() == null) ? "" : artwork.getTitle()%>" />
		<br /> Support: <select class="chosen" name="support">
			<option value="">Select a value</option>
			<%
				for (ArtSupport sup : ArtSupport.values()) {
			%>
			<option value="<%=sup.toString()%>"
				<%if (artwork.getSupport() != null) {
					if (sup.toString().equals(artwork.getSupport())) {%>
				selected="selected" <%}%> <%}%>>
				<%=sup.toString()%></option>
			<%
				}
			%>
		</select> <br /> Technique: <select class="chosen" name="technique">
			<option value="">Select a value</option>
			<%
				for (ArtTechnique tech : ArtTechnique.values()) {
			%>
			<option value="<%=tech.toString()%>"
				<%if (artwork.getTechnique() != null) {
					if (tech.toString().equals(artwork.getTechnique())) {%>
				selected="selected" <%}%> <%}%>>
				<%=tech.toString()%></option>
			<%
				}
			%>
		</select> <br /> Date: <input id="datepicker" type="date" name="date"
			value="<%=(artwork.getDate() == null) ? "" : artwork.getDate()%>" />
		<br /> Dimensions: <input type="number" name="dimension_x"
			value="<%=(artwork.getDimension() == null) ? "" : artwork
					.getDimension().getX()%>" />
		<input type="number" name="dimension_y"
			value="<%=(artwork.getDimension() == null) ? "" : artwork
					.getDimension().getY()%>" />
		<input type="number" name="dimension_z"
			value="<%=(artwork.getDimension() == null) ? "" : artwork
					.getDimension().getZ()%>" />
		<br /> Summary:
		<textarea rows="5" cols="20" name="summary">
				<%=(artwork.getSummary() == null) ? "" : (artwork
					.getSummary().equals("")) ? "" : artwork.getSummary()%>
			</textarea>
		<%
			StringBuffer tagsSb = new StringBuffer();
			StringBuffer commentsSb = new StringBuffer();
			if (artwork.getTags() != null) {
				for (String s : artwork.getTags()) {
					tagsSb.append(s);
					tagsSb.append(";");
				}
			}
			
			if(artwork.getComments() != null) {
				for(String s : artwork.getComments()) {
					commentsSb.append(s);
					commentsSb.append(";");
				}
			}
		%>
		<br /> Comments:
		<textarea rows="5" cols="20" name="comments"><%=(artwork.getComments() == null) ? "" : commentsSb.toString()%></textarea>
		<br /> Tags:
		<textarea rows="5" cols="20" name="tags"><%=(artwork.getTags() == null) ? "" : tagsSb.toString()%></textarea>
		<br /> <input type="submit" value="Submit" />
	</form>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>