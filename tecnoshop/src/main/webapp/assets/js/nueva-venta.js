const $selectClientes = document.querySelector("#selectCliente");
const $cedulaCliente = document.querySelector("#cc");
const $direccionCliente = document.querySelector("#dir");
const $emailCliente = document.querySelector("#email");
const $telefonoCliente = document.querySelector("#tel");
var $clientes,$productos,indice=1;
var $agregados = [];

function formatCurrency (locales, currency, fractionDigits, number) {
	var formatted = new Intl.NumberFormat(locales, {
	    style: 'currency',
	    currency: currency,
	    minimumFractionDigits: fractionDigits
	}).format(number);
	return formatted;
}

const eliminar = (id) => {

	$agregados.splice(id-1, 1);
	
	let subtotal=0, iva=0, total=0;
	
	document.querySelector("#table-body").innerHTML = "";
	
	for(let i=0; i<$agregados.length; i++){
		document.querySelector("#table-body").innerHTML += "<tr>"+
			"<td>"+(i+1)+"</td>"+
			"<td>"+$agregados[i]['nombre_producto']+"</td>"+
			"<td>"+$agregados[i]['cantidad']+"</td>"+
			"<td class='text-end'>"+formatCurrency("es-CO", "COP", 0, $agregados[i]['precio_venta'])+"</td>"+
			"<td class='text-end'>"+$agregados[i]['iva']+"</td>"+
			"<td class='text-end'>"+formatCurrency("es-CO", "COP", 0, $agregados[i]['precio_venta'] * $agregados[i]['cantidad'])+"</td>"+
			"<td class='text-end'><a href='#' onclick='eliminar("+(i+1)+")'><i class='bi bi-trash'></i></a></td>"+
			"</tr>";
		subtotal += ($agregados[i]['precio_compra']+($agregados[i]['precio_compra']*($agregados[i]['utilidad']/100)))*$agregados[i]['cantidad'];
		total += $agregados[i]['precio_venta']*$agregados[i]['cantidad'];
		iva = total - subtotal;
	}
	document.querySelector("#subtotal").innerHTML= formatCurrency("es-CO", "COP", 0, subtotal);
	document.querySelector("#iva-venta").innerHTML= formatCurrency("es-CO", "COP", 0, iva);
	document.querySelector("#total-pagar").innerHTML= formatCurrency("es-CO", "COP", 0, total);
	indice--
}

$(document).ready(function() {
	
	const dataC = "ini="+true;
	$.post('Clientes', dataC, function(response) {
		$clientes = response;
		for(let i=0; i<response.length; i++){
			const option = document.createElement('option');
			option.value = response[i]['cedula_cliente'];
			option.text = response[i]['nombre_cliente'];
			$selectClientes.appendChild(option);	
		}
	});
	
	const dataP = "ini="+true;
	$.post('Productos', dataP, function(response) {
		$productos = response;
	});
	
	const clienteCambiado = () => {
		if($selectClientes.value == 0){
			$cedulaCliente.value="";
			$direccionCliente.value="";
			$emailCliente.value="";
			$telefonoCliente.value="";
		} else {
			for(let i=0; i<$clientes.length; i++){
				if($selectClientes.value==$clientes[i]['cedula_cliente']){
					$cedulaCliente.value=$clientes[i]['cedula_cliente'];
					$direccionCliente.value=$clientes[i]['direccion_cliente'];
					$emailCliente.value=$clientes[i]['email_cliente'];
					$telefonoCliente.value=$clientes[i]['telefono_cliente'];
				}
			}
		}
	};
	
	
	$selectClientes.addEventListener("change", clienteCambiado);
	//$agregarProductos.addEventListener("click", agregarProducto);
		
});

/**
 * FUNCION PARA EDITAR USUARIOS EN LA BASE DE DATOS
 */

