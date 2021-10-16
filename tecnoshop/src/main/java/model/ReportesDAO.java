package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Connect;

public class ReportesDAO {

	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;
	
	/**
	 * Listar proveedores, usada para recuperar todos los proveedores registrados
	 * @return ArrayList<ClienteDTO>
	 */
	public ArrayList<ReportesDTO> ventas() {
		
		// ArrayList donde se almacenan todos los clientes recuperados
		ArrayList<ReportesDTO> ventas = new ArrayList<ReportesDTO>();
		
		try {
			// Consulta y ejecucion
			String sql = "select c.cedula_cliente, nombre_cliente, sum(total_venta), sum(ivaventa) ,sum(valor_venta) from clientes as c inner join ventas as v on c.cedula_cliente=v.cedula_cliente\r\n"
					+ "group by c.cedula_cliente;";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				ReportesDTO venta = new ReportesDTO(res.getLong("cedula_cliente"), res.getString("nombre_cliente"), res.getDouble("sum(total_venta)"), res.getDouble("sum(ivaventa)"), res.getDouble("sum(valor_venta)"));
				ventas.add(venta);
			}
			conec.close();
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return ventas;
	}
	
}
