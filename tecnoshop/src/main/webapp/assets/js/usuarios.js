$(document).ready(function() {
	
	$(document).ready(function() {
		
	    $('#usuarios').DataTable( {
			ajax: {
		        url: 'VerUsuarios',
		        dataSrc: ''
		    },
	        "columns": [
	            { "data": "cedula_usuario" },
	            { "data": "nombre_usuario" },
	            { "data": "email_usuario" },
	            { "data": "usuario" }
		        ],
			"language": {
	            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
	        }
		} );
	} );

});


/**
 * FUNCION PARA BORRAR USUARIOS DE LA BASE DE DATOS
 */

$(document).on("click", "#borrar", function() {
    
	(async() => {
	
		const { value: cc } = await Swal.fire({
        	title:'<h2>ELIMINAR USUARIO</h2>',
			html:'<div class="form-floating left-input">'+
            	'<input type="number" class="form-control" id="cedula" placeholder="Número de cédula">'+
                '<label for="floatingInput">Número de cédula</label>'+
            '</div>',
            confirmButtonText:'ELIMINAR',
			focusConfirm:false,
            cancelButtonText:'CANCELAR',
            confirmButtonColor:'#D33',
            showCancelButton: true,
            preConfirm: () => {
                return document.getElementById('cedula').value
			}
        })

        if (cc) {
        	
			const data = "cc="+cc;
                
			$.post('ConsultarUsuario', data, function(response) {
                    
            	if(response[0]=="success"){
                          
                	Swal.fire({
                    	title: '<h2>¿ESTÁS SEGURO DE ELIMINAR?</h2>',
                        html:'Número de cédula: <b>' + response[1] + '</b><br>'+
                        	'Email: <b>' + response[2] + '</b><br>' +
                            'Nombre completo: <b>' + response[3] + '</b><br>' +
                            'Usuario: <b>' + response[4] + '</b>',
                        icon: 'warning',
                        showCancelButton: true,
                       	confirmButtonText: 'SI, ELIMINAR',
                        cancelButtonText: 'NO, CANCELAR',
                        focusCancel: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#6e7d88',
                        reverseButtons: true
				    }).then((result) => {
					
                        if (result.isConfirmed) {
	
                            $.post('EliminarUsuario', data, function(response) {
                                        
                                if(response[0]=="success"){
                                            
                                    Swal.fire({
                                        title: '<h2>Eliminado</h2>',
                                        text:response[1],
                                        icon:response[0],
                                        confirmButtonText: 'CERRAR',
                                        confirmButtonColor: '#6e7d88'
                                    }).then(() => {
										window.location="./usuarios.jsp";
									})
									
                                } else {
                                    
                                    Swal.fire({
                                        title: '<h2>Error al eliminar el usuario</h2>',
                                        text: response[1],
                                        icon: response[0],
                                        confirmButtonText: 'CERRAR',
                                        confirmButtonColor: '#6e7d88'
                                    })
                                }
                            });      
                        } else if (result.dismiss === Swal.DismissReason.cancel) {
                            
                            Swal.fire({
                                title: '<h2>CANCELADO</h2>',
                                text:'La eliminación del usuario ha sido cancelada.',
                                icon:'error',
                                confirmButtonText: 'CERRAR',
                                confirmButtonColor: '#6e7d88'	
                            })
                        }
				    })
                } else {
                    Swal.fire({
                        icon:'error',
                        title:'<h2>Oops...</h2>',
                        text:'Usuario no encontrado',
						confirmButtonText:'CERRAR',
                        confirmButtonColor:'#6e7d88',
                    })
                }
		    });
	    }
	})();
})

/**
 * FUNCION PARA CREAR USUARIOS EN LA BASE DE DATOS
 */

$(document).on("click", "#crear", function(e) {
    
    (async() => {
        
        const { value: formValues } = await Swal.fire({
            title:'<h2>CREAR USUARIO</h2>',
            width:'45rem',
            html:'<div class="row sa">'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating left-input">'+
                        '<input type="text" class="form-control" id="cedula" placeholder="Número de cédula">'+
                        '<label for="floatingInput">Número de cédula</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating">'+
                        '<input type="text" class="form-control" id="usuario" placeholder="Usuario">'+
                        '<label for="floatingPassword">Usuario</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6">'+
                    '<div class="form-floating left-input">'+
                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre completo">'+
                        '<label for="floatingInput">Nombre completo</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6">'+
                    '<div class="form-floating">'+
                        '<input type="email" class="form-control" id="email" placeholder="Email">'+
                        '<label for="floatingPassword">Email</label>'+
                    '</div>'+
                '</div>'+
            '</div>',
            confirmButtonText: 'CREAR',
            cancelButtonText: 'CANCELAR',
            confirmButtonColor: '#33CC33',
			showCancelButton: true,
            focusConfirm: false,
            preConfirm: () => {
                return [
                    document.getElementById('cedula').value,
                    document.getElementById('usuario').value,
                    document.getElementById('nombre').value,
                    document.getElementById('email').value
                ]
            }
        })

        if (formValues) {

            const data = "cc="+formValues[0]+"&us="+formValues[1]+"&nc="+formValues[2]+"&em="+formValues[3];

            $.post('CrearUsuario', data, function(response) {
	                                    
                if(response[0]=="success"){
                            
                    Swal.fire({
                        title: '<h2>USUARIO CREADO</h2>',
                        text:response[1],
                        icon:response[0],
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: '#6e7d88'
                    }).then(() => {
						window.location="./usuarios.jsp";
					})
                } else {
                    
                    Swal.fire({
                        title: '<h2>ERROR AL CREAR</h2>',
                        text: response[1],
                        icon: response[0],
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: '#6e7d88'
                    })
                }
            }); 
        }
    })();
})

