package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.Connect;

public class VentasDAO {
	
	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;
	
	/**
	 * Listar proveedores, usada para recuperar todos los proveedores registrados
	 * @return ArrayList<ClienteDTO>
	 */
	public ArrayList<VentasDTO> listar() {
		
		// ArrayList donde se almacenan todos los clientes recuperados
		ArrayList<VentasDTO> ventas = new ArrayList<VentasDTO>();
		
		try {
			// Consulta y ejecucion
			String sql = "select codigo_venta,nombre_cliente,nombre_usuario,ivaventa,total_venta,valor_venta from ventas as vt inner join clientes as ct on vt.cedula_cliente = ct.cedula_cliente inner join usuarios as us on vt.cedula_usuario = us.cedula_usuario";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				VentasDTO venta = new VentasDTO(res.getInt("codigo_venta"), res.getString("nombre_cliente"), res.getString("nombre_usuario"), res.getInt("ivaventa"), res.getDouble("total_venta"), res.getDouble("valor_venta"));
				ventas.add(venta);
			}
			conec.close();
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return ventas;
	}
	
	/**
	 * Consultar Clientes, usada para recuperar informacion de usuarios
	 * @return List<String>
	 */
	public List<String> registrar(VentasDTO venta) {
		// List para retornar los mensajes
		List<String> list = new ArrayList<>();
		// Intento ejecucion SQL
		try {
			// Consulta y ejecucion
			String sql = "INSERT INTO `ventas` (`cedula_cliente`, `cedula_usuario`, `ivaventa`, `total_venta`, `valor_venta`) VALUES (?, ?, ?, ?, ?);";
			ps = conec.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, venta.getCedula_cliente());
			ps.setLong(2, venta.getCedula_usuario());
			ps.setInt(3, venta.getIvaventa());
			ps.setDouble(4, venta.getTotal_venta());
			ps.setDouble(5, venta.getValor_venta());
			int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	        	list.add("error");
				list.add("Creating user failed, no rows affected.");
	        }
	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	venta.setCodigo_venta(generatedKeys.getInt(1));
	            	for(int i = 0; i < venta.getDetalle_venta().size(); i++) {
	            		String sql2 = "INSERT INTO `detalle_ventas` (`cantidad_producto`, `codigo_producto`, `codigo_venta`, `valor_total`, `valor_venta`, `valoriva`) VALUES (?, ?, ?, ?, ?, ?);";
		    			ps = conec.prepareStatement(sql2);
		    			ps.setInt(1, venta.getDetalle_venta().get(i).getCantidad());
		    			ps.setInt(2, venta.getDetalle_venta().get(i).getCodigo_producto());
		    			ps.setInt(3, venta.getCodigo_venta());
		    			ps.setDouble(4, venta.getDetalle_venta().get(i).getTotal_venta());
		    			ps.setDouble(5, venta.getDetalle_venta().get(i).getPrecio_venta());
		    			ps.setInt(6, venta.getDetalle_venta().get(i).getIva());
		    			ps.executeUpdate();
	            	}
	            	list.add("success");
	    			list.add("Venta registrada correctamente");
	            }
	        }
	        conec.close();
			// Si hay error en el SQL	
		}catch(SQLException e) {
			list.add("error");
			list.add("Error: "+e);
		}
		return list;
	}

}
