$(document).ready(function() 
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 

//save
$(document).on("click", "#btnSave", function(event) 
{ 
	
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
 
// Form validation-------------------
var status = validatePaymentForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
 
// If valid------------------------
var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "PaymentAPI", 
 type : type, 
 data : $("#formPayment").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onPaymentSaveComplete(response.responseText, status); 
 } 
 }); 
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
$("#hidPaymentIDSave").val($(this).data("paymentid"));  
 $("#billId").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#cardHolderName").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#cardType").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#bank").val($(this).closest("tr").find('td:eq(3)').text()); 
 $("#payAmount").val($(this).closest("tr").find('td:eq(4)').text()); 
 $("#statuss").val($(this).closest("tr").find('td:eq(5)').text()); 
});


 
 //delete
 $(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "PaymentAPI", 
 type : "DELETE", 
 data : "paymentId=" + $(this).data("paymentid"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onPaymentDeleteComplete(response.responseText, status); 
 } 
 }); 
});


// CLIENT-MODEL================================================================
function validatePaymentForm() 
{ 
	
	// billId
	if ($("#billId").val().trim() == "") 
	 { 
	 return "Insert Bill Id."; 
	 }
	// name-------------------------------
	if ($("#cardHolderName").val().trim() == "") 
	 { 
	 return "Insert Name."; 
	 } 
	// cardType------------------------
	if ($("#cardType").val().trim() == "") 
	 { 
	 return "Insert Card Type."; 
	 } 
	 // bank------------------------
	if ($("#bank").val().trim() == "") 
	 { 
	 return "Insert Bank."; 
	 } 
	 // is numerical value
	var tmpPay = $("#payAmount").val().trim(); 
	if (!$.isNumeric(tmpPay)) 
	 { 
	 return "Insert a numerical value for payAmount."; 
	 } 
	 // convert to decimal price
	 $("#payAmount").val(parseFloat(tmpPay).toFixed(2)); 
	 //status
	if ($("#statuss").val().trim() == "") 
	 { 
	 return "Insert status."; 
	 }  
return true; 
}
	
	 

//onSave
function onPaymentSaveComplete(response, status) 
{ 
     if (status == "success") 
     { 
        var resultSet = JSON.parse(response); 
 
        if (resultSet.status.trim() == "success") 
       { 
          $("#alertSuccess").text("Successfully saved."); 
          $("#alertSuccess").show(); 
          $("#divPaymentGrid").html(resultSet.data); 
       
       } else if (resultSet.status.trim() == "error") 
       { 
           $("#alertError").text(resultSet.data); 
           $("#alertError").show(); 
           
       } 
       } else if (status == "error") 
       { 
         $("#alertError").text("Error while saving."); 
         $("#alertError").show(); 
         
       } else
       { 
          $("#alertError").text("Unknown error while saving.."); 
          $("#alertError").show(); 
      } 
 
          $("#hidPaymentIDSave").val(""); 
          $("#formPayment")[0].reset(); 
}



 
function onPaymentDeleteComplete(response, status) 
{ 
     if (status == "success") 
     { 
        var resultSet = JSON.parse(response); 
 
        if (resultSet.status.trim() == "success") 
       { 
          $("#alertSuccess").text("Successfully deleted."); 
          $("#alertSuccess").show(); 
          $("#divPaymentGrid").html(resultSet.data); 
       
       } else if (resultSet.status.trim() == "error") 
       { 
           $("#alertError").text(resultSet.data); 
           $("#alertError").show(); 
           
       } 
       } else if (status == "error") 
       { 
         $("#alertError").text("Error while deleting."); 
         $("#alertError").show(); 
         
       } else
       { 
          $("#alertError").text("Unknown error while deleting.."); 
          $("#alertError").show(); 
      } 
}

 