package com;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    Payment paymentObj = new Payment();
    
    public PaymentAPI() {
        
    	super();    
    } 
    
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	
		String output = paymentObj.insertPayment(request.getParameter("billId"),
				                           request.getParameter("cardHolderName"), 
				                           request.getParameter("cardType"),
				                           request.getParameter("bank"),
				                           request.getParameter("payAmount"), 
				                           request.getParameter("statuss"));
        response.getWriter().write(output);
	}


    
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
	{ 
			 Map paras = getParasMap(request); 
			 
			 String output = paymentObj.updatePayment(paras.get("hidPaymentIDSave").toString(), 
			                 paras.get("billId").toString(), 
			                 paras.get("cardHolderName").toString(), 
			                 paras.get("cardType").toString(),
			                 paras.get("bank").toString(),
			                 paras.get("payAmount").toString(), 
			                 paras.get("statuss").toString()); 
			     response.getWriter().write(output); 
	} 
    
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{ 
			 Map paras = getParasMap(request); 
			 
			 String output = paymentObj.deletePayment(paras.get("paymentId").toString()); 
			
			 response.getWriter().write(output); 
	}
    
    private static Map getParasMap(HttpServletRequest request) 
 	{ 
 	     Map<String, String> map = new HashMap<String, String>(); 
 	     try
 	     { 
 	         Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
 	         String queryString = scanner.hasNext() ?  scanner.useDelimiter("\\A").next() : ""; 
 	         scanner.close(); 
 	        
 	         String[] params = queryString.split("&"); 
 	         for (String param : params) 
 	         { 
 	              String[] p = param.split("="); 
 	              map.put(p[0], p[1]); 
 	         } 
 	     } 
 	     catch (Exception e) 
 	    { 
 	    } 
 	   return map; 
 	}
    
}
