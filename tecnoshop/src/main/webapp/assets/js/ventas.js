$(document).ready(function() {
	
	$('#ventas').DataTable( {
		ajax: {
        	url: 'Ventas',
			type: "POST",
			data:  {'ini':true},
			dataSrc: ''
		},
	    "columns": [
	    	{ "data": "codigo_venta" },
	        { "data": "nombre_cliente" },
	        { "data": "nombre_usuario" },
	        { "data": "total_venta" },
			{ "data": "ivaventa" },
			{ "data": "valor_venta" }
		],
		"language": {
	    	"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
	    }
	});
});