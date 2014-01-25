<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.appspot.freya_app.freya.Freya"%>
<%@ page import="com.appspot.freya_app.freya.model.ArtworkCollection"%>
<%@ page import="com.appspot.freya_app.freya.model.Artwork"%>
<%@ page import="com.appspot.freya_app.freya.model.Reproduction"%>
<%@ page import="com.google.api.client.extensions.appengine.http.UrlFetchTransport"%>
<%@ page import="com.google.api.client.json.gson.GsonFactory"%>
<jsp:include page="../includes/header.jsp"></jsp:include>
<%	
Reproduction reproduction = null;
Artwork artwork = null;
String reproductionId = request.getParameter("id");
Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
try {
reproduction = freya.reproductions().get(reproductionId).execute();
artwork = null;
if(reproduction == null) {
	response.sendRedirect("../404.jsp");
	return;
}
} catch (Exception e) {
	response.sendRedirect("../404.jsp");
	return;
}
//TODO request to get artwork from reproduction
%>

<%
if(artwork != null) {
%>
<h1>Reproduction from: <%=artwork.getTitle()%></h1>
<%} else {%>
<h1>Unattached Reproduction</h1>
<%} %>
<h2> Stock: </h2>
<p><%=reproduction.getStock() %></p>

<h2> Support Used: </h2>
<p><%=reproduction.getSupport() %></p>

<h2> Price: </h2>
<p><%=reproduction.getPrice() %></p>