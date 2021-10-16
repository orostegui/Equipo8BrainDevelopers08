package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.Connect;

public class ProductosDAO {
	
	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;
	
	/**
	 * Listar productos, usada para recuperar todos los productos
	 * @return ArrayList<ClienteDTO>
	 */
	public ArrayList<ProductosDTO> listar() {
		
		// ArrayList donde se almacenan todos los clientes recuperados
		ArrayList<ProductosDTO> productos = new ArrayList<ProductosDTO>();
		
		try {
			// Consulta y ejecucion
			String sql = "select codigo_producto,ivacompra,nombre_proveedor,nombre_producto,precio_compra,porcentaje_utilidad from proveedores as pv inner join productos as pr on pv.nitproveedor = pr.nitproveedor";
			//String sql = "select * from productos";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				double compra = res.getDouble("precio_compra");
				int utilidad = res.getInt("porcentaje_utilidad");
				int iva = res.getInt("ivacompra");
				double precio_venta = compra+((compra*utilidad)/100);
				precio_venta = precio_venta+((precio_venta*iva)/100);
				ProductosDTO producto = new ProductosDTO(res.getLong("codigo_producto"), res.getInt("ivacompra"), res.getString("nombre_proveedor"), res.getString("nombre_producto"),res.getDouble("precio_compra"),res.getInt("porcentaje_utilidad"),precio_venta);
				productos.add(producto);
			}
			conec.close();
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return productos;
	}
	
	/**
	 * Cargar productos
	 * @return List<String>
	 */
	public List<String> cargar(String Url) {
		// List para retornar los mensajes
		List<String> list = new ArrayList<>();
		
		// Intento ejecucion SQL
		try {
			// Consulta y ejecucion
			String sql = "load data infile \'"+Url+"\' into table productos fields terminated by \',\' lines terminated by \'\r\\n\'";
			ps = conec.prepareStatement(sql);
			// Si inserta usuario, success
			if(ps.executeUpdate()>0) {	
				list.add("success");
				list.add("Productos cargados correctamente");
				// Error, no encontrado
			} 
			conec.close();
		// Si hay error en el SQL	
		}catch(SQLException e) {
			list.add("error");
			list.add("Error: "+e);
		}
		// Retorno los mensajes
		return list;
	}
	
	
	/**
	 * Consultar productos, usada para recuperar informacion de usuarios
	 * @return List<String>
	 */
	public List<String> consultar(ProductosDTO producto) {
		// List para retornar los mensajes
		List<String> list = new ArrayList<>();
		// Recupero la cedula
		long cod = producto.getCodigo_producto();
		// Si cedula es 0, completar
		if(cod==0) {
			list.add("warning");
			list.add("Complete todos los campos");
		} else {
			// Intento ejecucion SQL
			try {
				// Consulta y ejecucion
				String sql = "select codigo_producto,nombre_producto,precio_compra,porcentaje_utilidad from productos where codigo_producto = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, cod);
				res = ps.executeQuery();
				// Si consigue usuario, success
				if(res.next()) {	
					list.add("success");
					list.add(res.getString(1));
					list.add(res.getString(2));
					list.add(res.getString(3));
					list.add(res.getString(4));
				// Error, no encontrado
				} else {
					list.add("error");
					list.add("Producto no encontrado");
				}
				conec.close();
			// Si hay error en el SQL	
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
			}
		}
		// Retorno los mensajes
		return list;
	}
	
	
	/**
	 * Editar clientes
	 * @return List<String>
	 */
	public List<String> actualizar(ProductosDTO producto) {
		// Lista para retornas respuestas
		List<String> list = new ArrayList<>();
		// Si hay valores vacios, mensaje de completar
		if(producto.getCodigo_producto()==0 || producto.getNombre_producto().isEmpty() || producto.getPorcentaje_utilidad()==0) {
			// Mensajes
			list.add("warning");
			list.add("Complete todos los campos");
		}  else {
			// SQL
			String sql;
			// Intentar SQL
			try {
				// Consulta y ejecucion
				sql = "UPDATE `productos` SET `nombre_producto` = ?,`porcentaje_utilidad` = ? WHERE `productos`.`codigo_producto` = ?;";
				ps = conec.prepareStatement(sql);
				ps.setString(1, producto.getNombre_producto());
				ps.setDouble(2, producto.getPorcentaje_utilidad());
				ps.setLong(3, producto.getCodigo_producto());
				// Si la consulta es exitosa
				if(ps.executeUpdate()==1) {
					// Mensaje success
					list.add("success");
					list.add("Producto actualizado correctamente");
				} else {
					// Mensaje de error
					list.add("error");
					list.add("Error al actualizar producto");
				}
				conec.close();
			// Si hay error en el SQL	
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
			}
		}
		// Retorno las respuestas
		return list;
	}
	
}
