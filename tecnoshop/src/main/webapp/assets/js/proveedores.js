$(document).ready(function() {
	
	$(document).ready(function() {
		
	    $('#proveedores').DataTable( {
			ajax: {
		        url: 'Proveedores',
				type: "POST",
				data:  {'ini':true},
		        dataSrc: ''
		    },
	        "columns": [
	            { "data": "nitproveedor" },
	            { "data": "nombre_proveedor" },
	            { "data": "direccion_proveedor" },
	            { "data": "ciudad_proveedor" },
				{ "data": "telefono_proveedor" }
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

$(document).on("click", "#crear", function() {
    
    (async() => {
        
        const { value: formValues } = await Swal.fire({
            title:'<h2>CREAR PROVEEDOR</h2>',
            width:'45rem',
            html:'<div class="row sa">'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating left-input">'+
                        '<input type="text" class="form-control" id="nit" placeholder="NIT">'+
                        '<label for="floatingInput">NIT</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating">'+
                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre">'+
                        '<label for="floatingInput">Nombre</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating left-input">'+
                        '<input type="text" class="form-control" id="direccion" placeholder="Dirección">'+
                        '<label for="floatingPassword">Dirección</label>'+
                    '</div>'+
                '</div>'+
                '<div class="col-6 mb-3">'+
                    '<div class="form-floating">'+
                        '<input type="text" class="form-control" id="ciudad" placeholder="Ciudad">'+
                        '<label for="floatingInput">Ciudad</label>'+
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
                    document.getElementById('nit').value,
                    document.getElementById('nombre').value,
                    document.getElementById('direccion').value,
                    document.getElementById('ciudad').value,
					document.getElementById('tel').value
                ]
            }
        })

        if (formValues) {

            const data = "cre="+true+"&nit="+formValues[0]+"&nb="+formValues[1]+"&dr="+formValues[2]+"&cd="+formValues[3]+"&tf="+formValues[4];

            $.post('Proveedores', data, function(response) {
	                                    
                if(response[0]=="success"){
                            
                    Swal.fire({
                        title: '<h2>PROVEEDOR CREADO</h2>',
                        text:response[1],
                        icon:response[0],
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: '#6e7d88'
                    }).then(() => {
						window.location="./proveedores.jsp";
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
	
		const { value: nit } = await Swal.fire({
        	title:'<h2>EDITAR PROVEEDOR</h2>',
			html:'<div class="form-floating left-input">'+
            	'<input type="number" class="form-control" id="nit" placeholder="NIT">'+
                '<label for="floatingInput">NIT</label>'+
            '</div>',
            confirmButtonText:'EDITAR',
			focusConfirm:false,
            cancelButtonText:'CANCELAR',
            confirmButtonColor:'#33CC33',
            showCancelButton: true,
            preConfirm: () => {
                return document.getElementById('nit').value
			}
        })

        if (nit) {
        	
			const data = "con="+true+"&nit="+nit;
                
			$.post('Proveedores', data, function(response) {
                    
            	if(response[0]=="success"){
	
                	(async() => {
	
						const { value: formValues } = await Swal.fire({
	                    	title: '<h2>PROVEEDOR A EDITAR</h2>',
            				width:'45rem',
							html:'<div class="row sa">'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="nit" placeholder="NIT" value="'+response[1]+'" disabled>'+
				                        '<label for="floatingInput">NIT</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre" value="'+response[4]+'">'+
				                        '<label for="floatingInput">Nombre</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="direccion" placeholder="Dirección" value="'+response[3]+'">'+
				                        '<label for="floatingPassword">Dirección</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="text" class="form-control" id="ciudad" placeholder="Ciudad" value="'+response[2]+'">'+
				                        '<label for="floatingInput">Ciudad</label>'+
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
				                    document.getElementById('nit').value,
				                    document.getElementById('nombre').value,
				                    document.getElementById('direccion').value,
				                    document.getElementById('ciudad').value,
									document.getElementById('tel').value
				                ]
				            }
					    })
						
						if (formValues) {
	
				            const data = "edit="+true+"&nit="+formValues[0]+"&nb="+formValues[1]+"&dr="+formValues[2]+"&cd="+formValues[3]+"&tf="+formValues[4];
				
				            $.post('Proveedores', data, function(response) {
					                                    
				                if(response[0]=="success"){
				                            
				                    Swal.fire({
				                        title: '<h2>PROVEEDOR ACTUALIZADO</h2>',
				                        text:response[1],
				                        icon:response[0],
				                        confirmButtonText: 'CERRAR',
				                        confirmButtonColor: '#6e7d88'
				                    }).then(() => {
										window.location="./proveedores.jsp";
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
                        text:'Proveedor no encontrado',
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
	
		const { value: nit } = await Swal.fire({
        	title:'<h2>ELIMINAR PROVEEDOR</h2>',
			html:'<div class="form-floating left-input">'+
            	'<input type="number" class="form-control" id="nit" placeholder="NIT">'+
                '<label for="floatingInput">NIT</label>'+
            '</div>',
            confirmButtonText:'ELIMINAR',
			focusConfirm:false,
            cancelButtonText:'CANCELAR',
            confirmButtonColor:'#D33',
            showCancelButton: true,
            preConfirm: () => {
                return document.getElementById('nit').value
			}
        })

        if (nit) {
        	
			const data = "con="+true+"&nit="+nit;
                
			$.post('Proveedores', data, function(response) {
                    
            	if(response[0]=="success"){
                          
                	Swal.fire({
                    	title: '<h2>¿ESTÁS SEGURO DE ELIMINAR?</h2>',
                        html:'NIT: <b>' + response[1] + '</b><br>'+
                            'Nombre: <b>' + response[4] + '</b><br>' +
                        	'Dirección: <b>' + response[3] + '</b><br>' +
                        	'Ciudad: <b>' + response[2] + '</b><br>' +
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
	
							const del = "del="+true+"&nit="+nit;
	
                            $.post('Proveedores', del, function(response) {
                                        
                                if(response[0]=="success"){
                                            
                                    Swal.fire({
                                        title: '<h2>ELIMINADO</h2>',
                                        text:response[1],
                                        icon:response[0],
                                        confirmButtonText: 'CERRAR',
                                        confirmButtonColor: '#6e7d88'
                                    }).then(() => {
										window.location="./proveedores.jsp";
									})
									
                                } else {
                                    
                                    Swal.fire({
                                        title: '<h2>Error al eliminar el proveedor</h2>',
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
                                text:'La eliminación del proveedor ha sido cancelada.',
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
                        text:'Proveedor no encontrado',
						confirmButtonText:'CERRAR',
                        confirmButtonColor:'#6e7d88',
                    })
                }
		    });
	    }
	})();
})