<%-- 
    Document   : ledger
    Created on : Jan 10, 2016, 3:38:23 PM
    Author     : kandy
--%>

<%@page import="com.rowi.lms.account_report.modle.AccountSummery"%>
<%@page import="com.rowi.lms.services.AccountSER"%>
<%@page import="com.rowi.lms.modle.Account"%>
<%@page import="com.rowi.lms.account_report.modle.IncomeExpences"%>
<%@page import="com.rowi.lms.util.Utilitys"%>
<%@page import="com.rowi.lms.modle.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rowi.lms.common.FormAction"%>
<%@page import="com.rowi.lms.common.SessionVariables"%>
<%@page import="com.rowi.lms.common.SystemMessageType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
        String FUNC = (String) request.getParameter(SessionVariables.FUNC);
        request.setAttribute(SessionVariables.URL, "admin/ledger.jsp?" + SessionVariables.FUNC + "=" + FUNC);
        String action = FormAction.SEARCH;
        String title = "New";

        String fromDate = Utilitys.systemDate();
        String toDate = Utilitys.systemDate();

        if (request.getParameter("fromDate") != null) {
            fromDate = (String) request.getParameter("fromDate");
        }

        if (request.getParameter("toDate") != null) {
            toDate = (String) request.getParameter("toDate");
        }

        String date = "";
        String description = "";
        String Dr = "";
        String Cr = "";

        String account = "";

        ArrayList<Account> accounts = AccountSER.getAccounts("'A', 'L', 'I', 'E'");

        if (request.getParameter("txtAccount") != null) {
            account = request.getParameter("txtAccount");
        }
//        try {
//            if (FUNC.equals(FormAction.SEARCH)) {
//                customer = (Customer) request.getAttribute(SessionVariables.REQ_OBJECT);
//                custCode = customer.getCode();
//                indexNo = customer.getIndexNo();
//                custName = customer.getName();
//                idNo = customer.getIdNo();
//                dob = customer.getDob();
//                phone = customer.getPhone();
//                address1 = customer.getAddress1();
//                address2 = customer.getAddress2();
//                address3 = customer.getAddress3();
//                branch = customer.getBranch();
//                //session.removeAttribute(SessionVariables.REQ_OBJECT);
//            }
//        } catch (Exception e) {
//        }
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
        <div class="form_header">Account Summery</div>
        <form id="frmCustomer" name="frmCustomer" method="post" action="${pageContext.request.contextPath}/AccountSummeryServlet">
            <div id="frmBody">
                <div class="row" style="<%//=style%>">
                    <label class="lbltxt">From :</label>
                    <input id="fromDate" name="fromDate" type="text" placeholder="From Date" value="<%=fromDate%>" class="double">
                    <label class="lbltxt">To :</label>
                    <input id="toDate" name="toDate" type="text" placeholder="To Date" value="<%=toDate%>" class="double">
                    <label class="lbltxt">Branch :</label>
                    <select id="txtAccount" name="txtAccount" class="double">
                        <%String selected = "selected"; %>
                        <% for (Account account1 : accounts) {%>
                        <option value="<%=account1.getAccountCode()%>" <%if (account.equals(account1.getAccountCode()))%><%=selected %> ><%=account1.getAccountCode() + " : " + account1.getAccountName()%></option>
                        <%
                            }
                        %>
                    </select>

                    <input type="submit"  name="searchBtn" value=" <%=FormAction.SEARCH%> " id="cmdSearch" onclick="search()">
                </div>
            </div>
        </form>


        <br><br>
        <%if (FUNC.equals(FormAction.GET_LIST)) {
                ArrayList<AccountSummery> accountSummerys = (ArrayList<AccountSummery>) request.getAttribute("accountSummerys");
        %>
        <div id="tbl">
            <table id="tableData" class="table">
                <thead><tr><th  style="border-left:1pt solid #666">Date</th><th  style="border-left:1pt solid #666">Description</th><th  style="border-left:1pt solid #666">Dr</th><th  style="border-left:1pt solid #666; border-right:1pt solid #666">Cr</th></tr></thead>
                <tbody>
                    <%
                        int i = 1;
                        String odd_tr = "odd-tr";
                        double drD = 0.00;
                        double crD = 0.00;
                        for (AccountSummery accountSummery : accountSummerys) {
                            date = accountSummery.getDate();
                            description = accountSummery.getDescription();
                            Dr = accountSummery.getDr();
                            Cr = accountSummery.getCr();
                            if (!Dr.isEmpty() && !Dr.equals("") && Dr != null) {
                                drD = drD + Double.parseDouble(Dr);
                            }
                            if (!Cr.isEmpty() && !Cr.equals("") && Cr != null) {
                                crD = crD + Double.parseDouble(Cr);
                            }

                            //session.removeAttribute(SessionVariables.REQ_OBJECT);
                            if (i % 2 == 0) {
                                odd_tr = "odd-tr";
                            } else {
                                odd_tr = "";
                            }
                    %>
                    <tr class="<%=odd_tr%>">
                        <td style="border-left:1pt solid #666"><%=date%><input type="hidden" value="<%=date%>" id="custCode<%=i%>" /></td>
                        <td style="border-left:1pt solid #666"><%=description%><input type="hidden" value="<%=description%>" id="custName<%=i%>" /></td>
                        <td style="border-left:1pt solid #666"><%=Dr%><input type="hidden" value="<%=Dr%>" id="idNo<%=i%>"></td>
                        <td style="border-left:1pt solid #666; border-right:1pt solid #666 " ><%=Cr%><input type="hidden" value="<%=Cr%>" id="idNo<%=i%>"></td>
                    </tr>
                    <%
                            i++;
                        }
                    %>
                    <tr style="border-top:1pt solid #666; border-bottom:2pt double #666;">
                        <td style="border-left:1pt solid #666"><input type="hidden" value="" /></td>
                        <td>TOTAL<input type="hidden" value="TOTAL"/></td>
                        <td style="border-left:1pt solid #666"><%=Utilitys.getDecimalValue(String.valueOf(drD))%><input type="hidden" value="<%=drD%>" ></td>
                        <td style="border-left:1pt solid #666; border-right:1pt solid #666"><%=Utilitys.getDecimalValue(String.valueOf(crD))%><input type="hidden" value="<%=crD%>" ></td>
                    </tr>
                    <%
                        }

                    %>
                </tbody>
            </table>
        </div>

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
                height: 70,
                minWidth: 210,
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
                height: 70,
                minWidth: 210
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
                height: 70,
                minWidth: 210,
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
                height: 70,
                minWidth: 210
            });
        });

    </script>


</html>
