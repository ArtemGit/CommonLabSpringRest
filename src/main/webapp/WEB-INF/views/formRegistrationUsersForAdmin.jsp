
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet" />
<link href="<c:url value="/resources/css/userFormRegistrationStyle2.css"/>" rel="stylesheet" />
<div class="generic-container" >
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Форма регистрации пользователей</span></div>
        <div class="formcontainer">
            <form ng-submit="submit()" ng-model="myForm" name="myForm" class="form-horizontal">
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uname">Имя пользователя</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="user.name" name="username" id="uname" class="username form-control input-sm" placeholder="Введите имя пользователя" required ng-minlength="3"/>
                            <div class="has-error" ng-show="myForm.username.$dirty">
                                <span ng-show="myForm.username.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.username.$error.minlength">Минимальная длина имени 3 символа</span>
                                <span ng-show="myForm.username.$invalid">Это поле заполненно некорректно</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="usurname">Фамилия пользователя</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="user.surname" name="usurname" id="usurname" class="username form-control input-sm" placeholder="Введите фамилию пользователя" required ng-minlength="3"/>
                            <div class="has-error" ng-show="myForm.usurname.$dirty">
                                <span ng-show="myForm.usurname.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.usurname.$error.minlength">Минимальная длина фамилии 3 символа</span>
                                <span ng-show="myForm.usurname.$invalid">Это поле заполненно некорректно</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uFname">Отчество пользователя</label>
                        <div class="col-md-7">
                            <input type="text" name="uFname" ng-model="user.fathername" id="uFname" class="username form-control input-sm" placeholder="Введите отчество пользователя" required ng-minlength="6"/>
                            <div class="has-error" ng-show="myForm.uFname.$dirty">
                                <span ng-show="myForm.uFname.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.uFname.$error.minlength">Минимальная длина отчества 6 символа</span>
                                <span ng-show="myForm.uFname.$invalid">Это поле заполненно некорректно</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="uphone">Телефон пользователя</label>
                        <div class="col-md-7">
                            <input type="text" name="uphone" ng-model="user.phone" id="uphone" class="username form-control input-sm" ng-pattern="/^\+?375[- ]\d{2}[- ]+\d{7}$/" placeholder="+375-35-1234567" required />
                            <div class="has-error" ng-show="myForm.uphone.$dirty">

                                <br><span  ng-show="myForm.uphone.$error.pattern">Пожалуйста введите в формате +375-35-1234567 или 375-35-1234567</span>
                                <span ng-show="myForm.uphone.$error.required">Это поле обязательно для заполнения</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="email">Email</label>
                        <div class="col-md-7">
                            <input type="email" ng-model="user.email" name="email" class="username form-control input-sm" placeholder="Введите Email пользователя" required/>
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
                            <input type="password" ng-model="user.password" name="upassword" class="userpassword username form-control input-sm" placeholder="Введите Пароль для пользователя" required ng-minlength="6"/>
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
                            <input type="password" ng-model="userpassword2" name="uPasswordRepeat" ng-minlength="6" class="userpassword2 username form-control input-sm" placeholder="Введите Пароль для пользователя" required/>
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
                        <label class="col-md-2 control-lable" for="login">Логин пользователя</label>
                        <div class="col-md-7">
                            <input type="text" ng-pattern="/^[a-z0-9_-]{3,16}$/" ng-model="user.username" name="login" class="username form-control input-sm" placeholder="Введите Логин пользователя" required  ng-minlength="7"/>
                            <div class="has-error" ng-show="myForm.login.$dirty">
                                <span ng-show="myForm.login.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.login.$invalid">Это поле заполненно некорректно</span>
                                <span ng-show="myForm.login.$error.minlength">Минимальная длинаЛогина 7 символов</span>
                                <span  ng-show="myForm.login.$error.pattern">Логин может содержать символы английского алфавита,-,_,цифры</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!ifExist(user.username) ? 'Добавить пользователя' : 'Обновить'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Очистить форму</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
