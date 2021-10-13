$(document).ready(function() {
	
	const $selectClientes = document.querySelector("#cc_cliente");
	const $cedulaCliente = document.querySelector("#cc");
	const $direccionCliente = document.querySelector("#dir");
	const $emailCliente = document.querySelector("#email");
	const $telefonoCliente = document.querySelector("#tel");
	const data = "cli="+true;
	var $clientes;
	
	$.post('Ventas', data, function(response) {
		$clientes = response;
		for(let i=0; i<response.length; i++){
			const option = document.createElement('option');
			option.value = response[i]['cedula_cliente'];
			option.text = response[i]['nombre_cliente'];
			$selectClientes.appendChild(option);	
		}
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
