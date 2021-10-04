$(document).ready(function() {
	
	$(document).ready(function() {
		
	    $('#proveedores').DataTable( {
			ajax: {
		        url: 'VerProveedores',
		        dataSrc: ''
		    },
	        "columns": [
	            { "data": "cedula_cliente" },
	            { "data": "nombre_cliente" },
	            { "data": "email_cliente" },
	            { "data": "direccion_cliente" },
				{ "data": "telefono_cliente" }
		        ],
			"language": {
	            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
	        }
		} );
	} );

});


/**
 * FUNCION PARA CREAR CLIENTES EN LA BASE DE DATOS
 */

$(document).on("click", "#crear", function(e) {
    
    (async() => {
        
        const { value: formValues } = await Swal.fire({
            title:'<h2>CREAR CLIENTE</h2>',
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
                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre completo">'+
                        '<label for="floatingInput">Nombre completo</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating left-input">'+
                        '<input type="email" class="form-control" id="email" placeholder="Email">'+
                        '<label for="floatingPassword">Email</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating">'+
                        '<input type="text" class="form-control" id="direccion" placeholder="Dirección">'+
                        '<label for="floatingInput">Dirección</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6">'+
                    '<div class="form-floating left-input">'+
                        '<input type="tel" class="form-control" id="tel" placeholder="Teléfono">'+
                        '<label for="floatingPassword">Teléfono</label>'+
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
                    document.getElementById('nombre').value,
                    document.getElementById('email').value,
                    document.getElementById('direccion').value,
					document.getElementById('tel').value
                ]
            }
        })

        if (formValues) {

            const data = "cc="+formValues[0]+"&nc="+formValues[1]+"&em="+formValues[2]+"&dr="+formValues[3]+"&tf="+formValues[4];

            $.post('CrearCliente', data, function(response) {
	                                    
                if(response[0]=="success"){
                            
                    Swal.fire({
                        title: '<h2>CLIENTE CREADO</h2>',
                        text:response[1],
                        icon:response[0],
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: '#6e7d88'
                    }).then(() => {
						window.location="./clientes.jsp";
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
        	title:'<h2>EDITAR CLIENTE</h2>',
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
                
			$.post('ConsultarCliente', data, function(response) {
                    
            	if(response[0]=="success"){
	
                	(async() => {
	
						const { value: formValues } = await Swal.fire({
	                    	title: '<h2>CLIENTE A EDITAR</h2>',
            				width:'45rem',
							html:'<div class="row sa">'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="cedula" placeholder="Número de cédula" value="'+response[1]+'" disabled>'+
				                        '<label for="floatingInput">Número de cédula</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre completo" value="'+response[4]+'">'+
				                        '<label for="floatingInput">Nombre completo</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="email" class="form-control" id="email" placeholder="Email" value="'+response[3]+'">'+
				                        '<label for="floatingPassword">Email</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="text" class="form-control" id="direccion" placeholder="Dirección" value="'+response[2]+'">'+
				                        '<label for="floatingInput">Dirección</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="tel" class="form-control" id="tel" placeholder="Teléfono" value="'+response[5]+'">'+
				                        '<label for="floatingPassword">Teléfono</label>'+
				                    '</div>'+
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
				                    document.getElementById('nombre').value,
				                    document.getElementById('email').value,
				                    document.getElementById('direccion').value,
									document.getElementById('tel').value
				                ]
				            }
					    })
						
						if (formValues) {
	
				            const data = "cc="+formValues[0]+"&nc="+formValues[1]+"&em="+formValues[2]+"&dr="+formValues[3]+"&tf="+formValues[4];
				
				            $.post('EditarCliente', data, function(response) {
					                                    
				                if(response[0]=="success"){
				                            
				                    Swal.fire({
				                        title: '<h2>CLIENTE ACTUALIZADO</h2>',
				                        text:response[1],
				                        icon:response[0],
				                        confirmButtonText: 'CERRAR',
				                        confirmButtonColor: '#6e7d88'
				                    }).then(() => {
										window.location="./clientes.jsp";
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


/**
 * FUNCION PARA BORRAR CLIENTES DE LA BASE DE DATOS
 */

$(document).on("click", "#borrar", function() {
    
	(async() => {
	
		const { value: cc } = await Swal.fire({
        	title:'<h2>ELIMINAR CLIENTE</h2>',
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
                
			$.post('ConsultarCliente', data, function(response) {
                    
            	if(response[0]=="success"){
                          
                	Swal.fire({
                    	title: '<h2>¿ESTÁS SEGURO DE ELIMINAR?</h2>',
                        html:'Número de cédula: <b>' + response[1] + '</b><br>'+
                            'Nombre completo: <b>' + response[4] + '</b><br>' +
                        	'Email: <b>' + response[3] + '</b><br>' +
                        	'Dirección: <b>' + response[2] + '</b><br>' +
                            'Teléfono: <b>' + response[5] + '</b>',
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
	
                            $.post('EliminarCliente', data, function(response) {
                                        
                                if(response[0]=="success"){
                                            
                                    Swal.fire({
                                        title: '<h2>Eliminado</h2>',
                                        text:response[1],
                                        icon:response[0],
                                        confirmButtonText: 'CERRAR',
                                        confirmButtonColor: '#6e7d88'
                                    }).then(() => {
										window.location="./clientes.jsp";
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