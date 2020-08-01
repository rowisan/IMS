<%-- 
    Document   : recipt_vou_ag_package
    Created on : Nov 18, 2015, 3:59:54 PM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.modle.Narration"%>
<%@page import="com.rowi.lms.modle.Account"%>
<%@page import="com.rowi.lms.modle.Charge"%>
<%@page import="com.rowi.lms.common.SystemMessageType"%>
<%@page import="com.rowi.lms.modle.Customer"%>
<%@page import="com.rowi.lms.modle.CustomerPackage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rowi.lms.modle.VehiclePackage"%>
<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page import="com.rowi.lms.common.FormAction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Receipt Ag Package</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paginat.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/jquery-ui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/paging.js"></script>
        <style>
            .form_header{
                background-color: #b8ffc7;
            }
        </style>
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
                if ($('#txtAmount').val() === null) {
                    alert('Invalide payment amount.');
                } else {
                    var account = $('#txtAccount').val();
                    if (account !== null) {
                        $('form').submit();
                    } else {
                        alert('Pleas select the account.');
                    }
                }
            }

            $(document).ready(function () {
                //msg Time out
                setTimeout(function () {
                    $('.msg').fadeOut('slow');
                }, 2000);

            });
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode !== 46 && charCode > 31
                        && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>
        <%
            String FUNC = (String) request.getParameter(SessionVariables.FUNC);
            ArrayList<Account> accounts = (ArrayList<Account>) request.getAttribute("account");
            ArrayList<Narration> narrations = (ArrayList<Narration>) request.getAttribute("narration");

            String custCode = "";
            String btnStyle = "";

            String cPackage = "";
            String title = "Add";
            String action = FormAction.SAVE;
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
            }
        %>
        <div class="form_header">Receipt Voucher (On Account)</div>
        <form id="frmCustomer" name="frmCustomer" method="post" action="${pageContext.request.contextPath}/ActionController/ReceiptOnAccountController">
            <div id="frmBody">

                <div class="row" id="extAccount">
                    <label class="lbltxt">Account : </label>

                    <select id="txtAccount" name="txtAccount">
                        <%
                            if (accounts != null) {
                                for (Account account : accounts) {
                        %>
                        <option value="<%=account.getAccountCode()%>" title="<%=account.getAccountName()%>"><%=account.getAccountCode() + " : " + account.getAccountName()%></option>
                        <%      }
                            }
                        %>
                    </select>
                </div>
                <div class="row">
                    <label class="lbltxt">Document No :</label>
                    <input id="txtDocumentNo" name="txtDocumentNo" type="text" placeholder="Document No" >
                </div>
                <div class="row" id="extNarration">
                    <label class="lbltxt">Narration : </label>

                    <select id="txtNarration" name="txtNarration" required="">
                        <%
                            if (narrations != null) {
                                for (Narration narration : narrations) {
                        %>
                        <option value="<%=narration.getCode()%>" title="<%=narration.getDescription()%>"><%=narration.getCode() + " : " + narration.getDescription()%></option>
                        <%      }
                            }
                        %>
                    </select>
                </div>
                <div class="row">
                    <label class="lbltxt">Remark :</label>
                    <textarea id="txtRemark" name="txtRemark" placeholder="Remark" cols="5" rows="5" maxlength="250"></textarea>
                </div>

                <div class="row">
                    <label class="lbltxt">Pay Amount :</label>
                    <input id="txtAmount" name="txtAmount" type="text" placeholder="0.00" value="<%%>" onkeypress="return isNumberKey(event)" >
                </div>
                <br/>
                <input type="hidden" id="action" name="txtAction" value="<%=action%>">
                <div class="row">
                    <label class="lbltxt"></label>
                    <input type="button"  name="actionBtn" value=" <%=action%> " id="cmdSave" class="funcbutton ui-button"  onclick="update()" <%=btnStyle%>>
                    <input type="reset" value=" RESET " id="cmdReset" class="funcbutton ui-button">
                </div>

            </div>
        </form>
        <br>
        <div id ="div1" class="table"></div>
    </body>
</html>
