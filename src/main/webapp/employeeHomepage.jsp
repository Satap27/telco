<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
    <script>
        let validityPeriodSerial = 0;
        let serviceSerial = 0;
        function createValidityPeriod() {
            document.getElementById('add-period').insertAdjacentHTML('beforebegin',
                '<div id="validity-period-' + validityPeriodSerial + '">' +
                '   <label for="validity-period-months">Months</label>' +
                '   <input required type="number" step="1" id="validity-period-months" name="validity-period-months">' +
                '   <label for="validity-period-fee">Fee</label>' +
                '   <input required type="number" step="0.01" id="validity-period-fee" name="validity-period-fee">' +
                '   <button type="button" id="delete-period" onclick="deleteValidityPeriod(' + validityPeriodSerial + ')">delete</button>' +
                '</div>');
            validityPeriodSerial++;
        }

        function deleteValidityPeriod(validityPeriodSerial) {
            document.getElementById("validity-period-" + validityPeriodSerial).remove();
        }

        function createService() {
            const addServiceButton = document.getElementById('add-service');
            const serviceSelected = document.getElementById('service-selection').value;
            if (serviceSelected === '0'){
                addServiceButton.insertAdjacentHTML('afterend',
                    '<div style="margin-bottom: 5px; text-align: center" id="service-' + serviceSerial + '">' +
                    '   FIXED PHONE SERVICE' +
                    '   <input  type="hidden" name="service-fixed-phone" value="1">' +
                    '   <button type="button" id="delete-period" onclick="deleteService(' + serviceSerial + ')">delete</button>' +
                    '</div>');
            } else if (serviceSelected === '1'){
                addServiceButton.insertAdjacentHTML('afterend',
                    '<div style="margin-bottom: 5px; text-align: center" id="service-' + serviceSerial + '">' +
                    '   MOBILE PHONE SERVICE<br>' +
                    '   <label for="service-mobile-phone-minutes-number">Minutes</label>' +
                    '   <input required type="number" step="1" id="service-mobile-phone-minutes-number" name="service-mobile-phone-minutes-number"> ' +
                    '   <label for="service-mobile-phone-minutes-fee">Fee</label> ' +
                    '   <input required type="number" step="0.01" id="service-mobile-phone-minutes-fee" name="service-mobile-phone-minutes-fee">' +
                    '   <br>' +
                    '   <label for="service-mobile-phone-sms-number">SMS</label>' +
                    '   <input required type="number" step="1" id="service-mobile-phone-sms-number" name="service-mobile-phone-sms-number"> ' +
                    '   <label for="service-mobile-phone-gb-fee">Fee</label> ' +
                    '   <input required type="number" step="0.01" id="service-mobile-phone-sms-fee" name="service-mobile-phone-sms-fee">' +
                    '   <button type="button" id="delete-period" onclick="deleteService(' + serviceSerial + ')">delete</button>' +
                    '</div>');
            } else if (serviceSelected === '2'){
                addServiceButton.insertAdjacentHTML('afterend',
                    '<div style="margin-bottom: 5px; text-align: center" id="service-' + serviceSerial + '">' +
                    '   FIXED INTERNET SERVICE<br>' +
                    '   <label for="service-fixed-internet-number">GB</label>' +
                    '   <input required type="number" step="1" id="service-fixed-internet-number" name="service-fixed-internet-number"> ' +
                    '   <label for="service-fixed-internet-fee">Fee</label> ' +
                    '   <input required type="number" step="0.01" id="service-fixed-internet-fee" name="service-fixed-internet-fee">' +
                    '   <button type="button" id="delete-period" onclick="deleteService(' + serviceSerial + ')">delete</button>' +
                    '</div>');
            } else {
                addServiceButton.insertAdjacentHTML('afterend',
                    '<div style="margin-bottom: 5px; text-align: center" id="service-' + serviceSerial + '">' +
                    '   MOBILE INTERNET SERVICE<br>' +
                    '   <label for="service-mobile-internet-number">GB</label>' +
                    '   <input required type="number" step="1" id="service-mobile-internet-number" name="service-mobile-internet-number"> ' +
                    '   <label for="service-mobile-internet-fee">Fee</label> ' +
                    '   <input required type="number" step="0.01" id="service-mobile-internet-fee" name="service-mobile-internet-fee">' +
                    '   <button type="button" id="delete-period" onclick="deleteService(' + serviceSerial + ')">delete</button>' +
                    '</div>');
            }
            serviceSerial++;
        }

        function deleteService(serviceSerial) {
            document.getElementById("service-" + serviceSerial).remove();
        }
    </script>
    <style>
        #page-title {
            width: 100%;
            text-align: center;
            margin-bottom: 100px;
        }

        .creation-div {
            width: 500px;
            display: flex;
            flex-direction: column;
            align-items: center;
            box-shadow: 0 0 10px #aaa;
            border-radius: 5px;
        }

        .creation-form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .main-container {
            display: flex;
            width: 100%;
            justify-content: center;
            gap: 50px;
        }
    </style>
