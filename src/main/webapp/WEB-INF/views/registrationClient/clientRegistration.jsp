<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet"/>
<link href="<c:url value="/resources/css/userFormRegistrationStyle2.css"/>" rel="stylesheet"/>
<style>
    .Myform {
        position: absolute;
        margin-top: 60px;
        margin-left: 150px;
    }</style>
<div class="generic-container Myform">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Регистрация</span></div>
        <div class="formcontainer">
            <form ng-submit="registrateClient()" ng-model="myForm" name="myForm" class="form-horizontal">
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uname">ФИО</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="user.username" name="username"
                                   class="username form-control input-sm" placeholder="Введите ваше ФИО"
                                   ng-pattern="/^[А-Яа-яЁё\s]{10,40}$/" required/>
                            <div class="has-error" ng-show="myForm.username.$dirty">
                                <span ng-show="myForm.username.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.username.$invalid">Это поле заполненно некорректно</span>
                                <span ng-show="myForm.username.$error.pattern">Пожалуйста вводите ФИО на русском языке от 10 до 40 символов</span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uphone">Телефон пользователя</label>
                        <div class="col-md-7">
                            <input type="text" name="uphone" ng-model="user.phone" id="uphone"
                                   class="username form-control input-sm" ng-pattern="/^\+?375[- ]\d{2}[- ]+\d{7}$/"
                                   placeholder="+375-35-1234567" required/>
                            <div class="has-error" ng-show="myForm.uphone.$dirty">

                                <br><span ng-show="myForm.uphone.$error.pattern">Пожалуйста введите в формате +375-35-1234567 или 375-35-1234567(городской в формате +375-17-1234567)</span>
                                <span ng-show="myForm.uphone.$error.required">Это поле обязательно для заполнения</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="email">Email</label>
                        <div class="col-md-7">
                            <input type="email" ng-model="user.email" name="email"
                                   class="username form-control input-sm" placeholder="Введите Email пользователя"
                                   required/>
                            <div class="has-error" ng-show="myForm.email.$dirty">
                                <span ng-show="myForm.email.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.email.$invalid">Это поле заполненно некорректно</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="upassword">Пароль</label>
                        <div class="col-md-7">
                            <input type="password" ng-model="user.password" name="upassword"
                                   class="userpassword username form-control input-sm" placeholder="Введите Пароль"
                                   required ng-minlength="6"/>
                            <div class="has-error" ng-show="myForm.upassword.$dirty">
                                <span ng-show="myForm.upassword.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.upassword.$invalid">Это поле заполненно некорректно</span>
                                <span ng-show="myForm.upassword.$error.minlength">Минимальная длина пароля 6 символов</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uPasswordRepeat">Повтор пароля</label>
                        <div class="col-md-7">
                            <input type="password" ng-model="userpassword2" name="uPasswordRepeat" ng-minlength="6"
                                   class="userpassword2 username form-control input-sm" placeholder="Повторите пароль"
                                   required/>
                            <div class="has-error" ng-show="myForm.uPasswordRepeat.$dirty">
                                <span ng-show="myForm.uPasswordRepeat.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.uPasswordRepeat.$invalid">Это поле заполненно некорректно</span>
                                <span ng-show="userpassword2!=user.password">Пароли не совпадают!!</span>
                                <span ng-show="myForm.uPasswordRepeat.$error.minlength">Минимальная длина пароля 6 символов</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uPasswordRepeat">Выберите интересующие группы
                            товаров</label>
                        <div class="col-md-7">

                            <label ng-repeat="group in listgroups">
                                <input type="checkbox" ng-model="group.selected" value="{{group.name}}"> {{group.name}}
                            </label>


                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit" value="Зарегистрироваться" class="btn btn-primary btn-sm"
                               ng-disabled="myForm.$invalid">
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
