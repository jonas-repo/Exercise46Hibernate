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
 * Servlet implementation class CreateProductServlet
 */
@WebServlet("/CreateProductServlet")
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html charset='utf-8");
		PrintWriter out = response.getWriter();
		
		Product product = new Product();  //persistence object
		
		product.setName(request.getParameter("txtNameProduct"));
		product.setPrice(Double.parseDouble(request.getParameter("txtPriceProduct")));
		
		//convert to json
		String JsonString = request.getParameter("txtJson");
		Gson goson = new Gson();
		Product myProduct =  new Product();
		
		myProduct = goson.fromJson(JsonString, Product.class);
		
		System.out.println("Contenido de myProduct: "+ myProduct.toString());
		System.out.println(product.toString());
		
		//create configuration object
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml");
		
		//create session factory
		SessionFactory sessionfactory = conf.buildSessionFactory();
		
		//open the session
		Session session = sessionfactory.openSession();
		
		//begin the request
		Transaction trans = session.beginTransaction();
		session.save(product); 
		
		//close the connection
		session.close();
		out.close();
		
		System.out.println("datos guardados");
	}

}
