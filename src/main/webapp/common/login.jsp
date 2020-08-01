<%-- 
    Document   : login
    Created on : Dec 23, 2015, 11:26:09 AM
    Author     : ROSHAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LMS - Login</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    </head>
    <body class="login-body">
        <table>
            <tr><td id="td-left"></td><td>
                    <div id="frmBody">
                        <div id="login-div">
                            <div class="login-hedder">
                                <h3>User Login</h3>
                            </div>
                            <br>
                            <form method="POST" action="${pageContext.request.contextPath}/ActionController/LoginController?FUNC=LOGIN">
                                <div class="row">
                                    <label class="lbltxt">User Name</label>
                                    <input type="text" id="txtName" name="txtName" required=""/>
                                    <input type="hidden" id="txtAction" name="txtAction" value="LOGIN" />
                                </div>

                                <div class="row">
                                    <label class="lbltxt">Password</label>
                                    <input type="password" id="txtPassword" name="txtPassword" required=""/>
                                </div>
                                <br>
                                <br>
                                <div class="row">
                                    <label class="lbltxt"></label>
                                    <input type="submit" value="Login" />
                                    <input type="reset" />
                                </div>
                            </form>
                            <br>
                            <center>
                                <div class="copy-right">
                                    <hr>
                                    &copy; Copy rights - 2015 <br>
                                    RoWi
                                </div>
                            </center>
                        </div>
                    </div>
                </td><td id="td-right"></td></tr>
        </table>
    </body>
</html>
