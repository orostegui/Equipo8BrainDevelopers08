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
		<title>Nueva Venta | Tecnoshop</title>
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
		        		<li class="nav-item dropdown">
				          	<a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
				<div class="col-md-4">
					<div class="bg-light contenido">
						<div class="row">
							<h2>DATOS DEL CLIENTE</h2>
						    <div class="col-md-12 mb-3">
								<select class="form-select form-select-lg" id="selectCliente">
									<option selected value="0">Seleccione un cliente</option>
								</select>
						    </div>
						   	<div class="col-md-6 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="cc" placeholder="N° Cédula" disabled>
									<label for="floatingInput">N° Cédula</label>
								</div>
						    </div>
						    <div class="col-md-6 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="dir" placeholder="Dirección" disabled>
									<label for="floatingInput">Dirección</label>
								</div>
						    </div>
						    <div class="col-md-6 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="email" placeholder="Email" disabled>
									<label for="floatingInput">Email</label>
								</div>
						    </div>
						    <div class="col-md-6 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="tel" placeholder="Teléfono" disabled>
									<label for="floatingInput">Teléfono</label>
								</div>
						    </div>
						    <div class="col-md-6 mb-3">
						    	<div class="form-floating">
									<input type="text" class="form-control" id="vendedor" placeholder="Vendedor" disabled value="<%=nombre%>">
									<label for="floatingInput">Vendedor</label>
								</div>
						    </div>
						</div>
					</div>
				</div>
				
				<div class="col-md-8">
					<div class="bg-light table">
						<h2>DETALLE VENTA</h2>
						<table id="productos" class="table table-bordered" style="width:100%">
					        <thead>
					            <tr>
					                <th>#</th>
					                <th>Producto</th>
					                <th>Cantidad</th>
					                <th class="text-end">Precio Unidad(+IVA)</th>
					                <th class="text-end" style="max-width:60px;">% IVA</th>
					                <th class="text-end" style="max-width:160px;">Precio Total(+IVA)</th>
					                <th></th>
					            </tr>
					        </thead>
					        <tbody id="table-body">
					        	<tr>
							    </tr>
							</tbody>
					        <tfoot>
					        	<tr>
							        <th colspan="5" class="text-end">SUBTOTAL</th>
							        <td id="subtotal" class="text-end">$ 0</td>
							    </tr>
							    <tr>
							        <th colspan="5" class="text-end">IVA</th>
							        <td id="iva-venta" class="text-end">$ 0</td>
							    </tr>
							    <tr>
							        <th colspan="5" class="text-end">TOTAL A PAGAR</th>
							        <td id="total-pagar" class="text-end">$ 0</td>
							    </tr>
							</tfoot>
					    </table>
					    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
							<button type="button" class="btn btn-primary" id="agregar">
						    	<i class="bi bi-plus-circle"></i>A. Producto
							</button>
  							<button type="button" class="btn btn-success" id="registrar">
						    	<i class="bi bi-file-earmark-check"></i>Registrar
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<% } %>
	</body>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/v/bs5/dt-1.11.3/datatables.min.js"></script>
    <script src="./assets/js/nueva-venta.js"></script>
</html>