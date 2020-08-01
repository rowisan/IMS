<%-- 
    Document   : menu
    Created on : Dec 22, 2015, 10:20:16 PM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.common.FormAction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/jquery-ui.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/ui/jquery-ui.js"></script>
        <style>
            #accordion-resizer {
                padding: 5px;
                /*width: 196px;*/
            }
            .ui-accordion .ui-accordion-header{
                font-size: 12px;
                font-weight: 900;
            }

            .ui-helper-reset{
                font-size: 12px;
            }
            .ui-accordion .ui-accordion-content{
                padding: 0;
            }
        </style>
        <script>
//            $(function () {
//                $("#accordion").accordion({
//                    heightStyle: "content"
//                });
//            });
            $(function () {
                $("#accordion").accordion({
                    collapsible: true
                });
            });

            $(document).ready(
                    $(window).resize(function () {
                var h = $(window).height();
                h = h - 102;

                $('#accordion').css('height', h)
            })
                    );
            $(document).ready(
                    function () {
                        var h = $(window).height();
                        h = h - 102;

                        $('#accordion').css('height', h)
                    });
        </script>
    </head>
    <body>
        <div id="accordion-resizer" class="ui-widget-content">
            <div id="accordion">
                <h6>Home</h6>
                <div>
                    <p> 
                        <a id="b0" href="common/welcome.jsp" target="imain_frm" class="menu_item" >Home </a>
                    </p>
                </div>
                <h6>Customer</h6>
                <div>
                    <p> 
                        <a id="b1" href="admin/customer.jsp?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Add Member</a><br>
                        <a id="b2" href="admin/customer.jsp?FUNC=<%=FormAction.UPDATE%>" target="imain_frm" class="menu_item" >Edit Member</a><br>
                        <a id="b2" href="admin/customer_history.jsp?FUNC=<%=FormAction.SEARCH%>" target="imain_frm" class="menu_item" >Member History</a>
                    </p>
                </div>
                <h6>Package</h6>
                <div>
                    <p> 
                        <a id="b3" href="PackageServlet?FUNC="<%=FormAction.INIT%> target="imain_frm" class="menu_item" >Add Package</a><br>
                    </p>
                </div>
                <h6>Charge</h6>
                <div>
                    <p>
                        <a id="b4" href="OtherChargeServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Other Charges </a><br>
                    </p>
                </div>
                <h6>Receipt</h6>
                <div>
                    <p>
                        <a id="b4" href="ReceiptAgPackageServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Receipt (Ag Package) </a><br>
                        <a id="b4" href="ReceiptAgAccountServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Receipt (On Account) </a><br>
                    </p>
                </div>
                <h6>Payment</h6>
                <div>
                    <p>
                        <a id="b4" href="PaymentOnAccountServlet?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Payment (On Account) </a><br>
                    </p>
                </div>
                <h6>Report</h6>
                <div>
                    <p>
                        <a id="b4" href="report/account_summery.jsp?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Income & Expences</a><br>
                        <a id="b4" href="report/ledger.jsp?FUNC=<%=FormAction.SAVE%>" target="imain_frm" class="menu_item" >Account Summery</a><br>
                        <br>
                        <br>
                        <br>
                        <br>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>
