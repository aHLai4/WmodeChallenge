<%-- 
    Document   : displayInfo
    Created on : Sep 11, 2015, 1:49:25 AM
    Author     : aHLai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Buyer information</title>
    </head>
    <body>
        Product Creator Name: <%= request.getParameter("fullName") %></br>
        Buyer Company Name: <%= request.getParameter("companyName") %></br>
        Edition Code: <%= request.getParameter("edition") %></br>
        
        <form action="CompleteTransaction" method="POST">
            <input type="hidden" name="returnUrl" value="<%= request.getParameter("returnUrl") %>">
            <input type="submit" value="Continue to checkout">
        </form>
    </body>
</html>
