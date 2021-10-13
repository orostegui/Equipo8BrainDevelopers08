<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	HttpSession sesion = request.getSession();
	Object usuario = (String) session.getAttribute("usuario");
	Object nombre = (String) session.getAttribute("nombre");
	if(usuario==null){
		response.sendRedirect("./login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Ventas | Tecnoshop</title>
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
		          			<a class="nav-link" href="./productos.jsp">PRODUCTOS</a>
		        		</li>
		        		<li class="nav-item">
		          			<a class="nav-link active" href="./ventas.jsp">VENTAS</a>
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
				<div class="col-md-12">
					<div class="bg-light contenido">
						<div class="row">
							<h2>NUEVA FACTURA <span style="font-size:14px">(Si desea agregar un cliente nuevo, de click <a href="./clientes.jsp">aquí</a>)</span></h2>
						    <div class="col-md-4 mb-3">
								<select class="form-select form-select-lg" id="cc_cliente">
									<option selected value="0">Seleccione un cliente</option>
								</select>
						    </div>
						   	<div class="col-md-4 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="cc" placeholder="N° Cédula" disabled>
									<label for="floatingInput">N° Cédula</label>
								</div>
						    </div>
						    <div class="col-md-4 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="dir" placeholder="Dirección" disabled>
									<label for="floatingInput">Dirección</label>
								</div>
						    </div>
						    <div class="col-md-4 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="email" placeholder="Email" disabled>
									<label for="floatingInput">Email</label>
								</div>
						    </div>
						    <div class="col-md-4 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="tel" placeholder="Teléfono" disabled>
									<label for="floatingInput">Teléfono</label>
								</div>
						    </div>
						    <div class="col-md-4 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="vendedor" placeholder="Vendedor" disabled value="<%=nombre%>">
									<label for="floatingInput">Vendedor</label>
								</div>
						    </div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 mt-3">
					<div class="bg-light table">
						<table id="productos" class="display mb-3" style="width:100%">
					        <thead>
					            <tr>
					                <th>#</th>
					                <th>Producto</th>
					                <th>Cantidad</th>
					                <th>Precio Unidad</th>
					                <th>Precio Total</th>
					            </tr>
					        </thead>
					    </table>
					    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
							<button type="button" class="btn btn-primary">
						    	<i class="bi bi-plus-circle"></i>&nbsp;Anadir producto
							</button>
  							<button type="button" class="btn btn-success">
						    	<i class="bi bi-save"></i>&nbsp;Guardar factura
							</button>
						</div>
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
    <script src="./assets/js/ventas.js"></script>
</html>