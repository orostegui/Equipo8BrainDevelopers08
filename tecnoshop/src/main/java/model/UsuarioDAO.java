package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import controller.Connect;

public class UsuarioDAO {
	
	Connect con = new Connect();
	Connection conec = con.Conecta();
	PreparedStatement ps = null;
	ResultSet res = null;	

	/**
	 * Login Usuarios, usada para crear las sesiones de los usuarios
	 * @return List<String>
	 */
	public List<String> login(UsuarioDTO user) {
		// Lista para retornar mensajes
		List<String> list = new ArrayList<>();
		// Recupero los valores recibidos
		String us = user.getUsuario();
		String pw = user.getPassword();
		// Si vienen vacios, erros para completar
		if(us.isEmpty()||pw.isEmpty()) {
			list.add("warning");
			list.add("Complete todos los campos");
		} else {
			// Intento ejecutar SQL
			try {
				// Consulta y ejecucion
				String sql = "select * from usuarios where usuario = ?";
				ps = conec.prepareStatement(sql);
				ps.setString(1, user.getUsuario());
				res = ps.executeQuery();
				// Si consigue un usuario, validar password
				if(res.next()) {
					// Recupero password de la db y encripto la recibida por el usuario
					String passwordDatabase = res.getString(5);
					String passEncript=DigestUtils.md5Hex(user.getPassword());
					// Si los dos hash de password coinciden, success
					if(passEncript.equals(passwordDatabase)) {
						list.add("success");
						list.add(res.getString(1));
						list.add(res.getString(2));
						list.add(res.getString(6));
					// Password errada
					} else {
						list.add("error");
						list.add("Contraseña incorrecta");
					}
				// No se consiguio el usuario
				} else {
					list.add("error");
					list.add("Usuario no registrado");
				}
			// Error en SQL
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
			}
		}
		// Retorno los mensajes
		return list;
	}
	
	
	/**
	 * Consultar Usuarios, usada para recuperar informacion de usuarios
	 * @return List<String>
	 */
	public List<String> consultar(UsuarioDTO user) {
		// List para retornar los mensajes
		List<String> list = new ArrayList<>();
		// Recupero la cedula
		long cc = user.getCedula_usuario();
		// Si cedula es 0, completar
		if(cc==0) {
			list.add("warning");
			list.add("Complete todos los campos");
			list.add("");
			list.add("");
			list.add("");
		} else {
			// Intento ejecucion SQL
			try {
				// Consulta y ejecucion
				String sql = "select * from usuarios where cedula_usuario = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, user.getCedula_usuario());
				res = ps.executeQuery();
				// Si consigue usuario, success
				if(res.next()) {	
					list.add("success");
					list.add(res.getString(1));
					list.add(res.getString(3));
					list.add(res.getString(4));
					list.add(res.getString(6));
				// Error, no encontrado
				} else {
					list.add("error");
					list.add("Usuario no encontrado");
					list.add("");
					list.add("");
					list.add("");
				}
			// Si hay error en el SQL	
			}catch(SQLException e) {
				list.add("error");
				list.add("Error: "+e);
				list.add("");
				list.add("");
				list.add("");
			}
		}
		// Retorno los mensajes
		return list;
	}
	
	
	
