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

import model.ProveedorDAO;
import model.ProveedorDTO;

/**
 * Servlet implementation class Proveedores
 */
@WebServlet("/Proveedores")
public class Proveedores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Proveedores() {
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
		ProveedorDTO provDTO = new ProveedorDTO();
		ProveedorDAO provDAO = new ProveedorDAO();
								
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
									
			ArrayList<ProveedorDTO> res = provDAO.listar();
			String jsonProviders = new Gson().toJson(res);
			response.getWriter().write(jsonProviders);
									
		// Consulta de proveedores para editar, valida si viene declarado el con
		} else if(Boolean.parseBoolean(request.getParameter("con"))) {
									
			// Valido si la cedula viene declarada
			long nit = (request.getParameter("nit").isEmpty()) ? 0 : Long.parseLong(request.getParameter("nit"));
			// Guardo la cedula en el DTO
			provDTO.setNitproveedor(nit);
			// Obtener y agregar resultados
			resp = provDAO.consultar(provDTO);
			list.add(resp.get(0));
			list.add(resp.get(1));
			list.add(resp.get(2));
			list.add(resp.get(3));
			list.add(resp.get(4));
			list.add(resp.get(5));
			// Convertir a JSON	y responder		
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
								
		// Crear proveedores, valida si viene declarado el cre
		} else if(Boolean.parseBoolean(request.getParameter("cre"))) {
												
			// Valido si nit viene declarada
			long nit = (request.getParameter("nit").isEmpty()) ? 0 : Long.parseLong(request.getParameter("nit"));
			// Guardamos los valores en el DTO
			provDTO.setNitproveedor(nit);
			provDTO.setNombre_proveedor(request.getParameter("nb"));
			provDTO.setDireccion_proveedor(request.getParameter("dr"));
			provDTO.setCiudad_proveedor(request.getParameter("cd"));
			provDTO.setTelefono_proveedor(request.getParameter("tf"));
			
			// Obtener y agregar resultados
			resp = provDAO.crear(provDTO);
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
						
		// Editar proveedores, valida si viene declarado el edit
		} else if(Boolean.parseBoolean(request.getParameter("edit"))) {
									
			// Valido si nit viene declarada
			long nit = (request.getParameter("nit").isEmpty()) ? 0 : Long.parseLong(request.getParameter("nit"));
			// Guardamos los valores en el DTO
			provDTO.setNitproveedor(nit);
			provDTO.setNombre_proveedor(request.getParameter("nb"));
			provDTO.setDireccion_proveedor(request.getParameter("dr"));
			provDTO.setCiudad_proveedor(request.getParameter("cd"));
			provDTO.setTelefono_proveedor(request.getParameter("tf"));
			// Obtener y agregar resultados
			resp = provDAO.actualizar(provDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
									
		// Eliminar clientes, valida si viene declarado el edit
		} else if(Boolean.parseBoolean(request.getParameter("del"))) {
									
			// Valido si nit viene declarada
			long nit = (request.getParameter("nit").isEmpty()) ? 0 : Long.parseLong(request.getParameter("nit"));
			// Guardamos los valores en el DTO
			provDTO.setNitproveedor(nit);
			// Obtener y agregar resultados
			resp = provDAO.eliminar(provDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
						
		}
		
	}

}
