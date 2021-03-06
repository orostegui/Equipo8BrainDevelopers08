$(document).ready(function() {
	
	var tabla = $('#ventas').DataTable( {
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
                .column( 5 )
                .data()
                .reduce( function (a, b) {
                    return intVal(a) + intVal(b);
                }, 0 );
 
            // Update footer
            $( api.column( 5 ).footer() ).html(
                '$'+ total
            );
        }
	});
});