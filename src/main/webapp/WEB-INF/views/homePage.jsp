<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-route.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet'
          type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-tooltip.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/tooltip.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/modernizr.custom.js" />"></script>
    >
    <script type="text/javascript" src="<c:url value="/resources/js/authorisationAngular.js" />"></script>
    <c:if test="${not empty error}">
        <script> alert("${error}");</script>
    </c:if>
</head>

<body ng-app="myApp" data-spy="scroll" data-offset="0" data-target="#navbar-main">

<div ng-controller="enranceFormConroller as enCtrl">
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
                            <li><a href="#about" class="smoothScroll"> О нас</a></li>
                            <li><a href="\goodsAdminPage" class="smoothScroll"
                                   data-placement="bottom"
                                   data-toggle="tooltip"
                                   data-original-title="Просмотр реализууемых товаров"> Услуги сервиса</a></li>
                            <li><a href="#contact" class="smoothScroll"
                                   data-placement="bottom"
                                   data-toggle="tooltip"
                                   data-original-title="Вам интересно,где находится наш офис, телефон для справок...?">Контакты</a>
                            </li>
                            <li><a ng-click="showFormAuthorisation()" href="#" class="smoothScroll "
                                   data-placement="bottom"
                                   data-toggle="tooltip"
                                   data-original-title="Вход в сервис"> Войти</a></li>
                            <li><a ng-click="showFormRegistration()" href="#" class="smoothScroll "
                                   data-placement="bottom"
                                   data-toggle="tooltip"
                                   data-original-title="Предоставляет расширенную информацию об реализуемом имуществе">Регистрация</a>
                            </li>
                        </ul>
                    </div><!--/.nav-collapse -->

                </div>
            </div>

        </div>
        <div dynamic="html"></div>
        <!-- ==== HEADERWRAP ==== -->
        <div id="headerwrap" id="home" name="home">
            <header class="clearfix">
                <h1><span class="icon icon-shield"></span></h1>
                <p>Ресурс ThemaBank</p>
                <p>для реализации залогового имущества</p>
            </header>
        </div><!-- /headerwrap -->
    </div>


    <!-- ==== ABOUT ==== -->
    <div class="container" id="about" name="about">
        <div class="row white">
            <br>
            <h1 class="centered">Немного о наших услугах</h1>
            <hr>

            <div class="col-lg-6">
                <p>Из-за специфики работы банка мы выдаём кредиты..., и зачастую организации в качестве залога для
                    кредита
                    предоставляют банку возможность, прописывая в договоре выдачи кредита, изъять прописанное в договоре
                    имущество предприятия-организации. Однако,чтобы его реализовать необходимо список реализуемого
                    имущества
                    опубликовать, для приёма заявок,что на данном ресурсе и осуществляется...</p>
            </div><!-- col-lg-6 -->

            <div class="col-lg-6">
                <p>Чтобы не напрягать вас бюрократическим аппаратом продажи)) каждый товар закреплён за определённым
                    менеджером,через которого вы можете проводить все операции : от косультации и до подписания договора
                    покупки... Ниже ппредставлены основные возможности,предоставляемые ресурсом вам.</p>
            </div><!-- col-lg-6 -->
        </div><!-- row -->
    </div><!-- container -->
    <!-- ==== GREYWRAP ==== -->
    <div id="greywrap" class="ContentMain">
        <div class="row">
            <div class="col-lg-4 callout">
                <span class="icon icon-stack"></span>
                <h2>Каталог</h2>
                <p>На ресурсе представлен каталог реализуемого ThemeBank имущества</p>
            </div><!-- col-lg-4 -->

            <div class="col-lg-4 callout">
                <span class="icon icon-eye"></span>
                <h2>Информация</h2>
                <p>Вы можете непосредственно ознакомитья с реализуемым списком товаров,зарегистрировавшись...<br>А также
                    получить конакты менеджера.</p>
            </div><!-- col-lg-4 -->

            <div class="col-lg-4 callout">
                <span class="icon icon-coin"></span>
                <h2>Заявка</h2>
                <p>Вы можете оставить заявку на покупку товара,после чего с вами свяжется менеджер,за которым он
                    закреплен</p>
            </div><!-- col-lg-4 -->

        </div><!-- row -->
        <div class="row">
            <div class="col-lg-4 callout">
                <span class="icon icon-phone"></span>
                <h2>Контакты</h2>
                <p>Сотрудники с ThemeBank с удовольствим ответят на ваши интересующие вопросы по заинтересовавшему вас
                    товару</p>
            </div><!-- col-lg-4 -->

            <div class="col-lg-4 callout">
                <span class="icon icon-thumbs-up"></span>
                <h2>Простота</h2>
                <p>Лёгкий для восприятия интерфейс не заставит вас напрягаться и думать: Где бы мне посмотреть...?</p>
            </div><!-- col-lg-4 -->

        </div><!-- row -->
    </div><!-- greywrap -->
    <!-- ==== SECTION DIVIDER1 -->
    <section class="section-divider textdivider divider1">
        <div class="container">
            <h1>Суд постановляет изьять по договорам предоставления кредита под залог</h1>
            <hr>
            <p>А мы предлагаем вам наше залоговое имущество</p>
        </div><!-- container -->
    </section><!-- section -->


    <!-- ==== SECTION DIVIDER2 -->
    <section class="section-divider textdivider divider2">
        <div class="container">
            <h1>Вы приобретаете и радуетесь</h1>
            <hr>
            <p>А мы рады что удовлетворили ваши и наши потребности</p>
        </div><!-- container -->
    </section><!-- section -->


    <!-- ==== GREYWRAP ==== -->
    <div id="greywrap" class="mYgreywrap">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 centered">

                    <img class="img-responsive" src=" <c:url value='/resources/img/macbook.png' />" align="">
                </div>
                <div class="col-lg-4">
                    <h2>Мы ждём ваших заявок</h2>
                    <p>Вы хотите просмотреть список товаров? Узнать, как связаться с нами? Мы работаем каждный
                        день(кроме
                        восскресенья) и ждём ваших звонков!</p>
                    <p><a class="btn btn-success">Перейти у услугам сервиса</a></p>
                </div>
            </div><!-- row -->
        </div>
        <br>
        <br>
    </div><!-- greywrap -->


    <!-- ==== SECTION DIVIDER6 ==== -->
    <section class="section-divider textdivider divider6">
        <div class="container">
            <h1>Центр реализации залогового имущества ThemаBank</h1>
            <hr>
            <p>Минск Иванова 987,Беларусь,</p>
            <p>+34 9884 4893</p>
            <p><a class="icon icon-twitter" href="#"></a> | <a class="icon icon-facebook" href="#"></a></p>
        </div><!-- container -->
    </section><!-- section -->

    <div class="container" id="contact" name="contact">
        <div class="row">
            <br>
            <h1 class="centered">СПАСИБО, ЧТО ВЫБРАЛИ НАС</h1>
            <hr>
            <br>
            <br>
            <div class="col-lg-4">
                <h3>Контактная информация</h3>
                <p><span class="icon icon-home"></span> Иванова 987, Миснк<br/>
                    <span class="icon icon-phone"></span> +34 9884 4893 <br/>
                    <span class="icon icon-mobile"></span> +34 59855 9853 <br/>
                    <span class="icon icon-envelop"></span> <a href="#"> thembank.by</a> <br/>
                    <span class="icon icon-twitter"></span> <a href="#"> @ThemаBanK_co </a> <br/>
                    <span class="icon icon-facebook"></span> <a href="#"> ThemаBank </a> <br/>
                </p>
            </div><!-- col -->


            <div class="col-lg-4">
                <h3>Поддержите нас</h3>
                <p>Зайдите на официальный сайт банка и оставьте отзыв о сервисе</p>
                <p><a href="#"> ThemаBank </a></p>
            </div><!-- col -->

        </div><!-- row -->

    </div><!-- container -->

    <div id="footerwrap">
        <div class="container">
            <h4> ThemаBank 2016</h4>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/retina.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.easing.1.3.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/smoothscroll.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-func.js" />"></script>
</body>
</html>

