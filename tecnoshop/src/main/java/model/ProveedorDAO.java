package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.Connect;

public class ProveedorDAO {
	
	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;
	
	/**
	 * Listar proveedores, usada para recuperar todos los proveedores registrados
	 * @return ArrayList<ClienteDTO>
	 */
	public ArrayList<ProveedorDTO> listar() {
		
		// ArrayList donde se almacenan todos los clientes recuperados
		ArrayList<ProveedorDTO> proveedores = new ArrayList<ProveedorDTO>();
		
		try {
			// Consulta y ejecucion
			String sql = "select * from proveedores";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				ProveedorDTO proveedor = new ProveedorDTO(res.getLong("nitproveedor"), res.getString("ciudad_proveedor"), res.getString("direccion_proveedor"), res.getString("nombre_proveedor"), res.getString("telefono_proveedor"));
				proveedores.add(proveedor);
			}
			conec.close();
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return proveedores;
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
				conec.close();
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
	 * Consultar Clientes, usada para recuperar informacion de usuarios
	 * @return List<String>
	 */
	public List<String> consultar(ProveedorDTO proveedor) {
		// List para retornar los mensajes
		List<String> list = new ArrayList<>();
		// Recupero la cedula
		long nit = proveedor.getNitproveedor();
		// Si cedula es 0, completar
		if(nit==0) {
			list.add("warning");
			list.add("Complete todos los campos");
			list.add("");
			list.add("");
			list.add("");
			list.add("");
		} else {
			// Intento ejecucion SQL
			try {
				// Consulta y ejecucion
				String sql = "select * from proveedores where nitproveedor = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, nit);
				res = ps.executeQuery();
				// Si consigue usuario, success
				if(res.next()) {	
					list.add("success");
					list.add(res.getString(1));
					list.add(res.getString(2));
					list.add(res.getString(3));
					list.add(res.getString(4));
					list.add(res.getString(5));
				// Error, no encontrado
				} else {
					list.add("error");
					list.add("Usuario no encontrado");
					list.add("");
					list.add("");
					list.add("");
					list.add("");
				}
				conec.close();
			// Si hay error en el SQL	
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
				list.add("");
				list.add("");
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
	public List<String> actualizar(ProveedorDTO proveedor) {
		// Lista para retornas respuestas
		List<String> list = new ArrayList<>();
		// Si hay valores vacios, mensaje de completar
		if(proveedor.getNitproveedor()==0 || proveedor.getCiudad_proveedor().isEmpty() || proveedor.getDireccion_proveedor().isEmpty() || proveedor.getNombre_proveedor().isEmpty() || proveedor.getTelefono_proveedor().isEmpty()) {
			// Mensajes
			list.add("warning");
			list.add("Complete todos los campos");
		}  else {
			// SQL
			String sql;
			// Intentar SQL
			try {
				// Consulta y ejecucion
				sql = "UPDATE `proveedores` SET `ciudad_proveedor` = ?,`direccion_proveedor` = ?, `nombre_proveedor` = ?, `telefono_proveedor` = ? WHERE `proveedores`.`nitproveedor` = ?;";
				ps = conec.prepareStatement(sql);
				ps.setString(1, proveedor.getCiudad_proveedor());
				ps.setString(2, proveedor.getDireccion_proveedor());
				ps.setString(3, proveedor.getNombre_proveedor());
				ps.setString(4, proveedor.getTelefono_proveedor());
				ps.setLong(5, proveedor.getNitproveedor());
				// Si la consulta es exitosa
				if(ps.executeUpdate()==1) {
					// Mensaje success
					list.add("success");
					list.add("Usuario actualizado correctamente");
				} else {
					// Mensaje de error
					list.add("error");
					list.add("Error al actualizar usuario");
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
	
	
	/**
	 * Eliminar clientes
	 * @return List<String>
	 */
	public List<String> eliminar(ProveedorDTO proveedor) {
		// Lista para retornar mensajes
		List<String> list = new ArrayList<>();
		// Recupero el valor de nit
		long nit = proveedor.getNitproveedor();
		// Si nit es 0, completar
		if(nit==0) {
			list.add("warning");
			list.add("Complete todos los campos");
		} else {
			// Intentar SQL
			try {
				// Consulta y ejecucion
				String sql = "DELETE FROM `proveedores` WHERE `proveedores`.`nitproveedor` = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, nit);
				// Si se ejecuta correctamente
				if(ps.executeUpdate()==1) {
					list.add("success");
					list.add("Usuario eliminado correctamente");
				} else {
					list.add("error");
					list.add("Error al eliminar");
				}
				conec.close();
			// Si hay error SQL
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
			}
		}
		// Retorno los mensajes
		return list;
	}

}
