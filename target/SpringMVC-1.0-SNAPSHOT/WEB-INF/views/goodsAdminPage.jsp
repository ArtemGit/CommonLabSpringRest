<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="SHIELD - Free Bootstrap 3 Theme">
    <meta name="author" content="Carlos Alvarez - Alvarez.is - blacktie.co">


    <title>ThemaBank</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value='/resources/css/bootstrap/css/bootstrap.css' />" rel="stylesheet"/>
    <!-- Custom styles for this template -->
    <link href="<c:url value='/resources/css/main.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/icomoon.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/animate-custom.css' />" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/goodsAdminStyle.css' />" rel="stylesheet"/>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet'
          type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-tooltip.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/tooltip.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
</head>

<body ng-app="myApp" ng-controller="Controller as enCtrl" data-spy="scroll" data-offset="0" data-target="#navbar-main">

<div>
    <div id="content">
        <div id="navbar-main">
            <!-- Fixed navbar -->
            <div class="navbar navbar-inverse navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse"
                                data-target=".navbar-collapse">
                            <span class="icon icon-shield" style="font-size:30px; color:#3498db;"></span>
                        </button>
                        <a class="navbar-brand hidden-xs hidden-sm" href="#home"><span class="icon icon-shield"
                                                                                       style="font-size:18px; color:#3498db;"></span></a>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li><a href="\homePage" class="smoothScroll">Home</a></li>
                            <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                                <li><a href="j_spring_security_logout" class="smoothScroll "
                                       data-placement="bottom"
                                       data-toggle="tooltip"
                                       data-original-title="Выход на главную страницу">Выйти</a></li>
                            </sec:authorize>
                            <li><a href="\goodsAdminPage" class="smoothScroll "
                                   data-placement="bottom"
                                   data-toggle="tooltip"
                                   data-original-title="Вернуться к группам товаров"> Группы</a></li>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li><a ng-click="showFormRegistrationGroupGoods()"
                                       href="#" class="smoothScroll "
                                       data-placement="bottom"
                                       data-toggle="tooltip"
                                       data-original-title="Добавление группы реализуемых товаров(Автомобили...)">Добавить
                                    группу товаров</a></li>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') ">
                                <li><a ng-click="showApplicationList()"
                                       href="#" class="smoothScroll "
                                       data-placement="bottom"
                                       data-toggle="tooltip"
                                       data-original-title="Список заявок на реализуемые товары">Просмотреть заявки</a>
                                </li>
                            </sec:authorize>

                        </ul>
                    </div><!--/.nav-collapse -->

                </div>
            </div>

        </div>
    </div>
</div>
<div class="dynamicBlockMessage" dynamic="html"></div>
<div class="dynamicBlockMessage2" dynamic="html2"></div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/retina.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.easing.1.3.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/smoothscroll.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-func.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/goodsAdmin.js" />"></script>
</body>
</html>

