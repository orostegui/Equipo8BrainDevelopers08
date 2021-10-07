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

import model.ClienteDAO;
import model.ClienteDTO;

/**
 * Servlet implementation class Clientes
 */
@WebServlet("/Clientes")
public class Clientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Clientes() {
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
		ClienteDTO cliDTO = new ClienteDTO();
		ClienteDAO cliDAO = new ClienteDAO();
						
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
							
			ArrayList<ClienteDTO> res = cliDAO.listar();
			String jsonUsers = new Gson().toJson(res);
			response.getWriter().write(jsonUsers);
							
		// Consulta de usuarios para editar, valida si viene declarado el con
		} else if(Boolean.parseBoolean(request.getParameter("con"))) {
						
			// Valido si la cedula viene declarada
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			// Guardo la cedula en el DTO
			cliDTO.setCedula_cliente(cc);
			// Obtener y agregar resultados
			resp = cliDAO.consultar(cliDTO);
			list.add(resp.get(0));
			list.add(resp.get(1));
			list.add(resp.get(2));
			list.add(resp.get(3));
			list.add(resp.get(4));
			list.add(resp.get(5));
			// Convertir a JSON	y responder		
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
					
		// Crear clientes, valida si viene declarado el cre
		} else if(Boolean.parseBoolean(request.getParameter("cre"))) {
									
			// Valido si la cedula viene declarada
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			// Guardamos los valores en el DTO
			cliDTO.setCedula_cliente(cc);
			cliDTO.setNombre_cliente(request.getParameter("nc"));
			cliDTO.setDireccion_cliente(request.getParameter("dr"));
			cliDTO.setEmail_cliente(request.getParameter("em"));
			cliDTO.setTelefono_cliente(request.getParameter("tf"));
			
			// Obtener y agregar resultados
			resp = cliDAO.crear(cliDTO);
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
			cliDTO.setCedula_cliente(cc);
			cliDTO.setNombre_cliente(request.getParameter("nc"));
			cliDTO.setDireccion_cliente(request.getParameter("dr"));
			cliDTO.setEmail_cliente(request.getParameter("em"));
			cliDTO.setTelefono_cliente(request.getParameter("tf"));
			// Obtener y agregar resultados
			resp = cliDAO.actualizar(cliDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
						
		// Eliminar clientes, valida si viene declarado el edit
		} else if(Boolean.parseBoolean(request.getParameter("del"))) {
						
			// Valido si la cedula viene declarada
			long cc = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
			cliDTO.setCedula_cliente(cc);
			// Obtener y agregar resultados
			resp = cliDAO.eliminar(cliDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
			
		}
		
	}

}
