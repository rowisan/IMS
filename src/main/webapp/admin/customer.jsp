<%-- 
    Document   : customer
    Created on : Oct 29, 2015, 9:59:46 PM
    Author     : ROSHAN
--%>

<%@page import="com.rowi.lms.common.SystemMessageType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rowi.lms.modle.Customer"%>
<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page import="com.rowi.lms.common.FormAction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            String FUNC = (String) request.getParameter(SessionVariables.FUNC);
            request.setAttribute(SessionVariables.URL, "admin/customer.jsp?" + SessionVariables.FUNC + "=" + FUNC);
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
        <script type="text/javascript">

//Pagination
            $(document).ready(function () {
                $('.table').paging({limit: 5});
            });

            function search() {
                $('#action').val("<%=FormAction.SEARCH%>");
            }

            function package() {
                $('#action').val("<%=FormAction.PACKAGE%>");
                $('form').submit();
            }

            function other() {
                $('#action').val("<%=FormAction.OTHER%>");
                $('form').submit();
            }

            function receipt() {
                $('#action').val("<%=FormAction.RECEIPT%>");
                $('form').submit();
            }

            function deletea() {
                $('#action').val("<%=FormAction.DELETE%>");
                if (confirm('Do you really want to Delete this customer?')) {
                    $('form').submit();
                }
            }

            function reset() {
                var id = "";
                var custCode = "";
                var custName = "";
                var idNo = "";
                var dob = "";
                var phone = "";
                var address = "";
                var branch = "";

                $('#code').text(custCode);
                $('#txtCustCode').val(custCode);
                $('#txtCustCodeH').val(custCode);
                $('#Code').val(custCode);
                $('#txtName').val(custName);
                $('#txtID').val(idNo);
                $('#fromDate').val(dob);
                $('#txtPhone').val(phone);
                $('#txtAddress1').val("");
                $('#txtAddress2').val("");
                $('#txtAddress3').val("");
                $('#txtBranch').val(branch);
                $('#search-cust').css('display', 'none');
            }
            function update() {
                var act = '<%=FormAction.UPDATE%>';
                if ($('#action').val() === act) {
                    if (confirm('Do you really want to Update this customer?')) {
                        $('form').submit();
                    }
                } else {
                    $('form').submit();
                }
            }

            function clkTr(idNo) {
//                var id = $(this).closest('tr').attr('id');
                var id = idNo;
                var custCode = $('#custCode' + id + '').val();
                var custName = $('#custName' + id + '').val();
                var idNo = $('#idNo' + id + '').val();
                var dob = $('#dob' + id + '').val();
                var phone = $('#phone' + id + '').val();
                var address = $('#address' + id + '').val();
                var branch = $('#branch' + id + '').val();
                address = address.split(",");

                $('#search-cust').css('display', 'block');

                $('#code').text(custCode);
                $('#txtCustCode').val(custCode);
                $('#txtCustCodeH').val(custCode);
                $('#Code').val(custCode);
                $('#txtName').val(custName);
                $('#txtID').val(idNo);
                $('#fromDate').val(dob);
                $('#txtPhone').val(phone);
                $('#txtAddress1').val((address[0]).trim());
                $('#txtAddress2').val((address[1]).trim());
                $('#txtAddress3').val((address[2]).trim());
                $('#txtBranch').val(branch);
            }

