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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Recupero los valores enviado en el formulario de login
		String user = request.getParameter("user");
	    String pass = request.getParameter("pass");
	    
	    // Si es ajax
	    boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

	    if (ajax) {
	    	// Objetos para datos e inicio de sesion
	    	UsuarioDTO userDTO = new UsuarioDTO();
			UsuarioDAO userDAO = new UsuarioDAO();
			
			// Listas para recuperar la respues del DAO y enviar al JSP
			List<String> list = new ArrayList<>();
			List<String> res = new ArrayList<>();
			
			userDTO.setUsuario(user);
			userDTO.setPassword(pass);
			res = userDAO.login(userDTO);
			
			list.add(res.get(0));
			list.add(res.get(1));
			
			if(res.get(0)=="success") {
				HttpSession sesion = request.getSession();
				sesion.setAttribute("id", res.get(1));
				sesion.setAttribute("rol", res.get(2));
				sesion.setAttribute("usuario", res.get(3));
			}
			
			String json = new Gson().toJson(list);

		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
			
	    }
			
	}

}
