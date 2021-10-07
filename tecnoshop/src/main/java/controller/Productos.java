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

import model.ProductosDAO;
import model.ProductosDTO;

/**
 * Servlet implementation class Productos
 */
@WebServlet("/Productos")
public class Productos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Productos() {
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
		ProductosDTO prodDTO = new ProductosDTO();
		ProductosDAO prodDAO = new ProductosDAO();
		
		// Listas para recuperar la respues del DAO y enviar al JSP
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
			
			ArrayList<ProductosDTO> res = prodDAO.listar();
			String jsonProducts = new Gson().toJson(res);
			response.getWriter().write(jsonProducts);
			
		} else if(Boolean.parseBoolean(request.getParameter("con"))) {
			
			// Recupero los valores enviado en el formulario de consulta de productos
			long cod = (request.getParameter("cod").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cod"));
			// Guardo el codigo en el DTO
			prodDTO.setCodigo_producto(cod);
			
			// Obtener y agregar resultados
			resp = prodDAO.consultar(prodDTO);
			list.add(resp.get(0));
			list.add(resp.get(1));
			list.add(resp.get(2));
			list.add(resp.get(3));
			// Convertir a JSON	y responder		
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
		
		// Editar productos, valida si viene declarado el edit
		} else if(Boolean.parseBoolean(request.getParameter("edit"))) {
			
			// Recupero los valores enviado en el formulario de editar producto
			long cod = (request.getParameter("cod").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cod"));
			String nb = request.getParameter("nb");
			double pv = (request.getParameter("pv").isEmpty()) ? 0 : Double.parseDouble(request.getParameter("pv"));
			// Guardamos los valores en el DTO
			prodDTO.setCodigo_producto(cod);
			prodDTO.setNombre_producto(nb);
			prodDTO.setPrecio_venta(pv);
			// Obtener y agregar resultados
			resp = prodDAO.actualizar(prodDTO);	
			list.add(resp.get(0));
			list.add(resp.get(1));
			// Convertir a JSON	y responder										
			String json = new Gson().toJson(list);
			response.getWriter().write(json);
			
		}
				
	}

}
