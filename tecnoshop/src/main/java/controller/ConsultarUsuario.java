package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.UsuarioDAO;
import model.UsuarioDTO;

/**
 * Servlet implementation class ConsultarUsuario
 */
@WebServlet("/ConsultarUsuario")
public class ConsultarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Recupero los valores enviado en el formulario de login
		long id = (request.getParameter("cc").isEmpty()) ? 0 : Long.parseLong(request.getParameter("cc"));
				
		// Objetos para datos e inicio de sesion
		UsuarioDTO userDTO = new UsuarioDTO(id);
		UsuarioDAO userDAO = new UsuarioDAO();
					
		// Listas para recuperar la respues del DAO y enviar al JSP
		List<String> list = new ArrayList<>();
		List<String> res = new ArrayList<>();
					
		res = userDAO.consultar(userDTO);
		
		list.add(res.get(0));
		list.add(res.get(1));
		list.add(res.get(2));
		list.add(res.get(3));
		list.add(res.get(4));
					
		String json = new Gson().toJson(list);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
	}

}
