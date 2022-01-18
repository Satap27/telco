<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sales Report</title>
    <style>
        #page-title {
            width: 100%;
            text-align: center;
            margin-bottom: 100px;
        }

        .statistic-div {
            width: 400px;
            display: flex;
            flex-direction: column;
            align-items: center;
            box-shadow: 0 0 10px #aaa;
            border-radius: 5px;
        }

        .access-form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .main-container {
            display: flex;
            width: 100%;
            justify-content: center;
            gap: 50px;
            flex-wrap: wrap;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

    </style>
</head>
<body>
<jsp:include page="userInformations.jsp"/>
<h1 id="page-title">Sales report</h1>
<div class="main-container">
    <div class="statistic-div">
        <h4>Purchases per package</h4>
        <table>
            <tr>
                <th>Package</th>
                <th>Purchases</th>
            </tr>
            <c:forEach items="${requestScope.purchasesPerPackage}" var="purchasePerPackage">
                <tr>
                    <td>${purchasePerPackage.getPackageName()}</td>
                    <td>${purchasePerPackage.getPurchases()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="statistic-div">
        <h4>Purchases per package and period</h4>
        <table>
            <tr>
                <th>Package</th>
                <th>Period Months</th>
                <th>Period Fee</th>
                <th>Purchases</th>
            </tr>
            <c:forEach items="${requestScope.purchasesPerPackagePeriod}" var="purchasePerPackagePeriod">
                <tr>
                    <td>${purchasePerPackagePeriod.getPackageName()}</td>
                    <td>${purchasePerPackagePeriod.getMonths()}</td>
                    <td>${purchasePerPackagePeriod.getMonthlyFee()}</td>
                    <td>${purchasePerPackagePeriod.getPurchases()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="statistic-div">
        <h4>Revenues per package</h4>
        <table>
            <tr>
                <th>Package</th>
                <th>Revenues with options</th>
                <th>Revenues without options</th>
            </tr>
            <c:forEach items="${requestScope.revenuesPerPackage}" var="revenuePerPackage">
                <tr>
                    <td>${revenuePerPackage.getPackageName()}</td>
                    <td>${revenuePerPackage.getRevenuesWithOptions()}</td>
                    <td>${revenuePerPackage.getRevenuesWithoutOptions()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="statistic-div">
        <h4>Average products bought per package</h4>
        <table>
            <tr>
                <th>Package</th>
                <th>Average products</th>
            </tr>
            <c:forEach items="${requestScope.productsPerPackage}" var="productPerPackage">
                <tr>
                    <td>${productPerPackage.getPackageName()}</td>
                    <td>${productPerPackage.getProductsAverage()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="statistic-div">
        <h4>Insolvent users</h4>
        <table>
            <tr>
                <th>Username</th>
            </tr>
            <c:forEach items="${requestScope.insolventUsers}" var="insolventUser">
                <tr>
                    <td>${insolventUser.getUsername()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="statistic-div">
        <h4>Suspended orders</h4>
        <table>
            <tr>
                <th>Username</th>
                <th>Total price</th>
            </tr>
            <c:forEach items="${requestScope.suspendedOrders}" var="suspendedOrder">
                <tr>
                    <td>${suspendedOrder.getUser().getUsername()}</td>
                    <td>${suspendedOrder.getTotalPrice()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="statistic-div">
        <h4>Alerts</h4>
        <table>
            <tr>
                <th>Username</th>
                <th>Amount</th>
                <th>Rejection time</th>
            </tr>
            <c:forEach items="${requestScope.alerts}" var="alert">
                <tr>
                    <td>${alert.getUser().getUsername()}</td>
                    <td>${alert.getAmount()}</td>
                    <td>${alert.getRejectionTime()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <c:set var="bestProduct" value="${requestScope.bestProduct}"/>
    <c:if test="${bestProduct != null}">
        <div class="statistic-div">
            <h4>Best product</h4>
            <table>
                <tr>
                    <th>Product</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td>${bestProduct.getProductName()}</td>
                    <td>${bestProduct.getValue()}</td>
                </tr>
            </table>
        </div>
    </c:if>
</div>
</body>
</html>
