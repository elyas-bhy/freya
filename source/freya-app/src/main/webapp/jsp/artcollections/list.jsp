<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page
	import="com.appspot.freya_app.freya.model.ArtCollectionCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page
	import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<%
	Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
	ArtCollectionCollection collections = null;
	try {
		collections = freya.artcollections().list().execute();
	} catch(Exception e) {
	}
%>

<div id="container">
	<div>
		<a style="float: right;" class="button new" href="edit.jsp">New ArtCollections</a>
	</div>
	<jsp:include page="../includes/artcollections.jsp"></jsp:include>
</div>
<jsp:include page="../includes/footer.jsp"></jsp:include>