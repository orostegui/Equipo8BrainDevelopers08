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
		<title>Productos | Tecnoshop</title>
		<link rel="shortcut icon" href="./assets/img/favicon.svg">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@sweetalert2/themes@3.2.0/bootstrap-4/bootstrap-4.css">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs5/dt-1.11.3/datatables.min.css"/>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
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
		          			<a class="nav-link active" href="./productos.jsp">PRODUCTOS</a>
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
		          			<a class="nav-link" href="./reportes.jsp">REPORTES</a>
		        		</li>
        			</ul>
        			<%=usuario%>,&nbsp;<a href="./logout.jsp">cerrar sesión</a>
      			</div>
    		</div>
  		</nav>
  		
  		<div class="container-xl main-content">
			  <div class="row">
			    <div class="col-md-9">
			    	<div class="bg-light table">
			    		<h2>PRODUCTOS</h2>
						<table id="productos" class="display" style="width:100%">
					        <thead>
					            <tr>
					                <th>Cod.</th>
					                <th>Producto</th>
					                <th>N. Proveedor</th>
					                <th>P. Compra</th>
					                <th>% IVA</th>
					                <th>% Utilidad</th>
					                <th>P. Venta(+IVA)</th>
					            </tr>
					        </thead>
					    </table>
				    </div>
				</div>
				<div class="col-md-3">
					<div class="sidebar sidebar-admin">	
                        <p style="font-size:1.4em;margin-top:10px;"><b>Cargar productos</b></p>
						<p>Cargue un archivo .csv con la información de los productos.</p>
						<form id="upload-form">					
							<div class="fileinputs">
							<label for="file-upload" class="file-upload">
								<svg width="1.5em" height="1.5em" viewBox="0 0 16 16" class="bi bi-file-earmark-spreadsheet-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
									<path fill-rule="evenodd" d="M2 2a2 2 0 0 1 2-2h5.293A1 1 0 0 1 10 .293L13.707 4a1 1 0 0 1 .293.707V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm7 2l.5-2.5 3 3L10 5a1 1 0 0 1-1-1zM3 9v1h2v2H3v1h2v2h1v-2h3v2h1v-2h3v-1h-3v-2h3V9H3zm3 3v-2h3v2H6z"/>
								</svg> Seleccionar archivo
							</label>
								<input type="file" name="file-upload" id="file-upload" style="display: none;" accept=".csv">
							</div>
							<div id="info-file"></div>
							<input type="submit" id="upload-document" class="icono btn btn-outline-success" style="min-width:20px;margin-top:20px;" value="SUBIR ARCHIVO"/>		
						</form>
                        <br>
                        <p style="font-size:12px;">Solo son permitidos archivos .csv</p>
                        <hr>
                        <div id="editar" class="edit-product" style="min-width:20px;"/>
                        	<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
								  <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
								  <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
							</svg>
							EDITAR PRODUCTO
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
	<script src="./assets/js/productos.js"></script>
</html>