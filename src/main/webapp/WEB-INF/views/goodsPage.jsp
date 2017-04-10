<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <title>AngularJS $http Example</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet" />
    <link href="<c:url value="/resources/css/userFormRegistrationStyle2.css"/>" rel="stylesheet" />
<div class="generic-container">
    <div class="panel panel-default myPanel" dynamic="html3">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="panel-heading"><span class="lead">Форма контроля  товаров группы <span id="groupOfGoods">${group}</span></span></div>
        <div class="formcontainer">
            <form ng-submit="submit()" name="myForm" class="form-horizontal">
                <input type="hidden" ng-model="goods.idAsset" />
                <input type="hidden" ng-model="goods.goodsGroupName" />
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="assetNameModel">Нaименование товара</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="goods.assetNameModel" name="assetNameModel" id="assetNameModel" class="username form-control input-sm" placeholder="Введите наименование товара" required ng-minlength="4"/>
                            <div class="has-error" ng-show="myForm.assetNameModel.$dirty">
                                <span ng-show="myForm.assetNameModel.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.assetNameModel.$error.minlength">Минимальный размер поля 4 символа</span>
                                <span ng-show="myForm.assetNameModel.$invalid">Это поле заполненно некорректно</span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="assetCost">Стоимость товара</label>
                        <div class="col-md-7">
                            <input type="number" ng-model="goods.assetCost" name="assetCost" id="assetCost" class="username form-control input-sm" placeholder="Введите стоимость  товара" positive  required/>
                            <div class="has-error" ng-show="myForm.assetCost.$dirty">
                                <span ng-show="myForm.assetCost.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.assetNameModel.$invalid">Это поле заполненнр некорректно</span>
                                <span ng-show="myForm.assetCost.$error.positive">Поле не может принимать отрицаткльные значения</span>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="description">Описание товара</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="goods.description" name="description" id="description" class="username form-control input-sm" placeholder="Описсание товара" required ng-minlength="10"/>
                            <div class="has-error" ng-show="myForm.description.$dirty">
                                <span ng-show="myForm.description.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.description.$error.minlength">Минимальный размер поля 10 символов</span>
                                <span ng-show="myForm.description.$invalid">Это поле заполненнр некорректно</span>
                                  </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!goods.idAsset ? 'Добавить' : 'Обновить'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Очистить форму</button>
                    </div>
                </div>
            </form>
        </div>
        </sec:authorize>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Список товаров группы</span>
            <input type="text" placeholder="Поиск..." id="myInput" onkeyup="seachInUserTable()"/>
        </div>
        <div class="tablecontainer">
            <table class="table table-hover" id="myTB">
                <thead>
                <tr>
                    <th>ID.</th>
                    <th>Нименование товара</th>
                    <th>Стоимость</th>
                    <th>Описание</th>
                    <th>Контактное лицо по продажам</th>
                    <th>Кнтакты</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="g in listGoods">
                    <td><span ng-bind="g.idAsset"></span></td>
                    <td><span ng-bind="g.assetNameModel"></span></td>
                    <td><span ng-bind="g.assetCost"></span></td>
                    <td><span ng-bind="g.description"></span></td>
                    <td><span ng-bind="g.username"></span></td>
                    <td><span ng-bind="g.goodsGroupName"></span></td>
                    <td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <button type="button" ng-click="edit(g.idAsset)" class="btn btn-success custom-width">Изменить</button>
                        <button type="button" ng-click="removeAsset(g.idAsset)" class="btn btn-danger custom-width">Удалить</button>
                        </sec:authorize>
                        <button type="button" ng-click="makeApplication(g.idAsset,g.assetNameModel)" class="btn btn-primary custom-width">Заявка</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>