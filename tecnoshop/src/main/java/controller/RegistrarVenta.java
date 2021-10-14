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

import model.VentasDAO;
import model.VentasDTO;

/**
 * Servlet implementation class RegistrarVenta
 */
@WebServlet("/RegistrarVenta")
public class RegistrarVenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarVenta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String jsonVenta = request.getParameter("datos");
		
		VentasDAO ventaDAO = new VentasDAO();
		HttpSession sesion = request.getSession();
		
		// Listas para recuperar la respuesta del DAO y enviar al JSP
		List<String> list = new ArrayList<>();
		List<String> resp = new ArrayList<>();
		
		// Setear la respuesta
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
				
		Gson gson = new Gson();
	    VentasDTO ventaDTO = gson.fromJson(jsonVenta,VentasDTO.class);
	    ventaDTO.setCedula_usuario(Long.parseLong((String) sesion.getAttribute("id")));
	    // Obtener y agregar resultados
	 	resp = ventaDAO.registrar(ventaDTO);	
	 	
	 	list.add(resp.get(0));
		list.add(resp.get(1));
	        
	    // Convertir a JSON	y responder		
	 	String json = new Gson().toJson(list);
	 	response.getWriter().write(json);
		
	}

}
