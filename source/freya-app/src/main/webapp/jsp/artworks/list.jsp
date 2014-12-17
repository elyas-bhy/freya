<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>

<div id="container">
	<div>
		<a style="float: right;" class="button new" href="edit.jsp">New Artwork</a>
	</div>
<jsp:include page="../includes/artworks.jsp"></jsp:include>
</div>

<jsp:include page="../includes/footer.jsp"></jsp:include>
