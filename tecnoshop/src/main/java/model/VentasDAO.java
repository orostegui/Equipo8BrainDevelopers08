package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Connect;

public class VentasDAO {
	
	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;
	
	/**
	 * Listar productos, usada para recuperar todos los productos
	 * @return ArrayList<ClienteDTO>
	 */
	public ArrayList<VentasDTO> listar() {
		
		// ArrayList donde se almacenan todos los clientes recuperados
		ArrayList<VentasDTO> ventas = new ArrayList<VentasDTO>();
		
		try {
			// Consulta y ejecucion
			String sql = "select codigo_venta,nombre_usuario,nombre_cliente,total_venta from ventas as vt inner join usuarios as us on vt.cedula_usuario = us.cedula_usuario inner join clientes as cl on vt.cedula_cliente = cl.cedula_cliente";
			//String sql = "select * from productos";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				VentasDTO venta = new VentasDTO(res.getLong("codigo_venta"),res.getDouble("total_venta"), res.getString("nombre_usuario"), res.getString("nombre_cliente"));
				ventas.add(venta);
			}
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return ventas;
	}

}
