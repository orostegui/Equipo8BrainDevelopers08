<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	HttpSession sesion = request.getSession();
	if(session.getAttribute("usuario")!=null){
		response.sendRedirect("./usuarios.jsp");
	}

%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<title>Tecnoshop - Iniciar sesion</title>
		<link rel="shortcut icon" href="./assets/img/favicon.svg">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">	
    	<!-- Custom styles for this template -->
    	<link href="./assets/css/login.css" rel="stylesheet">
    </head>
    <body class="text-center">
    	
    	<main class="form-signin">
		  	<form id="form" action="Login" method="post">
		    	<img class="mb-4" src="./assets/img/tecnoshop-logo.svg" alt="">
		    	<h1 class="h3 mb-4 fw-normal">INICIAR SESIÓN</h1>
		
		    	<div class="form-floating">
		      		<input type="text" class="form-control" name="user" placeholder="Usuario">
		      		<label for="floatingInput">Usuario</label>
		    	</div>
		    	<div class="form-floating mb-4">
		      		<input type="password" class="form-control" name="pass" placeholder="Contraseña">
		     		<label for="floatingPassword">Contraseña</label>
		    	</div>
		 		<button class="btn btn-lg btn-success mb-1" id="login">INGRESAR</button>
		 		<a href="./">CANCELAR</a>
        		<div id="somediv"></div>
		  	</form>
		</main>
        
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>

        	$(document).on("click", "#login", function(e) {
	        	
        		// Sweet Alert 2
        		const Toast = Swal.mixin({
        			  toast: true,
        			  position: 'top-end',
        			  showConfirmButton: false,
        			  timer: 3000,
        			  timerProgressBar: true,
        			  didOpen: (toast) => {
        			    toast.addEventListener('mouseenter', Swal.stopTimer)
        			    toast.addEventListener('mouseleave', Swal.resumeTimer)
        			  }
        		})
        		
        		e.preventDefault();
	        	var $form = $('#form');

        	    $.post($form.attr("action"), $form.serialize(), function(response) {
        	        Toast.fire({
          			  icon: response[0],
          			  title: "Iniciando sesión"
          			})
          			if(response[0]=="success"){
          				setTimeout(function(){ window.location="dashboard.jsp"; }, 3000);
          			}
        	    });
	        	
	        });
        	
        </script>
    </body>
</html>
