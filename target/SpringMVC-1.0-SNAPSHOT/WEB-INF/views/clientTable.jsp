<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet"/>
<div >
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Список Клинтов Сервиса</span>
            <input type="text" placeholder="Поиск..." id="myInput" onkeyup="seachInUserTable()"/></div>
    </div>

    <div class="tablecontainer">
        <table class="table table-hover" id="myTB">
            <thead>
            <tr>
                <th>ФИО</th>
                <th>Email</th>
                <th>Телефон</th>
                <th>Дата Регистрации</th>
                <th width="20%"></th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="u in users">
                <td><span ng-bind="u.name"></span></td>
                <td><span ng-bind="u.email"></span></td>
                <td><span ng-bind="u.phone"></span></td>
                <td><span ng-bind="u.date"></span></td>
                <td>
                    <button type="button" ng-click="remove(u.username)" class="btn btn-danger custom-width">Удалить
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</div>