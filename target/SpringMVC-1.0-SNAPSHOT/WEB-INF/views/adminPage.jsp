w

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Панель администрирования</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link href="<c:url value="/resources/css/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" />
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link href="<c:url value="/resources/css/adminPageStyles/css/AdminLTE.min.css"/>" rel="stylesheet" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link href="<c:url value="/resources/css/adminPageStyles/css/skins/_all-skins.min.css"/>" rel="stylesheet" />
    <!--Angular-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.js"></script>
    <script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini" ng-app="adminApp" ng-controller="adminController">

<header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>A</b>DM</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>Панель</b> Админа</span>
    </a>

</header>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">

                <img src="<c:url value="/resources/css/adminPageStyles/img/user2-160x160.jpg"/>"  class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>Администратор</p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">Навигация</li>
            <li class="active treeview">
                <a href="#">
                    <i class="fa fa-dashboard"></i> <span>Действия</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li ng-click="showFormUserRegistration()" class="active"><a href="#"><i class="fa fa-circle-o" ></i> Доабвление Управленцев</a></li>
                    <li><a href="/adminPage"><i class="fa fa-circle-o"></i>На главную панель</a></li>
                    <li ng-click="showWorkersList()"><a href="#"><i class="fa fa-circle-o"></i> Управленцы</a></li>
                    <li ng-click="showClientsList()"><a href="#"><i class="fa fa-circle-o"></i> Клиенты</a></li>
                </ul>
            </li>





        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            Рабочая область
        </h1>
        <ol class="breadcrumb">
            <li><a href="j_spring_security_logout"><i class="fa fa-dashboard"></i> Выйти на главную старницу</a></li>
            <li  class="active">Панель админа пользователей</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content start">
        <div class="dynamicBlockMessage2" dynamic="html3"></div>
        <div class="dynamicBlock" dynamic="html"></div>
`        <div class="dynamicBlockMessage" dynamic="html2"></div>
        <!-- Small boxes (Stat box) -->
        <div class="row start">

            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-green">
                    <div class="inner">
                        <h3>${countVisitors}<sup style="font-size: 20px"> чел</sup></h3>

                        <p>Посетители</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-stats-bars"></i>
                    </div>
                    <a href="#" ng-click="showClientsList()" class="small-box-footer">Подробнее<i class="fa fa-arrow-circle-right"></i></a>

                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-yellow">
                    <div class="inner" >
                        <h3>${countWorkers}<sup style="font-size: 20px"> чел</sup></h3>

                        <p>Регистрация Управленца</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-person-add"></i>
                    </div>
                    <a href="#" ng-click="showFormUserRegistration()" class="small-box-footer">Подробнее<i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>

        </div>
        <!-- /.row -->
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->
<footer class="main-footer">
</footer>


</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="<c:url value="/resources/js/plugins/jQuery/jquery-2.2.3.min.js"/>"></script>

<!-- jQuery UI 1.11.4 -->
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.6 -->
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="<c:url value="/resources/js/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"/>"></script>
<!-- Slimscroll -->
<script src="<c:url value="/resources/js/plugins/slimScroll/jquery.slimscroll.min.js"/>"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/resources/css/adminPageStyles/js/app.min.js"/>"></script>
<!--Custom realizan=tion Angular on admin page-->
<script src="<c:url value="/resources/js/adminPage.js"/>"></script>
</body>
</html>
