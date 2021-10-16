$(document).ready(function() {
	
	$('#usuarios').DataTable( {
		ajax: {
			url: 'Usuarios',
			type: "POST",
			data:  {'ini':true},
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
	});
	
	$('#clientes').DataTable( {
		ajax: {
			url: 'Clientes',
			type: "POST",
			data:  {'ini':true},
		    dataSrc: ''
		},
	    "columns": [
	    	{ "data": "cedula_cliente" },
	        { "data": "nombre_cliente" },
	        { "data": "direccion_cliente" },
			{ "data": "telefono_cliente" }
		],
		"language": {
	    	"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
	    }
	});
	
	$('#ventas').DataTable( {
		ajax: {
        	url: 'Reportes',
			type: "POST",
			data:  {'ini':true},
			dataSrc: ''
		},
	    "columns": [
	    	{ "data": "cedula_cliente" },
	        { "data": "nombre_cliente" },
	        { "data": "total_venta" },
			{ "data": "ivaventa" },
			{ "data": "valor_venta" }
		],
		"language": {
	    	"url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
	    },
		"footerCallback": function ( row, data, start, end, display ) {
            var api = this.api(), data;
 
            // Remove the formatting to get integer data for summation
            var intVal = function ( i ) {
                return typeof i === 'string' ?
                    i.replace(/[\$,]/g, '')*1 :
                    typeof i === 'number' ?
                        i : 0;
            };
 
            // Total over all pages
            total = api
                .column( 4 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
            // Update footer
            $( api.column( 4 ).footer() ).html(
                '$'+ total
            );
        }
	});

});