$(document).on("click", "#agregar", function() {
    
	(async() => {
	
		const { value: formValues } = await Swal.fire({
        	title:'<h2>AGREGAR PRODUCTO</h2>',
			html:'<div class="row sa">'+
                '<div class="col-9">'+
                    '<select class="form-select form-select-lg" style="width:95%" id="selectProducto">'+
						'<option selected value="0">Seleccione un producto</option>'+
					'</select>'+
                '</div>'+
                '<div class="col-3">'+
                    '<div class="form-floating">'+
                        '<input type="number" class="form-control" id="cantidad" placeholder="Cant." value="1">'+
                        '<label for="floatingInput">Cant.</label>'+
                    '</div>'+
                '</div>'+
            '</div>',
			allowOutsideClick: false,
            confirmButtonText:'AGREGAR',
			focusConfirm:false,
            cancelButtonText:'CANCELAR',
            confirmButtonColor:'#33CC33',
            showCancelButton: true,
			didOpen: () => {
				for(let i=0; i<$productos.length; i++){
					const option = document.createElement('option');
					option.value = $productos[i]['codigo_producto'];
					option.text = $productos[i]['nombre_producto'];
					document.querySelector("#selectProducto").appendChild(option);	
				}
			},
            preConfirm: () => {
				return [
                    document.getElementById('selectProducto').value,
					document.getElementById('cantidad').value,
                ]
			}
        })

        if (formValues) {
			
			for(let i=0; i<$productos.length; i++){
				if($productos[i]['codigo_producto']==formValues[0]){
					$agregados.push({ 
				        "codigo_producto" : $productos[i]['codigo_producto'],
						"nombre_producto" : $productos[i]['nombre_producto'],
						"precio_venta" : $productos[i]['precio_venta'],
						"precio_compra" : $productos[i]['precio_compra'],
						"utilidad" : $productos[i]['porcentaje_utilidad'],
						"iva" : $productos[i]['ivacompra'],
				        "cantidad" : parseInt(formValues[1]),
						"total_venta" : $productos[i]['precio_venta'] * parseInt(formValues[1])
				    });
					document.querySelector("#table-body").innerHTML += "<tr>"+
						"<td>"+indice+"</td>"+
						"<td>"+$productos[i]['nombre_producto']+"</td>"+
						"<td>"+parseInt(formValues[1])+"</td>"+
						"<td class='text-end'>"+formatCurrency("es-CO", "COP", 0, $productos[i]['precio_venta'])+"</td>"+
						"<td class='text-end'>"+$productos[i]['ivacompra']+"</td>"+
						"<td class='text-end'>"+formatCurrency("es-CO", "COP", 0, $productos[i]['precio_venta'] * parseInt(formValues[1]))+"</td>"+
						"<td class='text-end'><a href='#' onclick='eliminar("+indice+")'><i class='bi bi-trash'></i></a></td>"+
						"</tr>";
					indice++
				}
			}
			
			let subtotal=0, iva=0, total=0;
			
			for(let i=0; i<$agregados.length; i++){
				subtotal += ($agregados[i]['precio_compra']+($agregados[i]['precio_compra']*($agregados[i]['utilidad']/100)))*$agregados[i]['cantidad'];
				total += $agregados[i]['precio_venta']*$agregados[i]['cantidad'];
				iva = total - subtotal;
			}
			
			document.querySelector("#subtotal").innerHTML= formatCurrency("es-CO", "COP", 0, subtotal);
			document.querySelector("#iva-venta").innerHTML= formatCurrency("es-CO", "COP", 0, iva);
			document.querySelector("#total-pagar").innerHTML= formatCurrency("es-CO", "COP", 0, total);
	    }
	})();
})

$(document).on("click", "#registrar", function() {
	
	if($selectClientes.value==0){
		Swal.fire({
			icon: 'error',
			title: 'Oops...',
			text: 'Debes seleccionar un cliente',
            confirmButtonText:'CERRAR',
            confirmButtonColor:'#6e7d88'
		})
	} else if ($agregados.length == 0){
		Swal.fire({
			icon: 'error',
			title: 'Oops...',
			text: 'Debes agregar al menos un (1) producto',
            confirmButtonText:'CERRAR',
            confirmButtonColor:'#6e7d88'
		})
	} else {
		Swal.fire({
			title: '¿Deseas registrar esta venta?',
			showCancelButton: true,
			confirmButtonText: 'REGISTRAR',
			confirmButtonColor:'#33CC33',
			cancelButtonText: "CERRAR",
		}).then((result) => {
		  	/* Read more about isConfirmed, isDenied below */
			if (result.isConfirmed) {
				let subtotal=0, iva=0, total=0;
				for(let i=0; i<$agregados.length; i++){
					subtotal += ($agregados[i]['precio_compra']+($agregados[i]['precio_compra']*($agregados[i]['utilidad']/100)))*$agregados[i]['cantidad'];
					total += $agregados[i]['precio_venta']*$agregados[i]['cantidad'];
					iva = total - subtotal;
				}
				var $venta = {};
				$venta.cedula_cliente = $cedulaCliente.value;
				$venta.ivaventa = iva;
				$venta.total_venta = subtotal;
				$venta.valor_venta = total;
				$venta.detalle_venta = $agregados;
				
				$.ajax({
   		 			type: 'post', // it's easier to read GET request parameters
				    url: 'RegistrarVenta',
				    dataType: 'JSON',
				    data: { 
				      datos: JSON.stringify($venta) // look here!
				    },
				    success: function(response) {
						Swal.fire({
                        title: '<h2>VENTA REGISTRADA</h2>',
                        text:response[1],
                        icon:response[0],
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: '#6e7d88'
	                    }).then(() => {
							window.location="./nueva-venta.jsp";
						})
				    },
				    error: function(response) {
						Swal.fire({
                        title: '<h2>ERROR</h2>',
                        text:response[1],
                        icon:response[0],
                        confirmButtonText: 'CERRAR',
                        confirmButtonColor: '#6e7d88'
						})
				    }
				});
			}
		})
	}
	

})
