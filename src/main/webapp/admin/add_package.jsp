<%-- 
    Document   : add_package
    Created on : Nov 10, 2015, 4:49:00 PM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.modle.CustomerPackage"%>
<%@page import="com.rowi.lms.modle.Customer"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rowi.lms.modle.VehiclePackage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page import="com.rowi.lms.common.SystemMessageType"%>
<%@page import="com.rowi.lms.common.FormAction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Package</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paginat.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/ui/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/ui/jquery-ui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/ui/paging.js"></script>
        <script type="text/javascript">

            function search() {
                $('#action').val("<%=FormAction.SEARCH%>");
                $('#txtPackage').removeAttr('required');
                $('form').submit();

            }

            function deletea() {
                $('#action').val("<%=FormAction.DELETE%>");
                if (confirm('Do you really want to Delete this customer?')) {
                    $('form').submit();
                }
            }

            function history() {
                $('#action').val("<%=FormAction.HISTORY%>");
                var code = $('#txtCustCode').val();
                if (code.length === 8) {
                    $('#txtPackage').removeAttr('required');
                    $('form').submit();
                } else {
                    $('#msg').addClass('info-msg');
                    $('#msg').text('Pleas insert correct customer code (Length )');
                    $('#msg').show();
                    //msg Time out
                    setTimeout(function () {
                        $('#msg').fadeOut('slow');
                    }, 2000);
                }
            }

            function update() {
                if ($('#txtPackage').val() === null) {
                    alert('Pleas select the package.');
                } else {
                    var codeH = $('#txtCustCodeH').val();
                    var code = $('#txtCustCode').val();
                    if (codeH !== null) {
                        var act = '<%=FormAction.UPDATE%>';
                        if (codeH !== null && codeH !== "") {
                            if ($('#action').val() === act) {
                                if (confirm('Do you really want to Update this customer?')) {
                                    $('form').submit();
                                }
                            } else {
                                $('form').submit();
                            }
                        }
                    } else {
                        alert('Pleas check the customer code.');
                    }
                }
            }

            $(document).ready(function () {
                //msg Time out
                setTimeout(function () {
                    $('.msg').fadeOut('slow');
                }, 2000);

                // AJAX Call get the Package details
                $("select").change(function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/PackageInfoServlet",
                        data: {package: $('#txtPackage').val()},
                        success: function (result) {
                            $("#div1").html(result);
                        }});
                });

                $('#chkKeep').click(function () {
                    if ($('#chkKeep').is(':checked')) {
                        $('#action').val("<%=FormAction.SAVE%>");
                        $('#cmdSave').val('<%=FormAction.SAVE%>');
                    } else {
                        $('#action').val("<%=FormAction.UPDATE%>");
                        $('#cmdSave').val('<%=FormAction.UPDATE%>');
                    }

                });
            });
        </script>
        <%
            String FUNC = (String) request.getParameter(SessionVariables.FUNC);
            ArrayList<VehiclePackage> packages = (ArrayList<VehiclePackage>) request.getAttribute("packages");
