<%-- 
    Document   : recipt_vou_ag_package
    Created on : Nov 18, 2015, 3:59:54 PM
    Author     : ROSHAN
--%>

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
                    var codeH = $('#txtCustCodeH').val();
                    var code = $('#txtCustCode').val();
                    var charge = $('#txtCharge').val();
                    if (codeH !== null) {
                        if (charge !== null) {
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
                            alert('Pleas check the charge type.');
                        }
                    } else {
                        alert('Pleas check the customer code.');
                    }
                }
            }

            function loadSettlements() {
                $('#tblSettlementbody').empty();
                $.ajax({
                    url: "${pageContext.request.contextPath}/LoadSettlementServler",
                    data: {cpu: $('#txtPackage').val()},
                    type: 'GET',
                    dataType: 'json',
                    success: function (result) {
                        var amount = 0;
                        var paid = 0;
                        var balance = 0;
                        $('#tblSettlementbody').append('<tr><th>Description</th><th>Amount</th><th>Settled</th><th>Balance</th></tr>');
                        for (var i = 0; i < result.length; i++) {
                            amount = amount + parseFloat(result[i].amount);
                            paid = paid + parseFloat(result[i].paid);
                            balance = balance + parseFloat(result[i].balance);
                            $('#tblSettlementbody').append('<tr name="sett"><td>' + result[i].description + '</td><td align="right">' + (parseFloat(result[i].amount)).toFixed(2) + '</td><td align="right">' + (parseFloat(result[i].paid)).toFixed(2) + '</td><td align="right">' + (parseFloat(result[i].balance)).toFixed(2) + '</td></tr>');
                        }
                        $('#tblSettlementbody').append('<tr name="sett" style="border-top:1pt solid #666; border-bottom:2pt double #666;"><td><b> Totle </b></td><td align="right"><b>' + amount.toFixed(2) + '</b></td><td align="right"><b>' + paid.toFixed(2) + '</b></td><td align="right"><b>' + balance.toFixed(2) + '</b></td></tr>');
                        $('#txtDue').val(balance.toFixed(2));
                    }});
            }
            $(document).ready(function () {
                if ($('#txtPackage').val() !== null) {
                    loadSettlements();
                }

                //msg Time out
                setTimeout(function () {
                    $('.msg').fadeOut('slow');
                }, 2000);
                //AJAX Call get the Charge details
                $('#txtPackage').focusout(function () {
                    loadSettlements();
                });
                //Payment Validation 

                $('#txtAmount').focusout(function () {
                    var a = $('#txtAmount').val();
                    a = parseFloat(a);
                    a = a.toFixed(2);
                    $('#txtAmount').val(a);
                    var due = parseFloat($("#txtDue").val());
                    var amount = parseFloat($("#txtAmount").val());
                    if (amount > due) {
                        alert('Payment value exceded the due amount.');
                        $("#txtAmount").val('0.00');
                    }
                });
                //End of Payment Validation 
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
            ArrayList<CustomerPackage> customerPackages = (ArrayList<CustomerPackage>) request.getAttribute("customerPackages");
            ArrayList<Charge> charges = (ArrayList< Charge>) request.getAttribute("charges");

            String custCode = "";
            String indexNo = "";
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
        <div class="form_header">Receipt Voucher (Ag Package)</div>
        <form id="frmCustomer" name="frmCustomer" method="post" action="${pageContext.request.contextPath}/ActionController/ReceiptAgPackageController">
            <div id="frmBody">
                <div class="row">
                    <label class="lbltxt">Code :</label>
                    <input id="txtCustCode" name="txtCustCode" type="text" placeholder="Enter Customer Code to Search" maxlength="12" size="12" value="<%=custCode%>">
                    <input id="txtCustCodeH" name="txtCustCodeH" type="hidden" value="<%=indexNo%>">
                    <input type="button"  name="searchBtn" value=" <%=FormAction.SEARCH%> " id="cmdSearch" onclick="search()">
                </div>
                <div class="row">
                    <label class="lbltxt">Name :</label>
                    <input id="txtName" name="txtName" type="text" placeholder="Customer Name" value="<%=custName%>">
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
                <div class="row" id="extPackage">
                    <label class="lbltxt">Package :</label>

                    <select id="txtPackage" name="txtPackage" required="">
                        <%
                            if (customerPackages != null) {
                                for (CustomerPackage customerPackage : customerPackages) {
                        %>
                        <option value="<%=customerPackage.getCpu()%>" title="<%=customerPackage.getVehiclePackage().getDescription()%>"><%=customerPackage.getVehiclePackage().getCode()%></option>
                        <%      }
                            }
                        %>
                    </select>
                </div>
                <div class="row">
                    <label class="lbltxt">Document No :</label>
                    <input id="txtDocumentNo" name="txtDocumentNo" type="text" placeholder="Document No" >
                </div>
                <div class="row">
                    <label class="lbltxt">Remark :</label>
                    <textarea id="txtRemark" name="txtRemark" placeholder="Remark" cols="5" rows="5" maxlength="250"></textarea>
                </div>

                <div class="row">
                    <label class="lbltxt">Pay Amount :</label>
                    <input id="txtAmount" name="txtAmount" type="text" placeholder="0.00" value="<%%>" onkeypress="return isNumberKey(event)" >
                </div>
                <div class="row">
                    <label class="lbltxt">Due Amount :</label>
                    <input id="txtDue" name="txtDue" type="text" placeholder="0.00" value="<%%>" onkeypress="return isNumberKey(event)" disabled="">
                </div>
                <br/>
                <input type="hidden" id="action" name="txtAction" value="<%=action%>">
                <div class="row">
                    <label class="lbltxt"></label>
                    <input type="button"  name="actionBtn" value=" <%=action%> " id="cmdSave" class="funcbutton ui-button"  onclick="update()" <%=btnStyle%>>
                    <input type="reset" value=" RESET " id="cmdReset" class="funcbutton ui-button">
                </div>
                <br>
                <div id="tblSettlement" class="table"><table>
                        <tbody id="tblSettlementbody">

                        </tbody>
                    </table></div>
            </div>
        </form>
        <br>
        <div id ="div1" class="table"></div>
    </body>
</html>
