package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
	
	
	int billId;
	String cardHolderName;
	String cardType;
	String bank;
	double payAmount;
	String statuss;

	//1. Create a Connection method 
		public Connection connect() 
		{ 
		     Connection con = null; 
		 
		     try { 
		             Class.forName("com.mysql.jdbc.Driver"); 
		            
		              //Provide the correct details: DBServer/DBName, username, password 
		             con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powergrid","root", ""); 
		               
		             //For testing
		             System.out.print("Successfully connected"); 
		            	   
		     } 
		     catch(Exception e) { 
		             e.printStackTrace(); 
		     } 
		 
		     return con; 
		}
		
		
		
		//2. Create the method for get all the payment details
		public String readPayment() {
			
			   String out = ""; 
			   
			   try { 
			          Connection con = connect(); 
			          
			          if (con == null)  { 
			             return "Error while connecting to the database for reading."; 
			          } 
			 
			          // Prepare the html table to be displayed
			          out = "<table border='1'" 
			                   +"<tr><th>Bill ID</th><th>Card Holder Name</th>"
			                   +"<th>Card Type</th><th>Bank</th><th>Pay Amount</th>"
			                   +"<th>Status</th>"
			                   + "<th>Update</th><th>Remove</th></tr>";              
			          
			          String query = "select * from payment"; 
			          Statement stmt = con.createStatement(); 
			          ResultSet rs = stmt.executeQuery(query); 
			
			          // iterate through the rows in the result set
			          while (rs.next()) { 
			        	  
			        	     String paymentId  = Integer.toString(rs.getInt("paymentId")); 
				             String billId = Integer.toString(rs.getInt("billId")); 
				             String cardHolderName = rs.getString("cardHolderName"); 
				             String cardType = rs.getString("cardType"); 
				             String bank = rs.getString("bank"); 
				             String payAmount = Double.toString(rs.getDouble("payAmount")); 
				             String statuss =rs.getString("statuss"); 
				              
				             
			 
				             // Add a row into the html table
				           
				             
				             out += "<tr><td><input id='hidPaymentIDUpdate' "
				     	    		+" name='hidPaymentIDUpdate'" 
				     	    		 +"type='hidden' value='" + paymentId + "'>"
				     	    		 + billId + "</td>";
				     	    out+="<td>"+cardHolderName +"</td><td>"+cardType+"</td><td>"+bank+"</td>";
				     	    out+="<td>"+payAmount +"</td><td>"+statuss+"</td>";
				             
			 
				          // buttons
				     		out+= "<td><input name='btnUpdate' type='button' value='Update' "
				     		+ "class='btnUpdate btn btn-secondary' data-paymentid='" + paymentId + "'></td>"
				     		+ "<td><input name='btnRemove' type='button' value='Remove' "
				     		+ "class='btnRemove btn btn-danger' data-paymentid='" + paymentId + "'></td></tr>"; 
			         } 
			 
			          con.close(); 
			         
			          // Complete the html table
			          out += "</table>"; 
			 
			    } 
			    catch (Exception e)  { 
			             out = "Error while reading data."; 
			             System.err.println(e.getMessage()); 
			    } 
			       
			          return out; 
			}
		//add payment details 
		public String insertPayment(String billId,String cardHolderName,String cardType, String bank, String payAmount, String statuss) {
			 
			 
		       String out = "";
				
			   try{
					  
				   Connection con = connect();
			
				   if (con == null) {
						   return "Error while connecting to the database";
				   }
				
				   
				
					  
					  
				   // create a prepared statement
				   String query = "insert into payment(paymentId,billId,cardHolderName,cardType,bank,payAmount,statuss)" + " values (?,?,?,?,?,?,?)";
			
	              
				   PreparedStatement preparedStmt = con.prepareStatement(query);
			
				   // binding values
				   preparedStmt.setInt(1,0);
				   preparedStmt.setInt(2, Integer.parseInt(billId));
				   preparedStmt.setString(3, cardHolderName);
				   preparedStmt.setString(4, cardType);
				   preparedStmt.setString(5, bank);
				   preparedStmt.setDouble(6, Double.parseDouble(payAmount));
				   preparedStmt.setString(7, statuss);
				   
				   
				   //execute the statement
				   preparedStmt.execute();
				   out="inserted";
				   con.close();
			
				   
				   String newPayment = readPayment(); 
				   out = "{\"status\":\"success\", \"data\": \"" +  newPayment + "\"}"; 
				
				 } 
				 
				 catch (Exception e) 
				 { 
				    out = "{\"status\":\"error\", \"data\": \"Error while generating the Payment.\"}"; 
				 
				    System.err.println(e.getMessage()); 
				 } 
				 
				      return out; 
		} 
		//update payment details
		public String updatePayment(String paymentId, String billId,String cardHolderName,String cardType, String bank, String payAmount, String statuss) {
			  String output = ""; 
			
			  try
			  { 
			      Connection con = connect(); 
			     
			      if (con == null) 
			      {
			    	  return "Error while connecting to the database for updating."; 
			      } 
			
			      
			      // create a prepared statement
			      String query = "UPDATE payment SET billId=?, cardHolderName=?,cardType=?,bank=?,payAmount=?,statuss=?  WHERE paymentId=?"; 
			
			      PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			      
			      // binding values
				  
				   preparedStmt.setInt(1, Integer.parseInt(billId));
				   preparedStmt.setString(2, cardHolderName);
				   preparedStmt.setString(3, cardType);
				   preparedStmt.setString(4, bank);
				   preparedStmt.setDouble(5,Double.parseDouble(payAmount));
				   preparedStmt.setString(6, statuss);
				   preparedStmt.setInt(7, Integer.parseInt(paymentId));
			       
			
			        // execute the statement
			        preparedStmt.execute(); 
			        output ="update success";
			        con.close(); 
			        String newPayment = readPayment(); 
				    output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}"; 
			        
			      
			 } 
			  
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";  
			        System.err.println(e.getMessage()); 
			 } 
			 
			  return output;
		}
		
		//delete payment details
		public String deletePayment(String  paymentId)
		{ 
		        String output = ""; 
		        
		        try{ 
		        
		        	    Connection con = connect(); 
		               
		        	    if (con == null) { 
		                    return "Error while connecting to the database for deleting."; 
		                } 
		        	    
		        	      // create a prepared statement
		        	      String query = "delete from payment where paymentId=?"; 
		        	      
		        	      PreparedStatement preparedStmt = con.prepareStatement(query); 
		        	    
		        	      // binding values
		        	      preparedStmt.setInt(1, Integer.parseInt(paymentId)); 
		        	     
		        	      // execute the statement
		        	      preparedStmt.execute(); 
		        	    
		        	      con.close(); 
		        	    
		        	      String newPayment = readPayment(); 
		   		       output = "{\"status\":\"success\", \"data\": \"" +  newPayment + "\"}";
		        	    
		        } 
		        catch (Exception e) { 
		              
		        	output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";  
		                 System.err.println(e.getMessage()); 
		        }
		        
		        return output; 
		        }
}
	

		        
		
		
		