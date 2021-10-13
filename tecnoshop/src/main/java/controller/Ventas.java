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
import model.ProductosDAO;
import model.ProductosDTO;
import model.VentasDAO;
import model.VentasDTO;

/**
 * Servlet implementation class Ventas
 */
@WebServlet("/Ventas")
public class Ventas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ventas() {
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


		VentasDTO ventasDTO = new VentasDTO();
		VentasDAO ventasDAO = new VentasDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
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
		} else if(Boolean.parseBoolean(request.getParameter("cli"))) {
			
			ArrayList<ClienteDTO> res = clienteDAO.listar();
			String jsonCLientes = new Gson().toJson(res);
			response.getWriter().write(jsonCLientes);
			
		} else if(Boolean.parseBoolean(request.getParameter("pro"))) {
			
			ArrayList<ProductosDTO> res = prodDAO.listar();
			String jsonProductos = new Gson().toJson(res);
			response.getWriter().write(jsonProductos);
			
		}
		
	}

}
