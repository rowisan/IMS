<%-- 
    Document   : other_charges
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
        <title>Add Other Charges</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/paginat.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/jquery-ui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ui/paging.js"></script>
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

            $(document).ready(function () {
                //msg Time out
                setTimeout(function () {
                    $('.msg').fadeOut('slow');
                }, 2000);

                //AJAX Call get the Charge details
                $("#txtCharge").change(function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/ChargeInfoServlet",
                        data: {charge: $('#txtCharge').val()},
                        type: 'GET',
                        dataType: 'json',
                        success: function (result) {
                            $("#txtAmount").val((result.defaultAmount).toFixed(2));
                            $("#txtMinExam").val((result.minExam).toFixed(2));
                            $("#txtMinTrial").val((result.minTrial).toFixed(2));
                        }});
                });

                //Payment Validation 

                $('#txtMinExam').focusout(function () {
                    var minTrial = parseFloat($("#txtMinTrial").val());
                    var minExam = parseFloat($("#txtMinExam").val());
                    var amount = parseFloat($("#txtAmount").val());
                    $('#txtMinExam').val(minExam.toFixed(2));
                    if (amount >= minExam) {
                        if (minExam > minTrial) {
                            alert('Min payment for Exam value should lesthan or equal min for trial value \n Trial value will chane.');
                            $("#txtMinTrial").val(minExam.toFixed(2));
                        }
                    } else {
                        alert('Min for exam or Miin for Trial values can not exceed the Other charge amount.');
                        $("#txtMinExam").val('0.00');
                        if (minTrial > amount) {
                            $("#txtMinTrial").val('0.00');
                        }
                    }
                });

                $('#txtMinTrial').focusout(function () {
                    var minTrial = parseFloat($("#txtMinTrial").val());
                    var minExam = parseFloat($("#txtMinExam").val());
                    var amount = parseFloat($("#txtAmount").val());
                    $('#txtMinTrial').val(minTrial.toFixed(2));
                    if (amount < minTrial) {
                        alert('Min payment for Trial value should lesthan or equal Other Charge Amount.');
                        $("#txtMinTrial").val('0.00');
                    }

                    if (minExam > minTrial) {
                        alert('Min payment for Trial value should greater or equal min for Exam Amount.');
                        $("#txtMinTrial").val(minExam.toFixed(2));
                    }
                });

                $('#txtAmount').focusout(function () {
                    var a = $('#txtAmount').val();
                    a = parseFloat(a);
                    a = a.toFixed(2);
                    $('#txtAmount').val(a);

                    var minTrial = parseFloat($("#txtMinTrial").val());
                    var minExam = parseFloat($("#txtMinExam").val());
                    var amount = parseFloat($("#txtAmount").val());
                    if (amount < minTrial) {
                        alert('Min payment for Trial value should lesthan or equal Other Charge Amount.');
                        $("#txtMinTrial").val('0.00');
                    }
                    if (amount < minExam) {
                        alert('Min payment for Exam value should lesthan or equal Other Charge Amount.');
                        $("#txtMinExam").val('0.00');
                    }
                });
                //End of Payment Validation 
            });

            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode != 46 && charCode > 31
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
                indexNo = customer.getIndexNo();
                custCode = customer.getCode();
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
        <div class="form_header"><%=title%> - Other Charges</div>
        <form id="frmCustomer" name="frmCustomer" method="post" action="${pageContext.request.contextPath}/ActionController/OtherChargeController">
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
                <div class="row" id="extPackage">
                    <label class="lbltxt">Package :</label>

                    <select id="txtPackage" name="txtPackage" required="">
                        <%
                            if (customerPackages != null) {
                                for (CustomerPackage customerPackage : customerPackages) {
                        %>
                        <option value="<%=customerPackage.getCpu()%>" title="<%=customerPackage.getVehiclePackage().getDescription()%>"><%=customerPackage.getVehiclePackage().getCode()%></option>
                        <%      }
                            }       %>
                    </select>
                </div>

                <div class="row">
                    <label class="lbltxt">Charge :</label>
                    <select id="txtCharge" name="txtCharge" required="">
                        <option disabled selected style="text-align: center"> -- select a charge type -- </option>
                        <%
                            if (charges != null) {
                                for (Charge charge : charges) {
                        %>
                        <option value="<%=charge.getCode()%>" title="Rs. <%=charge.getDefaultAmount()%>"><%=charge.getDescription()%></option>
                        <%}
                            }%>
                    </select>
                </div>
                <div class="row">
                    <label class="lbltxt">Remark :</label>
                    <textarea id="txtRemark" name="txtRemark" placeholder="Remark" cols="5" rows="5" maxlength="20"></textarea>
                </div>

                <div class="row">
                    <label class="lbltxt">Amount :</label>
                    <input id="txtAmount" name="txtAmount" type="text" placeholder="0.00" value="<%%>" onkeypress="return isNumberKey(event)" >
                </div>
                <div class="row">
                    <label class="lbltxt">Min for Exam :</label>
                    <input id="txtMinExam" name="txtMinExam" type="text" placeholder="0.00" value="<%%>" onkeypress="return isNumberKey(event)" >
                </div>
                <div class="row">
                    <label class="lbltxt">Min for Trial :</label>
                    <input id="txtMinTrial" name="txtMinTrial" type="text" placeholder="0.00" value="<%%>" onkeypress="return isNumberKey(event)" >
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
