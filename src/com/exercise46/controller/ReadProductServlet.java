package com.exercise46.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.exercise46.model.Product;
import com.google.gson.Gson;

/**
 * Servlet implementation class ReadProductServlet
 */
@WebServlet("/ReadProductServlet")
public class ReadProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html charset='utf-8");
		PrintWriter out = response.getWriter();
		
		Product product = new Product();  //persistence object
		
		int id = Integer.parseInt(request.getParameter("txtIdProduct"));
		
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml");
		
		
		
		//create session factory
		SessionFactory sessionfactory = conf.buildSessionFactory();
		
		//open the session
		Session session = sessionfactory.openSession();
		
		//begin the request
		Transaction trans = session.beginTransaction();
		product = session.load(Product.class, id);
		
		System.out.println(product.toString());
		
		Product myProduct = new Product(product.getId(),product.getName(),product.getPrice());
		
		//close the connection
		session.close();
		
		//convert the product to json
		Gson gson = new Gson();
		String e = gson.toJson(myProduct);
		
		out.append(e);
	
		out.close();
		
	}

}
