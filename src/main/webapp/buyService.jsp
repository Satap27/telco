<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        function filterByServicePackage(servicePackageId) {
            document.querySelectorAll('#validity-period-select option[name]').forEach(function (el) {
                el.classList.add("hidden")
            });
            document.querySelectorAll('#validity-period-select option[name="' + servicePackageId + '"]').forEach(function (el) {
                el.classList.remove("hidden");
            })

            document.querySelectorAll('.optional-products-div').forEach(function (el) {
                el.classList.add("hidden")
            });
            document.getElementById('optional-products-' + servicePackageId).classList.remove("hidden");

            document.getElementById('validity-period-select').value =
                document.querySelector('#validity-period-select option[name="' + servicePackageId + '"]').value;

            const products = document.querySelectorAll('input[type=checkbox][name="optional-products"]');
            for(let i = 0; i < products.length; i++){
                products[i].checked = false;
            }
        }
    </script>
    <style>
        .hidden {
            display: none
        }

        #page-title {
            width: 100%;
            text-align: center;
            margin-bottom: 100px;
        }

        .buy-service-form {
            padding: 20px;
            width: 400px;
            display: flex;
            flex-direction: column;
            align-items: center;
            box-shadow: 0 0 10px #aaa;
            border-radius: 5px;
        }

        .main-container {
            display: flex;
            width: 100%;
            justify-content: center;
            gap: 50px;
            flex-wrap: wrap;
        }
    </style>
    <title>Buy Service</title>
</head>
<body>
<jsp:include page="userInformations.jsp"/>
<h1 id="page-title">Custom your subscription</h1>

<div class="main-container">
    <form action="buyService" method="POST" class="buy-service-form">
            <label for="service-package-select" style="margin-bottom: 5px">Choose a service package</label>
            <select required id="service-package-select" name="service-package"
                    onchange="filterByServicePackage(this.options[this.selectedIndex].value)">
                <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
                    <option value="${servicePackage.id}">
                            ${servicePackage.name}
                    </option>
                </c:forEach>
            </select>
            <label for="validity-period-select" style="margin-top: 20px; margin-bottom: 5px;">Choose a validity period</label>
            <select style="margin-bottom: 20px;" required id="validity-period-select" name="validity-period">
                <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
                    <c:forEach items="${servicePackage.availableValidityPeriods}" var="validityPeriod">
                        <option name="${servicePackage.id}" value="${validityPeriod.id}">
                                ${validityPeriod.numberOfMonths} months - ${validityPeriod.monthlyFee} €/month
                        </option>
                    </c:forEach>
                </c:forEach>
            </select>

        <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
            <div id="optional-products-${servicePackage.id}" class="optional-products-div">
                <c:forEach items="${servicePackage.availableOptionalProducts}" var="optionalProduct">
                    <input type="checkbox" id="optional-product-${optionalProduct.id}" name="optional-products" value="${optionalProduct.id}">
                    <label for="optional-product-${optionalProduct.id}">${optionalProduct.name} - ${optionalProduct.monthlyFee} €/month</label>
                    <br>
                </c:forEach>
            </div>
        </c:forEach>

        <label style="margin-top: 20px; margin-bottom: 5px;" for="start-date">Choose a starting date:</label>
        <input required type="date" id="start-date" name="start-date">
        <input style="margin-top: 20px;" type="submit" value="CONFIRM">
    </form>
</div>

<script>
    filterByServicePackage(document.getElementById('service-package-select').value);
</script>
</body>
</html>
