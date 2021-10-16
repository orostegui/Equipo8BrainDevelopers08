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

import model.ReportesDAO;
import model.ReportesDTO;
import model.VentasDTO;

/**
 * Servlet implementation class Reportes
 */
@WebServlet("/Reportes")
public class Reportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reportes() {
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


		ReportesDAO reporDAO = new ReportesDAO();

		// Listas para recuperar la respues del DAO y enviar al JSP
		List<String> list = new ArrayList<>();
				
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
			
			ArrayList<ReportesDTO> res = reporDAO.ventas();
			String jsonCLientes = new Gson().toJson(res);
			response.getWriter().write(jsonCLientes);
			
		} 
	}

}