/**
 * FUNCION PARA EDITAR USUARIOS EN LA BASE DE DATOS
 */

$(document).on("click", "#editar", function() {
    
	(async() => {
	
		const { value: cc } = await Swal.fire({
        	title:'<h2>EDITAR USUARIO</h2>',
			html:'<div class="form-floating left-input">'+
            	'<input type="number" class="form-control" id="cedula" placeholder="Número de cédula">'+
                '<label for="floatingInput">Número de cédula</label>'+
            '</div>',
            confirmButtonText:'EDITAR',
			focusConfirm:false,
            cancelButtonText:'CANCELAR',
            confirmButtonColor:'#33CC33',
            showCancelButton: true,
            preConfirm: () => {
                return document.getElementById('cedula').value
			}
        })

        if (cc) {
        	
			const data = "cc="+cc;
                
			$.post('ConsultarUsuario', data, function(response) {
                    
            	if(response[0]=="success"){
	
                	(async() => {
	
						const { value: formValues } = await Swal.fire({
	                    	title: '<h2>USUARIO A EDITAR</h2>',
							html:'<div class="row sa">'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="cedula" placeholder="Número de cédula" value="'+response[1]+'" disabled>'+
				                        '<label for="floatingInput">Número de cédula</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="text" class="form-control" id="usuario" placeholder="Usuario" value="'+response[4]+'">'+
				                        '<label for="floatingPassword">Usuario</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre completo" value="'+response[3]+'">'+
				                        '<label for="floatingInput">Nombre completo</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="email" class="form-control" id="email" placeholder="Email" value="'+response[2]+'">'+
				                        '<label for="floatingPassword">Email</label>'+
				                    '</div>'+
				                '</div>'+
								'<div class="col-6">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="password" class="form-control" id="password" placeholder="Contraseña">'+
				                        '<label for="floatingPassword">Contraseña</label>'+
				                    '</div>'+
				                '</div>'+
								'<div class="col-6">'+
				                    '<p style="font-size:.8rem;text-align:left;font-weight: 300;line-height:.8rem;">**Dejar contra vacia si no desea actualizarla</p>'+
				                '</div>'+
				            '</div>',
	                        showCancelButton: true,
	                       	confirmButtonText: 'CONFIRMAR',
	                        cancelButtonText: 'CANCELAR',
	                        focusCancel: true,
	                        confirmButtonColor: '#33CC33',
	                        cancelButtonColor: '#6e7d88',
							preConfirm: () => {
				                return [
				                    document.getElementById('cedula').value,
				                    document.getElementById('usuario').value,
				                    document.getElementById('nombre').value,
				                    document.getElementById('email').value,
									document.getElementById('password').value,
				                ]
				            }
					    })
						
						if (formValues) {
	
				            const data = "cc="+formValues[0]+"&us="+formValues[1]+"&nc="+formValues[2]+"&em="+formValues[3]+"&pw="+formValues[4];
				
				            $.post('EditarUsuario', data, function(response) {
					                                    
				                if(response[0]=="success"){
				                            
				                    Swal.fire({
				                        title: '<h2>USUARIO ACTUALIZADO</h2>',
				                        text:response[1],
				                        icon:response[0],
				                        confirmButtonText: 'CERRAR',
				                        confirmButtonColor: '#6e7d88'
				                    }).then(() => {
										window.location="./usuarios.jsp";
									})
				                } else {
				                    
				                    Swal.fire({
				                        title: '<h2>ERROR AL ACTUALIZAR</h2>',
				                        text: response[1],
				                        icon: response[0],
				                        confirmButtonText: 'CERRAR',
				                        confirmButtonColor: '#6e7d88'
				                    })
				                }
				            }); 
				        }
	
					})();
                	
                } else {
                    Swal.fire({
                        icon:'error',
                        title:'<h2>Oops...</h2>',
                        text:'Usuario no encontrado',
						confirmButtonText:'CERRAR',
                        confirmButtonColor:'#6e7d88',
                    })
                }
		    });
	    }
	})();
})