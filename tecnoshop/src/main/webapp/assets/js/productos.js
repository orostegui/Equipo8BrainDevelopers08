$(document).ready(function() {
	
	$(document).ready(function() {
		
	    $('#productos').DataTable( {
			ajax: {
		        url: 'Productos',
				type: "POST",
				data:  {'ini':true},
		        dataSrc: ''
		    },
	        "columns": [
	            { "data": "codigo_producto" },
	            { "data": "nombre_producto" },
	            { "data": "nitproveedor" },
	            { "data": "precio_compra" },
				{ "data": "ivacompra" },
				{ "data": "precio_venta" },
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

$("#file-upload").change(function() {
	$('#info-file')[0].innerHTML = $("#file-upload")[0].files[0].name;
});

$("#upload-form").on("submit", function(e){
    e.preventDefault();
    var f = $(this)[0][0];
    var formData = new FormData(document.getElementById("upload-form"));
    formData.append(f.name, f.files);
    
    if(!$("#file-upload")[0].files[0]){
        Swal.fire({
            icon: 'error',
            title: 'Seleccione un documento',
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })
    } else {
        Swal.fire({
            title: 'Esta accion es irreversible',
            text: "¿Quieres confirmar la carga del archivo?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#4ba13e',
            confirmButtonText: 'Si, proceder!',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.value) {
                $.ajax({
                    url: "CargarProductos",
                    type: "post",
                    dataType: "html",
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false,
                    beforeSend : function (){
                        Swal.fire({
                            title:'Actualizando la base de datos',
                            text:'No actualice la página hasta que se hayan cargado todos los datos.',
                            icon:'warning',
                            showConfirmButton: false
                        })
                    }
                })
                .done(function(res){
					res = JSON.parse(res);
                    Swal.fire({
                        text: res[1],
                        icon: res[0],
                        showConfirmButton: false,
                        onClose: () => {
                            document.location.reload();
                        }
                    })
                });
            }
        })
    }
});

/**
 * FUNCION PARA EDITAR USUARIOS EN LA BASE DE DATOS
 */

$(document).on("click", "#editar", function() {
    
	(async() => {
	
		const { value: cod } = await Swal.fire({
        	title:'<h2>EDITAR PRODUCTO</h2>',
			html:'<div class="form-floating left-input">'+
            	'<input type="number" class="form-control" id="cod" placeholder="Código producto">'+
                '<label for="floatingInput">Código producto</label>'+
            '</div>',
            confirmButtonText:'EDITAR',
			focusConfirm:false,
            cancelButtonText:'CANCELAR',
            confirmButtonColor:'#33CC33',
            showCancelButton: true,
            preConfirm: () => {
                return document.getElementById('cod').value
			}
        })

        if (cod) {
        	
			const data = "con="+true+"&cod="+cod;
                
			$.post('Productos', data, function(response) {
                    
            	if(response[0]=="success"){
	
                	(async() => {
	
						const { value: formValues } = await Swal.fire({
	                    	title: '<h2>PRODUCTO A EDITAR</h2>',
            				width:'45rem',
							html:'<div class="row sa">'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="cod" placeholder="Código producto" value="'+response[1]+'" disabled>'+
				                        '<label for="floatingInput">Código producto</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating">'+
				                        '<input type="text" class="form-control" id="nombre" placeholder="Nombre" value="'+response[2]+'">'+
				                        '<label for="floatingInput">Nombre</label>'+
				                    '</div>'+
				                '</div>'+
				                '<div class="col-6 mb-3">'+
				                    '<div class="form-floating left-input">'+
				                        '<input type="text" class="form-control" id="venta" placeholder="Precio Venta" value="'+response[3]+'">'+
				                        '<label for="floatingPassword">Precio Venta</label>'+
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
				                    document.getElementById('cod').value,
				                    document.getElementById('nombre').value,
				                    document.getElementById('venta').value
				                ]
				            }
					    })
						
						if (formValues) {
	
				            const data = "edit="+true+"&cod="+formValues[0]+"&nb="+formValues[1]+"&pv="+formValues[2];
				
				            $.post('Productos', data, function(response) {
					                                    
				                if(response[0]=="success"){
				                            
				                    Swal.fire({
				                        title: '<h2>PRODUCTO ACTUALIZADO</h2>',
				                        text:response[1],
				                        icon:response[0],
				                        confirmButtonText: 'CERRAR',
				                        confirmButtonColor: '#6e7d88'
				                    }).then(() => {
										window.location="./productos.jsp";
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
                        text:'Producto no encontrado',
						confirmButtonText:'CERRAR',
                        confirmButtonColor:'#6e7d88',
                    })
                }
		    });
	    }
	})();
})