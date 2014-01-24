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
String reproductionId = request.getParameter("id");
Freya freya = new Freya.Builder(new UrlFetchTransport(), new GsonFactory(), null).build();
Reproduction reproduction = freya.reproductions().get(reproductionId).execute();
Artwork artwork;
//TODO request to get artwork from reproduction
%>

<html>
<h1>Reproduction from: <%=artwork.getTitle()%></h1>

<h2> Stock: </h2>
<p><%=reproduction.getStock() %></p>

<h2> Support Used: </h2>
<p><%=reproduction.getSupport() %></p>

<h2> Price: </h2>
<p><%=reproduction.getPrice() %></p>
