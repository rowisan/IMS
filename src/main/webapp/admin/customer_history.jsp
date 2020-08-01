<%-- 
    Document   : customer_history
    Created on : Nov 16, 2015, 3:18:00 PM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.services.CustomerSER"%>
<%@page import="com.rowi.lms.modle.dto.CustomerHistoryDTO"%>
<%@page import="com.rowi.lms.modle.CustomerPackage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rowi.lms.modle.Customer"%>
<%@page import="com.rowi.lms.common.FormAction"%>
<%@page import="com.rowi.lms.common.SystemMessageType"%>
<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <%
            String FUNC = (String) request.getParameter(SessionVariables.FUNC);
            request.setAttribute(SessionVariables.URL, "admin/customer_history.jsp?" + SessionVariables.FUNC + "=" + FUNC);
        %>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paginat.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/jquery-ui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/paging.js"></script>
        <title>JSP Page</title>
    </head>
    <%
        String action = FormAction.SAVE;
        String title = "New";
        String style = "display: none;";
        ArrayList<CustomerPackage> customerPackages = (ArrayList<CustomerPackage>) request.getAttribute("customerPackages");
        if (!SessionVariables.FUNC.equals(FormAction.SAVE)) {
            action = FormAction.SEARCH;
            style = "";
            title = "History";
        }

        String code = "";
        String name = "";
        String nic = "";
        String phone = "";
        String custPackage = "";
        String description = "";

        double totalAmount = 0.00;
        double paidAmount = 0.00;
        double minOfExam = 0.00;
        double minOfTrial = 0.00;

        String examStatus = "";
        String trialStatus = "";

        try {
            if (request.getAttribute("txtCustCode") != null) {
                CustomerHistoryDTO customerHistoryDTO = CustomerSER.getCustomerHistory((String) request.getAttribute("txtCustCode"));
                code = customerHistoryDTO.getCode();
                name = customerHistoryDTO.getName();
                nic = customerHistoryDTO.getNic();
                phone = customerHistoryDTO.getPhone();
                custPackage = customerHistoryDTO.getPackkage();
                description = customerHistoryDTO.getDescription();

                totalAmount = customerHistoryDTO.getTotalAmount();
                paidAmount = customerHistoryDTO.getPaidAmount();
                minOfExam = customerHistoryDTO.getMinOfExam();
                minOfTrial = customerHistoryDTO.getMinOfTrial();

                examStatus = customerHistoryDTO.getExamStatus();
                trialStatus = customerHistoryDTO.getTrialStatus();
            }
        } catch (Exception e) {
        }
    %> 
    <body>
        <%
            String msg = (String) request.getAttribute(SessionVariables.MSG);
            if (msg
                    != null && !msg.equals(
                            "")) {
                String[] msga = msg.split("~");
                msg = msga[0];
                String msgType = msga[1];
                String msgStyle = SystemMessageType.getMsgStyle(msgType);
        %>
        <span class="msg <%=msgStyle%>"><%=msg%></span>
        <%
                //session.removeAttribute(SessionVariables.MSG);
            }
        %>
        <div class="form_header"><%=title%> - Customer</div>
        <form id="frmCustomer" name="frmCustomer" method="post" action="${pageContext.request.contextPath}/ActionController/CustomerController">
            <%if (!SessionVariables.FUNC.equals(FormAction.SAVE)) {%>
            <div id="but_container">
                <input type="button" id="cmdExam" onclick="addExam()" value="Add Exam List" />
                <input type="button" id="cmdTrial" onclick="addTrial()" value="Add Trial List" />
            </div>
            <%}%>
            <div id="frmBody">
                <div class="row" style="<%//=style%>">
                    <label class="lbltxt">Code :</label>
                    <input id="txtCustCode" name="txtCustCode" type="text" placeholder="Enter Customer Code" value="<%=code%>">
                    <input id="txtCustCodeH" name="txtCustCodeH" type="hidden" value="<%=code%>" required="">
                    <input type="submit"  name="searchBtn" value=" <%=FormAction.SEARCH%> " id="cmdSearch" onclick="search()" style="<%=style%>">
                </div>
                <%
                    String sty = "display: none;";
                    if (code
                            != null && !code.equals(
                                    "")) {
                        sty = "";
                    }
                %>
                <div class="row" style="<%=style%> <%=sty%>" id="search-cust">
                    <label class="lbltxt"></label>
                    <span class="search-descript">Currently selected customer <span id="code"><%=code%></span></span>
                </div>
                <div class="row">
                    <label class="lbltxt">Name :</label>
                    <input id="txtName" name="txtName" type="text" placeholder="Enter Customer Name" value="<%=name%>">
                </div>
                <div class="row">
                    <label class="lbltxt">ID No. :</label>
                    <input id="txtID" name="txtID" type="text" placeholder="Enter ID No." value="<%=nic%>">
                </div>
                <div class="row">
                    <label class="lbltxt">Phone :</label>
                    <input id="txtPhone" name="txtPhone" type="text" placeholder="Phone no." value="<%=phone%>">
                </div>
                <div class="row" id="extPackage">
                    <label class="lbltxt">Package :</label> 
                    <input id="txtPackage" name="txtPackage" type="text" placeholder="Package" value="<%=custPackage%>">
                </div>
                <div class="row" id="total">
                    <label class="lbltxt">Total Amount :</label> <label class="lbltxt"><%=totalAmount%></label>
                </div>
                <div class="row" id="paid">
                    <label class="lbltxt">Paid Amount :</label> <label class="lbltxt"><%=paidAmount%></label>
                </div>
                <div class="row" id="status">
                    <label class="lbltxt">Min for Exam :</label> <label class="lbltxt"><%=minOfExam%></label><label class="lbltxt" style="background-color: #094609"><%=examStatus%></label>
                </div>
                <div class="row" id="status">
                    <label class="lbltxt">Min for Trial :</label> <label class="lbltxt" ><%=minOfTrial%></label><label class="lbltxt" style="background-color: #7D0000"><%=trialStatus%></label>
                </div>
            </div>
            <br/><br/>
            <input type="hidden" id="action" name="txtAction" value="<%=action%>">
            <!--            <div class="row">
                            <label class="lbltxt"></label>
                            <input type="button"  name="actionBtn" value=" <%//action%> " id="cmdSave" class="funcbutton ui-button"  onclick="update()">
                            <input type="button"  name="actionBtn" value=" <%//FormAction.DELETE%> " id="cmdDelete" class="funcbutton ui-button" style="<%//style%>" onclick="deletea()">
                            <input type="reset" value=" RESET " id="cmdReset" class="funcbutton ui-button" onclick="reset()">
                        </div>-->
            </div>
        </form>
    </body>
</html>
