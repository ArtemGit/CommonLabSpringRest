<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet"/>

<div class="generic-container">
<div class="dynamic-html3" dynamic="html3"></div>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading"><span class="lead">Список заявок на покупку товаров</span>
        <input type="text" placeholder="Поиск..." id="myInput" onkeyup="seachInUserTable()"/></div>
</div>

<div class="tablecontainer">
<table class="table table-hover" id="myTB">
<thead>
<tr>
    <th>№</th>
    <th>Дата</th>
    <th>Товар</th>
    <th>ФИО</th>
    <th>Телефон</th>
    <th>Цена клиента</th>
    <th>Статус заявки</th>
    <th>Действия с заявкой</th>
</tr>
</thead>
<tbody>
<tr ng-repeat="ap in appList">
    <td><span ng-bind="ap.idapplication"></span></td>
<td><span ng-bind="ap.date"></span></td>
<td><span ng-bind="ap.assetIdAsset"></span></td>
<td><span ng-bind="ap.fio"></span></td>
<td><span ng-bind="ap.phone"></span></td>
<td><span ng-bind="ap.price"></span></td>
<td ng-switch="ap.status">
    <span ng-switch-when="1" class="label label-default ">Подана</span>
    <span ng-switch-when="2" class="label label-primary ">Отменена клиентом</span>
    <span ng-switch-when="3" class="label label-danger  ">В завке отказано</span>
    <span ng-switch-when="4" class="label label-success ">Принята оформляется</span>
    <span ng-switch-when="5" class="label label-warning ">Просмотрена банком</span>
    <span ng-switch-when="6">Удовлетворена</span>
</td>

<td>
<sec:authorize access="hasRole('ROLE_CLIENT')">
    <button ng-show="{{ap.status == 1}}" type="button" ng-click="editApplicationForm(ap)" class="btn btn-warning custom-width">Изменить</button>
    <button ng-show="{{ap.status == 1 || ap.status == 5}}" type="button" ng-click="changeApplicationStatus(ap,2,0)" class="btn btn-info custom-width">Отменить</button>
</sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
    <button ng-show="{{ap.status == 1 || ap.status == 4 || ap.status == 5}}" type="button" ng-click="changeApplicationStatus(ap,3,0)" class="btn btn-danger custom-width glyphicon glyphicon-minus"
            data-toggle="tooltip" data-placement="bottom" title="Отказать в заявке"></button>
        <button ng-show="{{ap.status == 1 || ap.status == 5}}" type="button" ng-click="changeApplicationStatus(ap,4,0)" class="btn btn-success custom-width glyphicon glyphicon-plus"
                data-toggle="tooltip" data-placement="bottom" title="Приянята оформляется"></button>
        <button ng-show="{{ap.status == 1 || ap.status == 3 || ap.status == 4}}" type="button" ng-click="changeApplicationStatus(ap,5,0)" class="btn btn-info glyphicon glyphicon-ok"
                data-toggle="tooltip" data-placement="bottom" title="Отметить как просмотренную"></button>
        <button ng-show="{{ap.status == 1 || ap.status == 4 || ap.status == 5}}" type="button" ng-click="formSetPriceRealisation(ap,6)" class="btn btn-outline-danger glyphicon glyphicon-usd"
                data-toggle="tooltip" data-placement="bottom" title="Отметить заявку и товар как реализованные"></button>
    </sec:authorize>
    </td>
    </tr>
    </tbody>
    </table>
    </div>
    </div>
    </div>