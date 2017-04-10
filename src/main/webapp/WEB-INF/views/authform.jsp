<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/resources/css/authorisation.css' />" rel="stylesheet"/>

<div class='container Myform'>
    <div class='row'>
        <div class='col-xs-12 col-sm-12 col-lg-6'>
            <div class='panel panel-primary'>
                <div class='panel-heading'>
                    <h3 class='panel-title'>

                        <span id="forma-uth-head">Вход в панель администрирования </span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <link
                                ng-click="delAuthForm()" class="glyphicon glyphicon-remove-sign close-form">
                        </link>

                    </h3>

                </div>

                <div class='panel-body'>
                    <div class='row myrow'>

                        <div class='col-xs-6 col-sm-6 col-md-6 login-box'>
                            <form name='myform' method="POST"
                                  action="<%=request.getContextPath()%>/j_spring_security_check">
                                <div class='input-group'>
                                    <span class='input-group-addon'><span
                                            class='glyphicon glyphicon-user'></span></span>
                                    <input type='text' class='username form-control input-sm' name='user_login'
                                           ng-model="user_login" placeholder='Имя пользователя' required autofocus
                                           ng-minlength="3"/>
                                    <div class="has-error" ng-show="myform.user_login.$dirty">
                                        <span ng-show="myform.user_login.$error.required">Это поле обязательно для заполения</span>
                                        <span ng-show="myform.user_login.$error.minlength">Минимальная длина логина 3</span>
                                    </div>
                                </div>
                                <div class='input-group'>
                                    <span class='input-group-addon'><span
                                            class='glyphicon glyphicon-lock'></span></span>
                                    <input type='password' class='userpassword form-control' name="password_login"
                                           ng-model="fctrl.password_login" placeholder='Ваш пароль' required/>
                                    <div class="has-error" ng-show="myform.password_login.$dirty">
                                        <span ng-show="myform.password_login.$error.required">Это поле обязательно для заполения</span>
                                    </div>
                                </div>
                                <div class='panel-footer'>
                                    <div class='row'>
                                        <div class='col-xs-6 col-sm-6 col-md-6'>
                                            <div class='checkbox'>
                                                <label>
                                                    <input type='checkbox' name="_spring_security_remember_me"
                                                           value='Remember'>
                                                    Запомнить меня
                                                </label>
                                            </div>
                                        </div>
                                        <div class='col-xs-6 col-sm-6 col-md-6'>
                                            <button type='submit' class='btn btn-labeled btn-success '
                                                    ng-disabled="myform.$invalid">
                                                <span class='btn-label'><i class='glyphicon glyphicon-ok'></i></span>Войти
                                            </button>
                                        </div>
                                        <div class='col-xs-6 col-sm-6 col-md-6'>
                                            <button type='submit' class='btn btn-labeled btn-info'>
                                                <a  href="#" ng-click="showFormRegistration()"><span class='btn-label'><i class='glyphicon glyphicon-ok'></i>
                                                </span>Зарегистрироваться</a>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>