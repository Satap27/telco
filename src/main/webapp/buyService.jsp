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
    </style>
    <title>Buy Service</title>
</head>
<body>
<jsp:include page="userInformations.jsp"/>
<h1>Custom your subscription</h1>

<form action="buyService" method="POST">
    <div>
        <label for="service-package-select">Choose a service package:</label>
        <select required id="service-package-select" name="service-package"
                onchange="filterByServicePackage(this.options[this.selectedIndex].value)">
            <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
                <option value="${servicePackage.id}">
                        ${servicePackage.name}
                </option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="validity-period-select">Choose a validity period:</label>
        <select required id="validity-period-select" name="validity-period">
            <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
                <c:forEach items="${servicePackage.availableValidityPeriods}" var="validityPeriod">
                    <option name="${servicePackage.id}" value="${validityPeriod.id}">
                            ${validityPeriod.numberOfMonths} months - ${validityPeriod.monthlyFee} €/month
                    </option>
                </c:forEach>
            </c:forEach>
        </select>
    </div>

    <c:forEach items="${requestScope.servicePackages}" var="servicePackage">
        <div id="optional-products-${servicePackage.id}" class="optional-products-div">
            <c:forEach items="${servicePackage.availableOptionalProducts}" var="optionalProduct">
                <input type="checkbox" id="optional-product-${optionalProduct.id}" name="optional-products" value="${optionalProduct.id}">
                <label for="optional-product-${optionalProduct.id}">${optionalProduct.name} - ${optionalProduct.monthlyFee} €/month</label>
                <br>
            </c:forEach>
        </div>
    </c:forEach>

    <div>
        <%-- TODO need a min and max?--%>
        <label for="start-date">Choose a starting date:</label>
        <input required type="date" id="start-date" name="start-date">
    </div>
    <input type="submit" value="CONFIRM">
</form>

<script>
    // TODO check if exist
    filterByServicePackage(document.getElementById('service-package-select').value);
</script>
</body>
</html>
