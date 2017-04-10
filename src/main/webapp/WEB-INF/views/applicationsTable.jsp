<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet"/>
<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Список заявоу на покупку товаров</span>
            <input type="text" placeholder="Поиск..." id="myInput" onkeyup="seachInUserTable()"/></div>
    </div>

    <div class="tablecontainer">
        <table class="table table-hover" id="myTB">
            <thead>
            <tr>
                <th>Дата</th>
                <th>Товар</th>
                <th>ФИО</th>
                <th>Телефон</th>
                <th width="20%"></th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="ap in appList">
                <td><span ng-bind="ap.date"></span></td>
                <td><span ng-bind="ap.assetIdAsset"></span></td>
                <td><span ng-bind="ap.fio"></span></td>
                <td><span ng-bind="ap.phone"></span></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</div>