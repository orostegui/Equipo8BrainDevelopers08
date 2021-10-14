<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	HttpSession sesion = request.getSession();
	Object usuario = (String) session.getAttribute("usuario");
	if(usuario==null){
		response.sendRedirect("./login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Reportes | Tecnoshop</title>
		<link rel="shortcut icon" href="./assets/img/favicon.svg">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@sweetalert2/themes@3.2.0/bootstrap-4/bootstrap-4.css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs5/dt-1.11.3/datatables.min.css"/>
    	<!-- Custom styles for this template -->
    	<link href="./assets/css/main.css" rel="stylesheet">
    </head>
	<body>
		<nav class="navbar">
		  	<div class="container-xl">
		   		<a class="navbar-brand" href="./dashboard.jsp">
		      		<img src="./assets/img/tecnoshop-logo.svg" height="60">
		    	</a>
		  	</div>
		</nav>
		<nav class="navbar navbar-expand-lg navbar-light bg-light no-padding" aria-label="Ninth navbar example">
    		<div class="container-xl">
      			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07XL" aria-controls="navbarsExample07XL" aria-expanded="false" aria-label="Toggle navigation">
        			<span class="navbar-toggler-icon"></span>
      			</button>
      			<div class="collapse navbar-collapse" id="navbarsExample07XL">
        			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
          				<% if(session.getAttribute("rol").equals("1")) { %>
          				<li class="nav-item">
		          			<a class="nav-link" href="./usuarios.jsp">USUARIOS</a>
		        		</li>
          				<% } %>
		        		<li class="nav-item">
		          			<a class="nav-link" href="./clientes.jsp">CLIENTES</a>
		        		</li>
		        		<li class="nav-item">
		          			<a class="nav-link" href="./proveedores.jsp">PROVEEDORES</a>
		        		</li>
		        		<li class="nav-item">
		          			<a class="nav-link" href="./productos.jsp">PRODUCTOS</a>
		        		</li>
		        		<li class="nav-item dropdown">
				          	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
				            	VENTAS
				          	</a>
				          	<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
				          		<li><a class="dropdown-item" href="./ventas.jsp">Ver ventas</a></li>
				            	<li><a class="dropdown-item" href="./nueva-venta.jsp">Nueva Venta</a></li>
				          	</ul>
				        </li>
		        		<li class="nav-item">
		          			<a class="nav-link active" href="./reportes.jsp">REPORTES</a>
		        		</li>
        			</ul>
        			<%=usuario%>,&nbsp;<a href="./logout.jsp">cerrar sesión</a>
      			</div>
    		</div>
  		</nav>
  		
  		<div class="container-xl main-content">
			  <div class="row">
			    <div class="col-md-12">
			    	<div class="bg-light table">
			    		<h1>EN CONSTRUCCIÓN</h1>
				    </div>
				</div>
			</div>
		</div>
		<% } %>
	</body>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9/dist/sweetalert2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/v/bs5/dt-1.11.3/datatables.min.js"></script>
</html>