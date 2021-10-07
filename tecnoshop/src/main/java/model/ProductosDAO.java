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
			String sql = "select * from productos";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				ProductosDTO producto = new ProductosDTO(res.getLong("codigo_producto"), res.getDouble("ivacompra"), res.getLong("nitproveedor"), res.getString("nombre_producto"),res.getDouble("precio_compra"),res.getDouble("precio_venta"));
				productos.add(producto);
			}
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return productos;
	}
	
	/**
	 * Crear proveedores
	 * @return List<String>
	 */
	public List<String> crear(ProveedorDTO proveedor) {
		// Lista para retornar mensajes del servidor
		List<String> list = new ArrayList<>();
		// Si hay valores vacios, mensaje de completar
		if(proveedor.getNitproveedor()==0 || proveedor.getCiudad_proveedor().isEmpty() || proveedor.getDireccion_proveedor().isEmpty() || proveedor.getNombre_proveedor().isEmpty() || proveedor.getTelefono_proveedor().isEmpty()) {
			// Mensajes
			list.add("warning");
			list.add("Complete todos los campos");
		} else {
			// SQL
			String sql;
			try {
				// Consulta y ejecucion
				sql = "select * from proveedores where nitproveedor = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, proveedor.getNitproveedor());
				res = ps.executeQuery();
				if(res.next()) {
					// Si hay campos error en la cedula
					list.add("error");
					list.add("El NIT ya se encuentra registrado");
				} else {
					// Consulta y ejecucion
					sql = "select * from proveedores where telefono_proveedor = ?";
					ps = conec.prepareStatement(sql);
					ps.setString(1, proveedor.getTelefono_proveedor());
					res = ps.executeQuery();
					if(res.next()) {
						// Si hay campos, error en el email
						list.add("error");
						list.add("El teléfono ya se encuentra registrado");
					} else {
						// Consulta y ejecucion del registro
						sql = "INSERT INTO `proveedores` (`nitproveedor`, `ciudad_proveedor`, `direccion_proveedor`, `nombre_proveedor`, `telefono_proveedor`) VALUES (?,?,?,?,?)";
						ps = conec.prepareStatement(sql);
						ps.setLong(1, proveedor.getNitproveedor());
						ps.setString(2, proveedor.getCiudad_proveedor());
						ps.setString(3, proveedor.getDireccion_proveedor());
						ps.setString(4, proveedor.getNombre_proveedor());
						ps.setString(5, proveedor.getTelefono_proveedor());
						if(ps.executeUpdate()==1) {
							// Si ejecuto correctamente, success
							list.add("success");
							list.add("Usuario creado correctamente");
						} else {
							// Error en la consulta
							list.add("error");
							list.add("Error al crear usuario");
						}
					}
				}
			// Error en el SQL	
			}catch(SQLException e) {
				// Muestra error
				list.add("error");
				list.add("Error: "+e);
			}
		}
		// Retorno la lista con respuestas
		return list;		
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
			list.add("");
			list.add("");
		} else {
			// Intento ejecucion SQL
			try {
				// Consulta y ejecucion
				String sql = "select codigo_producto,nombre_producto,precio_venta from productos where codigo_producto = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, cod);
				res = ps.executeQuery();
				// Si consigue usuario, success
				if(res.next()) {	
					list.add("success");
					list.add(res.getString(1));
					list.add(res.getString(2));
					list.add(res.getString(3));
				// Error, no encontrado
				} else {
					list.add("error");
					list.add("Producto no encontrado");
					list.add("");
					list.add("");
				}
			// Si hay error en el SQL	
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
				list.add("");
				list.add("");
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
		if(producto.getCodigo_producto()==0 || producto.getNombre_producto().isEmpty() || producto.getPrecio_venta()==0) {
			// Mensajes
			list.add("warning");
			list.add("Complete todos los campos");
		}  else {
			// SQL
			String sql;
			// Intentar SQL
			try {
				// Consulta y ejecucion
				sql = "UPDATE `productos` SET `nombre_producto` = ?,`precio_venta` = ? WHERE `productos`.`codigo_producto` = ?;";
				ps = conec.prepareStatement(sql);
				ps.setString(1, producto.getNombre_producto());
				ps.setDouble(2, producto.getPrecio_venta());
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
