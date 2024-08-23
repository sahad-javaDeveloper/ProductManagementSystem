package com.product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;



@WebServlet("/register")//these is used to map the servlet

public class ProductServlet extends HttpServlet {//hypertext(hypermedia) may be html 
	private static final long serialVersionUID = 1L;

	@Override
	protected void  doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action=	request.getParameter("action");
		System.out.println(action);
	
	switch(action) {
	
	case "create":
		
		createTheProduct(request, response);
	case "update":
		
		try {
			showUpdateForm(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	case "delete":
		try {
			deleteTheData(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	case "read":
		
		try {
			readTheProduct(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	}
	private void createTheProduct(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addProduct.html").forward(request, response);
	}
	
	private void readTheProduct(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		ProductDao dao=new ProductDao();
		List<Product>listOfProduct=	dao.readTheData();
		
		request.setAttribute("list", listOfProduct);// this is used to set the object to the jsp page
		request.getRequestDispatcher("ListOfProducts.jsp").forward(request, response);
		
	
	}
	private void showUpdateForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		System.out.println("update");
		ProductDao dao=new ProductDao();
	
		
		Integer id= Integer.parseInt(request.getParameter("id"));//id is extracted from the url
	Product product=	dao.retriveTheDataForUpdateForm(id);
		request.setAttribute("product", product);
		
		request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
		
		
		
		
	}
	
	
	
	@Override
	protected void  doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");	
		if(action.equals("create")) {
			saveTheData(request, response);
		}
		else if(action.equals("update")) {
			try {
				updateTheData(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void updateTheData(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		
		System.out.println("update page ");
		Integer id= Integer.parseInt(request.getParameter("productId"));
		String productName=	request.getParameter("productName");
		double productPrice=Double.parseDouble(request.getParameter("productPrice"));
		ProductDao dao=new ProductDao();
		System.out.println(productName);
		System.out.println(productPrice);
		dao.updateTheData(id,productName, productPrice);
		
	response.sendRedirect("/ProductManagementSystem/register?action=read");
	}
public void saveTheData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String productName=	request.getParameter("productName");
double productPrice=Double.parseDouble(request.getParameter("productPrice"));
ProductDao dao=new ProductDao();
try {
	dao.createTheProduct(productName, productPrice);
} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

request.getRequestDispatcher("success.jsp").forward(request, response);

	}
	
	
public void deleteTheData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {

	Integer id= Integer.parseInt(request.getParameter("id"));//id is extracted from the url
	
	ProductDao dao=new ProductDao();
	dao.deletetheData(id);
}
}