</head>
<body>
<jsp:include page="userInformations.jsp" />
<h1 id="page-title">Employee Homepage</h1>
<div class="main-container">
    <div class="creation-div">
        <h3>Create service package</h3>
        <form class="creation-form" action="servicePackageCreation" method="POST">
            <label style="margin-bottom: 5px;" for="service-package-name">Name</label>
            <input style="margin-bottom: 20px;" required type="text" id="service-package-name" name="service-package-name">
            <div style="margin-bottom: 20px">
                <c:forEach items="${requestScope.optionalProducts}" var="optionalProduct">
                    <input type="checkbox" id="optional-product-${optionalProduct.id}" name="optional-products" value="${optionalProduct.id}">
                    <label for="optional-product-${optionalProduct.id}">${optionalProduct.name} - ${optionalProduct.monthlyFee} ???/month</label>
                    <br>
                </c:forEach>
            </div>
            <div style="display: flex; flex-direction: column; gap: 5px; margin-bottom: 20px">
                <div>
                    <label for="validity-period-months">Months</label>
                    <input required type="number" step="1" id="validity-period-months" name="validity-period-months">
                    <label for="validity-period-fee">Fee</label>
                    <input required type="number" step="0.01" id="validity-period-fee" name="validity-period-fee">
                </div>
                <button style="width: 50px; align-self: center;" type="button" id="add-period" onclick="createValidityPeriod()">+</button>
            </div>
            <div style="margin-bottom: 20px; display: flex; flex-direction: column; align-items: center;">
                <label style="margin-bottom: 5px;" for="service-selection">Select a service</label>
                <select id="service-selection" style="margin-bottom: 5px">
                    <option value="0">fixed phone</option>
                    <option value="1">mobile phone</option>
                    <option value="2">fixed internet</option>
                    <option value="3">mobile internet</option>
                </select>
                <button style="margin-bottom: 5px;" type="button" id="add-service" onclick="createService()">add</button>
            </div>
            <input type="submit" value="CREATE SERVICE PACKAGE">
        </form>
    </div>
    <div class="creation-div">
        <h3>Create optional product</h3>
        <form class="creation-form" action="productCreation" method="POST">
            <label style="margin-bottom: 5px" for="product-name">Name</label>
            <input style="margin-bottom: 20px" required type="text" id="product-name" name="product-name">
            <label style="margin-bottom: 5px" for="product-monthly-fee">Monthly fee</label>
            <input style="margin-bottom: 20px" required type="number" step="0.01" id="product-monthly-fee" name="product-monthly-fee">
            <input type="submit" value="CREATE OPTIONAL PRODUCT">
        </form>
    </div>
</div>
<div style="display: flex; width: 100%; justify-content: center;">
    <div style="margin-top: 30px; box-shadow: 0 0 10px #aaa; padding: 20px; width: 200px; text-align: center; border-radius: 5px;">
        <p>Sales report page <a href="salesReport">here</a></p>
    </div>
</div>
</body>
</html>