//msg Time out
            setTimeout(function () {
                $('.msg').fadeOut('slow');
            }, 2000);
        </script>
        <title>Customer</title>
    </head>
    <%
        String action = FormAction.SAVE;
        String title = "New";
        String style = "display: none;";
        if (!FUNC.equals(FormAction.SAVE)) {
            action = FormAction.UPDATE;
            style = "";
            title = "Edit";
        }
        Customer customer = new Customer();
        String custCode = "";
        String indexNo = "";
        String custName = "";
        String idNo = "";
        String dob = "";
        String phone = "";
        String address1 = "";
        String address2 = "";
        String address3 = "";
        String branch = "";
        String address = "";
        try {
            if (FUNC.equals(FormAction.SEARCH)) {
                customer = (Customer) request.getAttribute(SessionVariables.REQ_OBJECT);
                custCode = customer.getCode();
                indexNo = customer.getIndexNo();
                custName = customer.getName();
                idNo = customer.getIdNo();
                dob = customer.getDob();
                phone = customer.getPhone();
                address1 = customer.getAddress1();
                address2 = customer.getAddress2();
                address3 = customer.getAddress3();
                branch = customer.getBranch();
                //session.removeAttribute(SessionVariables.REQ_OBJECT);
            }
        } catch (Exception e) {
        }
    %> 
    <body>
        <%
            String msg = (String) request.getAttribute(SessionVariables.MSG);
            if (msg != null && !msg.equals("")) {
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
            <%if (!FUNC.equals(FormAction.SAVE)) {%>
            <div id="but_container">
                <input type="button" id="cmdPackage" onclick="package()" value="Add Package" />
                <input type="button" id="cmdOtherCharge" onclick="other()" value="Other Charge" />
                <input type="button" id="cmdReceiptAgPackage" onclick="receipt()"  value="Receipt (Ag Package)" />
                <input type="button" id="cmdHistory" onclick="history()"  value="Customer History" />
            </div>
            <%}%>
            <div id="frmBody">
                <div class="row" style="<%//=style%>">
                    <label class="lbltxt">Code :</label>
                    <input id="txtCustCode" name="txtCustCode" type="text" placeholder="Enter Customer Code" value="<%=custCode%>">
                    <input id="txtCustCodeH" name="txtCustCodeH" type="hidden" value="<%=custCode%>" required="">
                    <input type="submit"  name="searchBtn" value=" <%=FormAction.SEARCH%> " id="cmdSearch" onclick="search()" style="<%=style%>">
                </div>
                <%
                    String sty = "display: none;";
                    if (custCode != null && !custCode.equals("")) {
                        sty = "";
                    }
                %>
                <div class="row" style="<%=style%> <%=sty%>" id="search-cust">
                    <label class="lbltxt"></label>
                    <span class="search-descript">Currently selected customer <span id="code"><%=custCode%></span></span>
                </div>
                <div class="row">
                    <label class="lbltxt">Name :</label>
                    <input id="txtName" name="txtName" type="text" placeholder="Enter Customer Name" value="<%=custName%>">
                </div>
                <div class="row">
                    <label class="lbltxt">ID No. :</label>
                    <input id="txtID" name="txtID" type="text" placeholder="Enter ID No." value="<%=idNo%>">
                </div>
                <div class="row">
                    <label class="lbltxt">Date of Birth :</label>
                    <input id="fromDate" name="from" type="text" placeholder="Date of Birth" value="<%=dob%>">
                </div>
                <div class="row">
                    <label class="lbltxt">Phone :</label>
                    <input id="txtPhone" name="txtPhone" type="text" placeholder="Phone no." value="<%=phone%>">
                </div>
                <div class="row">
                    <label class="lbltxt">Address :</label>
                    <input id="txtAddress1" name="txtAddress1" type="text" placeholder="Address Line 1" value="<%=address1%>" class="double">
                    <input id="txtAddress2" name="txtAddress2" type="text" placeholder="Address Line 2" value="<%=address2%>" class="double-other">
                </div>
                <div class="row">
                    <label class="lbltxt-hidn1"></label>
                    <input id="txtAddress3" name="txtAddress3" type="text" placeholder="Address Line 3" value="<%=address3%>">
                </div>
                <div class="row" style="<%=style%>">
                    <label class="lbltxt">Branch :</label>
                    <select id="txtBranch" name="txtBranch">
                        <%String selected = "selected"; %>
                        <option value="PUJ" <%if (branch.equals("PUJ"))%><%=selected %>>POOJAPITIYA</option>
                        <option value="AMB" <%if (branch.equals("AMB"))%><%=selected %>>AMBATENNA</option>
                        <option value="ANK" <%if (branch.equals("ANK"))%><%=selected %>>ANKUMBURE</option>
                    </select>
                </div>
                <br/><br/>
                <input type="hidden" id="action" name="txtAction" value="<%=action%>">
                <div class="row">
                    <label class="lbltxt"></label>
                    <input type="button"  name="actionBtn" value=" <%=action%> " id="cmdSave" class="funcbutton ui-button"  onclick="update()">
                    <input type="button"  name="actionBtn" value=" <%=FormAction.DELETE%> " id="cmdDelete" class="funcbutton ui-button" style="<%=style%>" onclick="deletea()">
                    <input type="reset" value=" RESET " id="cmdReset" class="funcbutton ui-button" onclick="reset()">
                </div>
            </div>
        </form>
        <br><br>
        <%if (FUNC.equals(FormAction.GET_LIST)) {
                ArrayList<Customer> customers = (ArrayList<Customer>) request.getAttribute(SessionVariables.REQ_OBJECT);
        %>
        <div id="tbl">
            <table id="tableData" class="table">
                <thead><tr><th>Code</th><th>Name</th><th>ID No.</th><th>Date of Birth</th><th>Phone</th><th>Address</th><th>Branch</th></tr></thead>
                            <%
                                int i = 1;
                                String odd_tr = "odd-tr";

                                for (Customer customer1 : customers) {
                                    custCode = customer1.getCode();
                                    custName = customer1.getName();
                                    idNo = customer1.getIdNo();
                                    dob = customer1.getDob();
                                    phone = customer1.getPhone();
                                    address1 = customer1.getAddress1();
                                    address2 = customer1.getAddress2();
                                    address3 = customer1.getAddress3();
                                    address = address1 + "," + address2 + "," + address3;
                                    branch = customer1.getBranch();
                                    //session.removeAttribute(SessionVariables.REQ_OBJECT);
                                    if (i % 2 == 0) {
                                        odd_tr = "odd-tr";
                                    } else {
                                        odd_tr = "";
                                    }
                            %>
                <tbody>
                    <tr class="<%=odd_tr%> data" id="<%=i%>" onclick="clkTr(<%=i%>)">
                        <td><%=custCode%><input type="hidden" value="<%=custCode%>" id="custCode<%=i%>" /></td>
                        <td><%=custName%><input type="hidden" value="<%=custName%>" id="custName<%=i%>" /></td>
                        <td><%=idNo%><input type="hidden" value="<%=idNo%>" id="idNo<%=i%>"></td>
                        <td><%=dob%><input type="hidden" value="<%=dob%>" id="dob<%=i%>"></td>
                        <td><%=phone%><input type="hidden" value="<%=phone%>" id="phone<%=i%>"></td>
                        <td><%=address%><input type="hidden" value="<%=address%>" id="address<%=i%>"></td>
                        <td><%=branch%><input type="hidden" value="<%=branch%>" id="branch<%=i%>"></td>
                    </tr>
                </tbody>
                <%
                        i++;
                    }%>
            </table>
        </div>
        <%
                request.setAttribute(SessionVariables.URL, "admin/customer.jsp?" + SessionVariables.FUNC + "=" + FormAction.UPDATE);
            }%>
    </body>

    <script type="text/javascript">
        $(function () {
            $("#fromDate").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true
            });
            $("#toDate").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true
            });
            $("#deftype").multiselect({
                noneSelectedText: "Select an option",
                selectedList: 3,
                height: 100,
                minWidth: 310,
                multiple: false
            });
            $("#branchCode").multiselect({
                close: function (event, ui) {
                    var values = $("#" + this.id).val();
                    txtVal = document.getElementById('val' + this.id);
                    if (values == null) {
                        txtVal.value = "";
                    } else {
                        txtVal.value = values;
                        
                        
                    }
                },
                noneSelectedText: "Select an option",
                selectedList: 3,
                height: 100,
                minWidth: 310
            });
            $("#repType").multiselect({
                close: function (event, ui) {
                    var values = $("#" + this.id).val();
                    txtVal = document.getElementById('val' + this.id);
                    if (values === null) {
                        txtVal.value = "";
                    } else {
                        txtVal.value = values;
                    }
                },
                noneSelectedText: "Select an option",
                selectedList: 3,
                height: 100,
                minWidth: 310,
                multiple: false
            });
            $("#deftype").multiselect({
                close: function (event, ui) {
                    var values = $("#" + this.id).val();
                    txtVal = document.getElementById('val' + this.id);
                    if (values === null) {
                        txtVal.value = "";
                    } else {
                        txtVal.value = values;
                    }
                },
                noneSelectedText: "Select an option",
                selectedList: 3,
                height: 100,
                minWidth: 310
            });
        });

    </script>
</html>
