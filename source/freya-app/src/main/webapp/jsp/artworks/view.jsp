<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>
<%	
String artworkId = request.getParameter("id");
%>
<jsp:include page="../includes/reproductions.jsp">
	<jsp:param name="artist" value="<%=artworkId%>" />
</jsp:include>
