<%-- 
    Document   : login
    Created on : Oct 22, 2015, 11:27:44 AM
    Author     : ROSHAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="ActionController/LoginAction" method="POST">
            User Name : <input type="text" name="name" />
            Password : <input type="password" name="password"/>
            <input type="submit" value="Login" />
        </form>
        <br>
        <span style="color:red"> 
            <%//if (session.getAttribute("REQ_MSG") != null) {
               //out.print(session.getAttribute("REQ_MSG"));
          // }%>
        </span>
    </body>
</html>
