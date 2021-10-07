package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import model.ProductosDAO;

/**
 * Servlet implementation class CargarProductos
 */
@WebServlet("/CargarProductos")
@MultipartConfig
public class CargarProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarProductos() {
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
		
		// DAO
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
		} else {
		
			Part archivo = request.getPart("file-upload");
			String Url="C:/Users/Jehison Orostegui/git/Equipo8BrainDevelopers08/tecnoshop/src/main/webapp/documents/";
						
			try {
				InputStream file = archivo.getInputStream();
				File copia = new File(Url+"productos.csv");
				FileOutputStream escribir = new FileOutputStream(copia);
				int num = file.read();
				while(num!=-1) {
					escribir.write(num);
					num = file.read();
				}
				file.close();
				escribir.close();
				resp = prodDAO.cargar(Url+"productos.csv");
				list.add(resp.get(0));
				list.add(resp.get(1));
				// Convertir a JSON	y responder		
				String json = new Gson().toJson(list);
				response.getWriter().write(json);
			}catch(Exception e) {
				
			}
		}
		
	}

}
