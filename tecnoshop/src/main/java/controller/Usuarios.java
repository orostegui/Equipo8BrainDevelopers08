package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.UsuarioDAO;
import model.UsuarioDTO;

/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/Usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Usuarios() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
		response.getWriter().append("<h1>NO DEBERIAS ESTAR AQUI<h1> <img src='./assets/img/no.gif'>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// DTO & DAO
		UsuarioDTO userDTO = new UsuarioDTO();
		UsuarioDAO userDAO = new UsuarioDAO();
				
		// Listas para recuperar la respuesta del DAO y enviar al JSP
		List<String> list = new ArrayList<>();
		List<String> resp = new ArrayList<>();
				
		// Setear la respuesta
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
							
		HttpSession sesion = request.getSession();
		Object usuario = (String) sesion.getAttribute("usuario");
		if(usuario==null){
			list.add("error");
			list.add("No tienes una sesión válida, refresca e inicia sesión");
			// Convertir a JSON	y responder		
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		// Muestra inicial de usuarios, valida si viene declarado el ini	
		} else if(Boolean.parseBoolean(request.getParameter("ini"))) {
					
			ArrayList<UsuarioDTO> res = userDAO.listar();
			String jsonUsers = new Gson().toJson(res);
			response.getWriter().write(jsonUsers);
					
		// Consulta de usuarios para editar, valida si viene declarado el con
		} else if(Boolean.parseBoolean(request.getParameter("con"))) {
			
			// Recupero los valores enviado en el formulario de consulta de usuarios
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			// Guardo el codigo en el DTO
			userDTO.setCedula_usuario(cc);
			// Obtener y agregar resultados
			resp = userDAO.consultar(userDTO);
			list.add(resp.get(0));
			list.add(resp.get(1));
			list.add(resp.get(2));
			list.add(resp.get(3));
			list.add(resp.get(4));
			// Convertir a JSON	y responder		
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		
		// Crear usuarios, valida si viene declarado el cre
		} else if(Boolean.parseBoolean(request.getParameter("cre"))) {
						
			// Valido si la cedula viene declarada
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			// Guardamos los valores en el DTO
			userDTO.setCedula_usuario(cc);
			userDTO.setUsuario(request.getParameter("us"));
			userDTO.setNombre_usuario(request.getParameter("nc"));
			userDTO.setEmail_usuario(request.getParameter("em"));
			userDTO.setPassword(request.getParameter("cc"));
			// Obtener y agregar resultados
			resp = userDAO.crear(userDTO);
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
						
		// Editar productos, valida si viene declarado el edit
		} else if(Boolean.parseBoolean(request.getParameter("edit"))) {
			
			// Valido si la cedula viene declarada
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			// Guardamos los valores en el DTO
			userDTO.setCedula_usuario(cc);
			userDTO.setUsuario(request.getParameter("us"));
			userDTO.setNombre_usuario(request.getParameter("nc"));
			userDTO.setEmail_usuario(request.getParameter("em"));
			userDTO.setPassword(request.getParameter("pw"));
			// Obtener y agregar resultados
			resp = userDAO.actualizar(userDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
			
		// Editar productos, valida si viene declarado el edit
		} else if(Boolean.parseBoolean(request.getParameter("del"))) {
			
			// Valido si la cedula viene declarada
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			userDTO.setCedula_usuario(cc);
			// Obtener y agregar resultados
			resp = userDAO.eliminar(userDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
			
		}
				
	}

}
