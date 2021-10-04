<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	HttpSession sesion = request.getSession();
	sesion.invalidate();
	response.sendRedirect("./login.jsp");
%>