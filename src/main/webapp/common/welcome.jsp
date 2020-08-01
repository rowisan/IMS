<%-- 
    Document   : welcome
    Created on : Nov 3, 2015, 11:02:05 AM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            String FUNC = (String) request.getParameter(SessionVariables.FUNC);
            //session.setAttribute(SessionVariables.URL, "common/welcome.jsp?FUNC="+FUNC);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1 style="text-align: center; width: 100%; color: #666">Welcome</h1>
    </body>
</html>
