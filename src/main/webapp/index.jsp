<%-- 
    Document   : index
    Created on : Oct 28, 2015, 2:23:11 PM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page import="com.rowi.lms.common.FormAction"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LMS</title>
        <link rel="stylesheet" href="css/index.css"/>

        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">

            <%for (int i = 1; i <= 2; i++) {%>
            $(document).ready(function () {
                $('#c<%=i%>').click(function () {
                    if ($("#c<%=i%>").is(':checked')) {
                        $('#b<%=i%>').attr('disabled', 'disabled');
                    } else {
                        $('#b<%=i%>').removeAttr('disabled');
                    }
                })
            });
            <%}%>

            $(document).ready(
                    $(window).resize(function () {
                var h = $(window).height();
                h = h - 88;
                var w = $(window).width();
                w = w - 206;
                $('.side_pane').css('height', h)
                $('.desktop_pane').css('width', w)
                $('.desktop_conainer').css('height', h - 32)
            })
                    );
            $(document).ready(
                    function () {
                        var h = $(window).height();
                        h = h - 88;
                        var w = $(window).width();
                        w = w - 206;
                        $('.side_pane').css('height', h)
                        $('.desktop_pane').css('width', w)
                        $('.desktop_conainer').css('height', h - 32)
                    });

            $(document).ready(function () {
                $(".menu_item").click(function () {
                    var url = $(this).attr('name')
//                    url=url+".jsp";

                    $(".desktop_conainer").load(url);

                });
            });
//           


        </script>

        <%
            session.setAttribute(SessionVariables.BRANCH, "AMB");
            session.setAttribute(SessionVariables.USER, "TESTUSER");
        %>
    </head>
    <body>
        <div class="top_pane">
            <h2 style="padding-left: 20px;">Haritha Driving School<%//=user.getuRole()%></h2>
            <% // if (user.getuRole() == 1 || user.getuRole() == 2) { %>
            <div class="user-panel"><p><%=session.getAttribute(SessionVariables.USER)%> | <a href="#">LOGOUT</a></p></div>
        </div>
        <div class="side_pane">
            <%@ include file="common/menu.jsp" %>
<!--                        <div>
                            <a id="b0" href="common/welcome.jsp" target="imain_frm"><button class="menu_item" >Home >></button></a>
                            <hr>
                            <a id="b1" href="admin/customer.jsp?FUNC=<%=FormAction.SAVE%>" target="imain_frm"><button class="menu_item" >Add Member >></button></a><br>
            <%//} else if (user.getuRole() == 3) { %>
            <a id="b2" href="admin/customer.jsp?FUNC=<%=FormAction.UPDATE%>" target="imain_frm"><button class="menu_item" >Edit Member >></button></a>
            <%// }%>
            <a id="b3" href="PackageServlet?FUNC="<%=FormAction.INIT%> target="imain_frm"><button class="menu_item" >Add Package >></button></a><br>
            <hr>
            <a id="b4" href="OtherChargeServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm"><button class="menu_item" >Other Charges >></button></a><br>
            <a id="b4" href="ReceiptAgPackageServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm"><button class="menu_item" >Receipt (Ag Package) >></button></a><br>
            <a id="b4" href="ReceiptAgAccountServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm"><button class="menu_item" >Receipt (On Account) >></button></a><br>
            <a id="b4" href="PaymentOnAccountServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm"><button class="menu_item" >Payment (On Account) >></button></a><br>

            <br>
            <br>
            <a href="/TestWebApp/ActionController/LogoutAction" class="menu_item">Logout</a>
        </div>-->

        </div>

        <div class="desktop_pane">
            <div class="desktop_conainer"  id="desktop_conainer">
                <iframe width="100%" height="98%" id="iframe1" name="imain_frm" marginheight="0" frameborder="0" src="common/welcome.jsp"></iframe>
            </div>
            <div class="footer">solution & &copy; copy rights</div>
        </div>
    </body>


</html>
