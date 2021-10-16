package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.Connect;

public class ClienteDAO {
	
	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;
	
	/**
	 * Listar clientes, usada para recuperar todos los clientes registrados
	 * @return ArrayList<ClienteDTO>
	 */
	public ArrayList<ClienteDTO> listar() {
		
		// ArrayList donde se almacenan todos los clientes recuperados
		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
		
		try {
			// Consulta y ejecucion
			String sql = "select * from clientes";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			// Ciclo para almacenar en el ArrayList los clientes
			while (res.next()) {
				ClienteDTO cliente = new ClienteDTO(res.getLong("cedula_cliente"), res.getString("direccion_cliente"), res.getString("email_cliente"), res.getString("nombre_cliente"), res.getString("telefono_cliente"));
				clientes.add(cliente);
			}
			conec.close();
		}catch(SQLException e) {
			// Si hay error en SQL
		}
		// Retornamos los clientes
		return clientes;
		
	}
	
	/**
	 * Crear clientes
	 * @return List<String>
	 */
	public List<String> crear(ClienteDTO cliente) {
		// Lista para retornar mensajes del servidor
		List<String> list = new ArrayList<>();
		// Si hay valores vacios, mensaje de completar
		if(cliente.getCedula_cliente()==0 || cliente.getDireccion_cliente().isEmpty() || cliente.getEmail_cliente().isEmpty() || cliente.getNombre_cliente().isEmpty() || cliente.getTelefono_cliente().isEmpty()) {
			// Mensajes
			list.add("warning");
			list.add("Complete todos los campos");
		} else {
			// SQL
			String sql;
			try {
				// Consulta y ejecucion
				sql = "select * from clientes where cedula_cliente = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, cliente.getCedula_cliente());
				res = ps.executeQuery();
				if(res.next()) {
					// Si hay campos error en la cedula
					list.add("error");
					list.add("El número de cédula ya se encuentra registrado");
				} else {
					// Consulta y ejecucion
					sql = "select * from clientes where email_cliente = ?";
					ps = conec.prepareStatement(sql);
					ps.setString(1, cliente.getEmail_cliente());
					res = ps.executeQuery();
					if(res.next()) {
						// Si hay campos, error en el email
						list.add("error");
						list.add("El email ya se encuentra registrado");
					} else {
						// Consulta y ejecucion
						sql = "select * from clientes where telefono_cliente = ?";
						ps = conec.prepareStatement(sql);
						ps.setString(1, cliente.getTelefono_cliente());
						res = ps.executeQuery();
						if(res.next()) {
							// Si hay campos, error en el telefono
							list.add("error");
							list.add("El teléfono ya se encuentra registrado");
						} else {
							// Consulta y ejecucion del registro
							sql = "INSERT INTO `clientes` (`cedula_cliente`, `direccion_cliente`, `email_cliente`, `nombre_cliente`, `telefono_cliente`) VALUES (?,?,?,?,?)";
							ps = conec.prepareStatement(sql);
							ps.setLong(1, cliente.getCedula_cliente());
							ps.setString(2, cliente.getDireccion_cliente());
							ps.setString(3, cliente.getEmail_cliente());
							ps.setString(4, cliente.getNombre_cliente());
							ps.setString(5, cliente.getTelefono_cliente());
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
	public List<String> consultar(ClienteDTO cliente) {
		// List para retornar los mensajes
		List<String> list = new ArrayList<>();
		// Recupero la cedula
		long cc = cliente.getCedula_cliente();
		// Si cedula es 0, completar
		if(cc==0) {
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
				String sql = "select * from clientes where cedula_cliente = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, cc);
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
	public List<String> actualizar(ClienteDTO cliente) {
		// Lista para retornas respuestas
		List<String> list = new ArrayList<>();
		// Si hay valores vacios, mensaje de completar
		if(cliente.getCedula_cliente()==0 || cliente.getDireccion_cliente().isEmpty() || cliente.getEmail_cliente().isEmpty() || cliente.getNombre_cliente().isEmpty() || cliente.getTelefono_cliente().isEmpty()) {
			// Mensajes
			list.add("warning");
			list.add("Complete todos los campos");
		}  else {
			// SQL
			String sql;
			// Intentar SQL
			try {
				// Consulta y ejecucion
				sql = "UPDATE `clientes` SET `direccion_cliente` = ?,`email_cliente` = ?, `nombre_cliente` = ?, `telefono_cliente` = ? WHERE `clientes`.`cedula_cliente` = ?;";
				ps = conec.prepareStatement(sql);
				ps.setString(1, cliente.getDireccion_cliente());
				ps.setString(2, cliente.getEmail_cliente());
				ps.setString(3, cliente.getNombre_cliente());
				ps.setString(4, cliente.getTelefono_cliente());
				ps.setLong(5, cliente.getCedula_cliente());
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
	public List<String> eliminar(ClienteDTO cliente) {
		// Lista para retornar mensajes
		List<String> list = new ArrayList<>();
		// Recupero el valor de cc
		long cc = cliente.getCedula_cliente();
		// Si cc es 0, completar
		if(cc==0) {
			list.add("warning");
			list.add("Complete todos los campos");
		} else {
			// Intentar SQL
			try {
				// Consulta y ejecucion
				String sql = "DELETE FROM `clientes` WHERE `clientes`.`cedula_cliente` = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, cliente.getCedula_cliente());
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
