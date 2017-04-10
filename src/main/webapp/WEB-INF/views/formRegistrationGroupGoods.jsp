
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link href="<c:url value="/resources/css/userFormRegistrationStyle.css"/>" rel="stylesheet" />
<link href="<c:url value="/resources/css/userFormRegistrationStyle2.css"/>" rel="stylesheet" />
<%
    request.setCharacterEncoding("UTF-8");
    out.print(request.getParameter("groupname"));
%>
<div class="generic-container" >
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Форма добавления группы товара</span></div>
        <div class="formcontainer">
            <form  method="POST" action="/goodsAdminPage/data"  ng-model="myForm" name="myForm" class="form-horizontal" enctype="multipart/form-data">
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="groupname">Наименование группы</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="group.name" name="groupname" id="groupname" class="username form-control input-sm" placeholder="Введите название реализуемой группы товаров" required ng-minlength="5" ng-maxlength="25"/>
                            <div class="has-error" ng-show="myForm.username.$dirty">
                                <span ng-show="myForm.groupname.$error.required">Это поле обязательно для заполнения</span>
                                <span ng-show="myForm.groupname.$error.minlength">Минимальная длина названия 5 символов</span>
                                <span ng-show="myForm.groupname.$error.maxlength">Максимальная длина названия 25 символов</span>
                                <span ng-show="myForm.groupname.$invalid">Это поле заполненно некорректно</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="groupimage">Изображение группы</label>
                        <div class="col-md-7">
                            <input type="file" ng-model="group.image" name="image" id="groupimage" class="username form-control input-sm"  valid-file image-with-preview accept="image/jpeg,image/jpg,image/png" required multiple/>
                            <div class="has-error" ng-show="myForm.groupimage.$dirty">
                                <span ng-show="myForm.groupimage.$error.required">Это поле обязательно для заполнения .jpeg,.jpg,.png файлы</span>
                                <span ng-show="myForm.groupimage.$invalid">Это поле заполненно некорректно</span>
                                <span ng-show="ifExist(group.name)">Уже есть группа с наким названием</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="Добавить группу" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
