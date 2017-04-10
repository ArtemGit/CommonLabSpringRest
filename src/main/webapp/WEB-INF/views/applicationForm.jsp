<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-heading"><span class="lead">Форма подачи заявки на товар  <span >{{assetOrdering}}</span></span></div>
<div class="formcontainer">
    <form ng-submit="registrAppl()" name="myForm" class="form-horizontal">
        <input type="hidden" ng-model="application.idapplication" />
        <input type="hidden" ng-model="application.assetIdAsset" />
        <input type="hidden" ng-model="application.date" />
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable" for="fio"> Ваше ФИО</label>
                <div class="col-md-7">
                    <input type="text" ng-model="application.fio" name="fio" id="fio" class="username form-control input-sm" placeholder="Введите ФИО" required ng-minlength="10"/>
                    <div class="has-error" ng-show="myForm.fio.$dirty">
                        <span ng-show="myForm.fio.$error.required">Это поле обязательно для заполнения</span>
                        <span ng-show="myForm.fio.$error.minlength">Минимальный размер поля 10 символов</span>
                        <span ng-show="myForm.fio.$invalid">Это поле заполненно некорректно</span>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable" for="phone">Ваш Телефон</label>
                <div class="col-md-7">
                    <input type="text" name="phone" ng-model="application.phone" id="phone" class="username form-control input-sm" ng-pattern="/^\+?375[- ]\d{2}[- ]+\d{7}$/" placeholder="+375-35-1234567" required />
                    <div class="has-error" ng-show="myForm.phone.$dirty">

                        <br><span  ng-show="myForm.phone.$error.pattern">Пожалуйста введите в формате +375-35-1234567 или 375-35-1234567</span>
                        <span ng-show="myForm.phone.$error.required">Это поле обязательно для заполнения</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-actions floatRight">
                <input type="submit"  value="Оставить заявку" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                <button type="button" ng-click="reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Очистить форму</button>
            </div>
        </div>
    </form>
</div>