//            if (packages == null) {
//                response.sendRedirect("PackageServlet");
//            }

            String indexNo = "";
            String custCode = "";
            String btnStyle = "";
            String custName = "";

            CustomerPackage extVehiclePackage = (CustomerPackage) request.getAttribute("extPackage");
            Customer customer = (Customer) request.getAttribute("customer");
            if (customer != null) {

                custName = customer.getName();
                custCode = customer.getCode();
                indexNo = customer.getIndexNo();
            } else {
                btnStyle = "disabled";
                custName = "";
                custCode = "";
            }
            String cPackage = "";
            String title = "Add";
            String action = "";
        %>
    </head>
    <body>
        <%
            String msg = (String) request.getAttribute(SessionVariables.MSG);
            if (msg != null && !msg.equals("")) {
                String[] msga = msg.split("~");
                msg = msga[0];
                String msgType = msga[1];
                String msgStyle = SystemMessageType.getMsgStyle(msgType);
        %>
        <span class="msg <%=msgStyle%>" id="msg"><%=msg%></span>
        <%
                msg = "";
                msgStyle = "";
                //session.removeAttribute(SessionVariables.MSG);
            }
        %>
        <div class="form_header"><%=title%> - Customer Package</div>
        <form id="frmCustomer" name="frmCustomer" method="post" action="${pageContext.request.contextPath}/ActionController/PackageController">
            <div id="frmBody">
                <div class="row">
                    <label class="lbltxt">Code :</label>
                    <input id="txtCustCode" name="txtCustCode" type="text" placeholder="Enter Customer Code to Search" maxlength="12" size="12" value="<%=custCode%>">
                    <input id="txtCustCodeH" name="txtCustCodeH" type="hidden" value="<%=indexNo%>">
                    <input type="button"  name="searchBtn" value=" <%=FormAction.SEARCH%> " id="cmdSearch" onclick="search()">
                </div>
                <div class="row">
                    <label class="lbltxt">Name :</label>
                    <input id="txtName" name="txtName" type="text" placeholder="Customer Name" value="<%=custName%>" disabled="">
                </div>
                <%
                    String sty = "display: none;";
                    if (custCode != null && !custCode.equals("")) {
                        sty = "";
                    }
                %>
                <div class="row" style="<%=sty%>" id="search-cust">
                    <label class="lbltxt"></label>
                    <span class="search-descript">Currently selected customer <span id="code"><%=custCode%></span></span>
                </div>
                <%
                    String packageSty = "display: none;";
                    action = FormAction.SAVE;
                    request.setAttribute("extPackage", "true");
                    if (extVehiclePackage != null) {
                        request.setAttribute("extPackage", "false");
                        packageSty = "";
                        action = FormAction.UPDATE;

                %>
                <div class="row" style="<%=packageSty%>" id="search-cust">
                    <label class="lbltxt"></label>
                    <span class="search-descript">
                        Currently activated package <span id="code"><%=extVehiclePackage.getVehiclePackage().getCode()%></span>
                        (<%=extVehiclePackage.getVehiclePackage().getDescription()%>)
                    </span>
                    <input type="hidden" name="txtCPU" value="<%=extVehiclePackage.getCpu()%>" />
                    <input type="hidden" name="txtExtPackage" value="<%=extVehiclePackage.getVehiclePackage().getCode()%>" />
                    <input type="hidden" name="txtExtPackageId" value="<%=extVehiclePackage.getIndexNo()%>" />
                </div>
                <%
                    }
                %>
                <div class="row">
                    <label class="lbltxt">Package :</label>
                    <select id="txtPackage" name="txtPackage" required="">
                        <option disabled selected style="text-align: center"> -- select a package -- </option>
                        <%
                            if (packages != null) {
                                for (VehiclePackage package1 : packages) {
                                    String disabled = "";
                                    if (extVehiclePackage != null) {
                                        if (extVehiclePackage.getVehiclePackage().getCode().equals(package1.getCode())) {
                                            disabled = "disabled";
                                        }
                                    }
                        %>
                        <option value="<%=package1.getCode()%>" <%=disabled%> title="<%=package1.getDescription()%>"><%=package1.getCode()%></option>
                        <%}
                            }%>
                    </select>
                </div>
                <div class="row" style="<%=packageSty%>">
                    <label class="lbltxt">Keep existing Package :</label>
                    <input type="checkbox" id="chkKeep" value="TRUE" >
                </div>
                <br/>
                <input type="hidden" id="action" name="txtAction" value="<%=action%>">
                <div class="row">
                    <label class="lbltxt"></label>
                    <input type="button"  name="actionBtn" value=" <%=action%> " id="cmdSave" class="funcbutton ui-button"  onclick="update()" <%=btnStyle%>>
                    <% if (custCode != null) {%>
                    <input type="button"  name="actionBtn" value=" <%=FormAction.HISTORY%> " id="cmdHistory" class="funcbutton ui-button"  onclick="history()">
                    <% }%>
                    <input type="reset" value=" RESET " id="cmdReset" class="funcbutton ui-button">
                </div>
            </div>
        </form>
        <br>
        <div id ="div1" class="table"></div>
    </body>
</html>