	public List<String> eliminar(UsuarioDTO user) {
			
			List<String> list = new ArrayList<>();
			
			long cc = user.getCedula_usuario();
			
			if(cc==0) {
				
				list.add("warning");
				list.add("Complete todos los campos");
				
			} else {
			
				try {
					
					String sql = "DELETE FROM `usuarios` WHERE `usuarios`.`cedula_usuario` = ?";
					ps = conec.prepareStatement(sql);
					ps.setLong(1, user.getCedula_usuario());
					if(ps.executeUpdate()==1) {
							
						list.add("success");
						list.add("Usuario eliminado correctamente");
						
					} else {
						
						list.add("error");
						list.add("Usuario no encontrado");
						
					}
					
				}catch(SQLException e) {
					
					list.add("error");
					list.add("Error: "+e);
					
				}
			}
			
			return list;
			
			
			
	}
	
	
	public List<String> crear(UsuarioDTO user) {
		
		List<String> list = new ArrayList<>();
		
		if(user.getCedula_usuario()==0 || user.getEmail_usuario().isEmpty() || user.getNombre_usuario().isEmpty() || user.getUsuario().isEmpty()) {
			
			list.add("warning");
			list.add("Complete todos los campos");
			
		} else {
			
			String sql;
		
			try {
				
				sql = "select * from usuarios where cedula_usuario = ?";
				ps = conec.prepareStatement(sql);
				ps.setLong(1, user.getCedula_usuario());
				res = ps.executeQuery();
				if(res.next()) {
						
					list.add("error");
					list.add("El número de cédula ya se encuentra registrado");
					
				} else {
					
					sql = "select * from usuarios where email_usuario = ?";
					ps = conec.prepareStatement(sql);
					ps.setString(1, user.getEmail_usuario());
					res = ps.executeQuery();
					if(res.next()) {
						
						list.add("error");
						list.add("El email ya se encuentra registrado");
						
					} else {
						
						sql = "select * from usuarios where usuario = ?";
						ps = conec.prepareStatement(sql);
						ps.setString(1, user.getUsuario());
						res = ps.executeQuery();
						if(res.next()) {
							
							list.add("error");
							list.add("El usuario ya se encuentra registrado");
							
						} else {
							
							String passEncript=DigestUtils.md5Hex(user.getPassword());
							sql = "INSERT INTO `usuarios` (`cedula_usuario`, `rol`, `email_usuario`, `nombre_usuario`, `password`, `usuario`) VALUES (?,2,?,?,?,?)";
							ps = conec.prepareStatement(sql);
							ps.setLong(1, user.getCedula_usuario());
							ps.setString(2, user.getEmail_usuario());
							ps.setString(3, user.getNombre_usuario());
							ps.setString(4, passEncript);
							ps.setString(5, user.getUsuario());
							if(ps.executeUpdate()==1) {
								
								list.add("success");
								list.add("Usuario creado correctamente");
								
							} else {
								
								list.add("error");
								list.add("Error al crear usuario");
								
							}
							
						}
					}
					
				}
				
			}catch(SQLException e) {
				
				list.add("error");
				list.add("Error: "+e);
				
			}
		}
		
		return list;
			
	}
	
	
	public ArrayList<UsuarioDTO> listar() {
		
		// Objetos para datos e inicio de sesion
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		
		try {
			
			String sql = "select * from usuarios";
			ps = conec.prepareStatement(sql);
			res = ps.executeQuery();
			
			while (res.next()) {
				if(res.getInt("rol")==2) {
					UsuarioDTO user = new UsuarioDTO(res.getLong("cedula_usuario"), res.getString("email_usuario"), res.getString("nombre_usuario"), res.getString("usuario"));
					usuarios.add(user);
				}
			}
			
		}catch(SQLException e) {
			
		}
		
		return usuarios;
		
	}
	
	public List<String> actualizar(UsuarioDTO user) {
		
		List<String> list = new ArrayList<>();
		
		if(user.getCedula_usuario()==0 || user.getEmail_usuario().isEmpty() || user.getNombre_usuario().isEmpty() || user.getUsuario().isEmpty()) {
			
			list.add("warning");
			list.add("Complete todos los campos");
			
		} else {
			
			String sql;
		
			try {
				
				if(user.getPassword().isEmpty()) {
					sql = "UPDATE `usuarios` SET `email_usuario` = ?, `nombre_usuario` = ?,`usuario` = ? WHERE `usuarios`.`cedula_usuario` = ?;";
					ps = conec.prepareStatement(sql);
					ps.setString(1, user.getEmail_usuario());
					ps.setString(2, user.getNombre_usuario());
					ps.setString(3, user.getUsuario());
					ps.setLong(4, user.getCedula_usuario());
					
				} else {
					String passEncript=DigestUtils.md5Hex(user.getPassword());
					sql = "UPDATE `usuarios` SET `email_usuario` = ?, `nombre_usuario` = ?,`password` = ?,`usuario` = ? WHERE `usuarios`.`cedula_usuario` = ?;";
					ps = conec.prepareStatement(sql);
					ps.setString(1, user.getEmail_usuario());
					ps.setString(2, user.getNombre_usuario());
					ps.setString(3, passEncript);
					ps.setString(4, user.getUsuario());
					ps.setLong(5, user.getCedula_usuario());
				}
				
				if(ps.executeUpdate()==1) {
					
					list.add("success");
					list.add("Usuario actualizado correctamente");
					
				} else {
					
					list.add("error");
					list.add("Error al actualizar usuario");
					
				}
				
			}catch(SQLException e) {
				
				list.add("error");
				list.add("Error: "+e);
				
			}
		}
		
		return list;
			
	}
	
}
