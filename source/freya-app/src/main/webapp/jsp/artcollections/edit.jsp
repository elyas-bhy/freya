<%@page import="com.appspot.freya_app.freya.model.Response"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	ArtCollection artCollection = null;
	ArtworkCollection artworks = null;
	Long collectionIdLong = null;

	String collectionId = request.getParameter("id");
	Freya freya = new Freya.Builder(new UrlFetchTransport(),
			new GsonFactory(), null).build();
	try {
		artworks = freya.artworks().list().execute();
		artCollection = new ArtCollection();
		if (collectionId != null) {
			if(!collectionId.equals("")) {
				// Collection id was set
				collectionIdLong = Long.parseLong(collectionId);
				artCollection = freya.artcollections().get(collectionIdLong).execute();
				if (artCollection == null) {
					// No collection corresponds to that id, create new collection
					artCollection = new ArtCollection();
				}
			}
			
			String tags, comments, isPublic = null;
			String[] artworksIds = request.getParameterValues("artworks");
			tags = request.getParameter("tags");
			comments = request.getParameter("comments");
			isPublic = request.getParameter("public");
			
			boolean notEmpty = false;
			if (tags != null) {
				String tagArray[] = tags.split(";");
				ArrayList<String> strings = new ArrayList<String>();
				for (String s : tagArray) {
					strings.add(s);
				}
				artCollection.setTags(strings);
				notEmpty = true;
			}
			
			if(comments != null) {
				String commentArray[] = comments.split(";");
				ArrayList<String> strings = new ArrayList<String>();
				for (String s : commentArray) {
					strings.add(s);
				}
				artCollection.setComments(strings);
				notEmpty = true;
			}
			
			if(isPublic != null) {
				if(!isPublic.equals("")) {
					artCollection.setPublic(Boolean.valueOf(isPublic));
				}
			}
			
			if(artworksIds != null) {
				Artwork a = null;
				Response r = freya.artcollections().add(artCollection).execute();
				for(String id : artworksIds) {
					a = freya.artworks().get(id).execute();
					freya.artcollections().addArtworkToArtCollection(Long.parseLong(r.getValue()), a).execute();
				}
				notEmpty = true;
			}
			
			if(notEmpty) {
				freya.artcollections().add(artCollection).execute();
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
			value="<%=(collectionId == null) ? "" : collectionId%>" />
		Artworks: <select class="chosen" name="artworks" multiple>
			<%if(artworks != null) {
				for(Artwork a : artworks.getItems()) { %>
			<option value="<%=a.getId()%>">
				<%=a.getTitle()%>
				</option>
			<%
				}
			}
			%>
		<%
			StringBuffer tagsSb = new StringBuffer();
			StringBuffer commentsSb = new StringBuffer();
			if (artCollection.getTags() != null) {
				for (String s : artCollection.getTags()) {
					tagsSb.append(s);
					tagsSb.append(";");
				}
			}
			
			if(artCollection.getComments() != null) {
				for(String s : artCollection.getComments()) {
					commentsSb.append(s);
					commentsSb.append(";");
				}
			}
		%>
		</select>
		<br /> Comments:
		<textarea rows="5" cols="20" name="comments"><%=(artCollection.getComments() == null) ? "" : commentsSb.toString()%></textarea>
		<br /> Tags:
		<textarea rows="5" cols="20" name="tags"><%=(artCollection.getTags() == null) ? "" : tagsSb.toString()%></textarea>
		<br /> Public: <select name="public">
			<option value="">Select a value</option>
		<% if(artCollection.getPublic() != null) {
			if(artCollection.getPublic()) {
			%> 
			<option value="false">No</option>
			<option value="true" selected="selected">Yes</option>
			<%}
		} else { %>
			<option value="false">No</option>
			<option value="true">Yes</option>
		<%} %>
			</select>
		<br /> <input type="submit" value="Submit" />
	</form>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>