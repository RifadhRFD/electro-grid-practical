<%@ page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Component/jquery-3.2.1.min.js"></script>
<script src="Component/payment.js"></script>
</head>
<body> 

		<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Payment Management</a>
  	</nav>	<br><br>
  	
  	
	<div class="container"><div class="row"><div class="col-6"> 
	
	<form id="formPayment" name="formPayment">

		 
		 
		 Bill ID: 
		 <input id="billId" name="billId" type="text" class="form-control form-control-sm">
		 <br> 
		 
		 Card Holder Name: 
		 <input id="cardHolderName" name="cardHolderName" type="text"  class="form-control form-control-sm">
		 <br> 
		 
		 Card Type: 
		 <input id="cardType" name="cardType" type="text" class="form-control form-control-sm">
		 <br>
		 
		 Bank: 
		 <input id="bank" name="bank" type="text"  class="form-control form-control-sm">
		 <br> 
		 
		 Pay Amount: 
		 <input id="payAmount" name="payAmount" type="text"  class="form-control form-control-sm">
		 <br> 
		 
		 Status: 
		 <input id="statuss" name="statuss" type="text"  class="form-control form-control-sm">
		 <br> 
		 
		 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		 <input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
		</form>
	
	<br>
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	
	
	<div id="divPaymentGrid">
 
			 <%
			 Payment paymentObj = new Payment(); 
			 out.print(paymentObj.readPayment()); 
			 %>
 
</div>
</div> </div> </div> 
</body>
</html>
    