<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <title>ThemaBank</title>
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
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="staticAssetStatusIdstaticAssetStatus">Статус товара</label>
                        <div class="col-md-7">
                            <select name="staticAssetStatusIdstaticAssetStatus"
                                    id="staticAssetStatusIdstaticAssetStatus"
                                    class="selectpicker"
                                    ng-change="showSelectValueStaticAssetStatusIdstaticAssetStatus()"
                                    ng-model="selectedStatus"
                                    ng-init="selectedStatus  = staticAssetStatusIdstaticAssetStatusList[0]"
                                    ng-options="item.idstaticAssetStatus for item in staticAssetStatusIdstaticAssetStatusList">

                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!goods.idAsset ? 'Добавить' : 'Обновить'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || chooseIdstaticAssetStatus==0">
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
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <button type="button" ng-click="listAssetsBy('${group}','release')"
                        class="btn btn-success icon icon-coin"
                        data-toggle="tooltip"
                        data-placement="bottom"
                        title="Реализованные товары"></button>
                <button type="button" ng-click="listAssetsBy('${group}','old')"
                        class="btn btn-danger glyphicon glyphicon-time"
                        data-toggle="tooltip"
                        data-placement="bottom"
                        title="Товары реализуемые более 2 месяцев"></button>
                <button type="button" ng-click="getListGroupGoods('${group}')"
                        class="btn btn-warning glyphicon glyphicon-th-list"
                        data-toggle="tooltip"
                        data-placement="bottom"
                        title="Товары реализуемые сейчас"></button>
            </sec:authorize>
        </div>
        <div class="tablecontainer">
            <table class="table table-hover" id="myTB">
                <thead>
                <tr>
                    <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                    <th>ID.</th>
                    </sec:authorize>
                    <th>Нименование товара</th>
                    <th>Стоимость</th>
                    <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                    <th>Описание</th>
                    <th>Контактное лицо по продажам</th>
                    <th>Кнтакты</th>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <th>Статус</th>
                    </sec:authorize>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="g in listGoods">
                    <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                    <td><span ng-bind="g.idAsset"></span></td>
                    </sec:authorize>
                    <td><span ng-bind="g.assetNameModel"></span></td>
                    <td><span ng-bind="g.assetCost"></span></td>
                    <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                    <td><span ng-bind="g.description"></span></td>
                    <td><span ng-bind="g.username"></span></td>
                    <td><span ng-bind="g.goodsGroupName"></span></td>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <td><span ng-bind="g.staticAssetStatusIdstaticAssetStatus"></span></td>
                    </sec:authorize>
                    <td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <button type="button" ng-click="edit(g.idAsset)" class="btn btn-warning custom-width">Изменить</button>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')">
                            <button type="button" ng-click="imageList(g.idAsset)" class="btn btn-danger custom-width">Картинки</button>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <button type="button" ng-click="removeAsset(g.idAsset)" class="btn btn-success custom-width">Удалить</button>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_CLIENT')">
                        <button type="button" ng-click="makeApplication(g.idAsset,g.assetNameModel)" class="btn btn-primary custom-width">Заявка</button>
                        </sec:authorize>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>