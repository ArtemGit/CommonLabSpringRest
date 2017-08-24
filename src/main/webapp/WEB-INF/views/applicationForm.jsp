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
                    <input type="text" ng-disabled="true" ng-model="application.fio" name="fio" id="fio" class="username form-control input-sm" placeholder="{{application.fio}}" required ng-minlength="10"/>
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
                <label class="col-md-2 control-lable" for="price">Ваше начальное предложение по цене</label>
                <div class="col-md-7">
                    <input type="number" ng-model="application.price" name="price" id="price" class="username form-control input-sm" placeholder="Введите начальное предложение" positive  required/>
                    <div class="has-error" ng-show="myForm.price.$dirty">
                        <span ng-show="myForm.price.$error.required">Это поле обязательно для заполнения</span>
                        <span ng-show="myForm.price.$invalid">Это поле заполненнр некорректно</span>
                        <span ng-show="myForm.price.$error.positive">Поле не может принимать отрицаткльные значения</span>
                    </div>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable" for="phone">Ваш Телефон</label>
                <div class="col-md-7">
                    <input type="text" ng-disabled="true" name="phone" ng-model="application.phone" id="phone" class="username form-control input-sm" ng-pattern="/^\+?375[- ]\d{2}[- ]+\d{7}$/" placeholder="{{application.phone}}" required />
                    <div class="has-error" ng-show="myForm.phone.$dirty">

                        <br><span  ng-show="myForm.phone.$error.pattern">Пожалуйста введите в формате +375-35-1234567 или 375-35-1234567</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-actions floatRight">
                <input type="submit"  value="Оставить заявку" class="btn btn-primary btn-sm add-ap-for-asset" ng-hide="${!status}" ng-disabled="myForm.$invalid"/>
                <input  ng-click="editApplication()" class="btn btn-warning btn-sm" value="Изменить"  ng-hide="${status}" ng-disabled="myForm.$invalid"/>
                <input  ng-click="closeForm()" class="btn btn-info " value="Скрыть форму"  />
            </div>
        </div>
    </form>
</div>
