<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="<c:url value="/resources/js/view-image.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.1.1.slim.min.js" />"></script>

<style>
    .caption .my {
        margin-left: 500px;
    }

    .row {
        margin-left: 20px;
    }
    #image-asset-zoom
    {
        visibility: hidden;
    }
</style>
<%
    request.setCharacterEncoding("UTF-8");
    out.print(request.getParameter("groupImageID"));
%>
<div id="mask"></div>
<img id="image-asset-zoom"  ng-click="zoomOutImage()"
     src=""
     title="Кликните по изображению,чтобы закрыть окно просмотра"/>
<div id="asset-image-container">
<div class="row">
<div class="alert alert-info">
    <strong><h2>Инвентарный номер : ${inventNumber}</h2></strong>
</div>
<div class="alert alert-success">
    <strong><h2>Изображения по товару : ${nameAsset}</h2></strong>
</div>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <form method="POST" action="/add/image/for/asset" ng-model="myForm" name="myForm" class="form-horizontal"
          enctype="multipart/form-data">
        <input type="hidden" name="groupImageID" id="groupImageID" value="${inventNumber}"/>
        <div class="form-group col-md-12">
            <div class="col-md-7">
                <input type="file" ng-model="imageAsset" name="image" id="image"
                       class="username form-control input-sm" valid-file image-with-preview
                       accept="image/jpeg,image/jpg,image/png" required multiple/>
                <div class="has-error" ng-show="myForm.image.$dirty">
                    <span ng-show="myForm.image.$error.required">Это поле обязательно для заполнения .jpeg,.jpg,.png файлы</span>
                    <span ng-show="myForm.image.$invalid">Это поле заполненно некорректно</span>
                </div>
            </div>
        </div>
        <div class="form-actions floatRight">
            <input type="submit" value="Добавить Картинку" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
        </div>
    </form>
    </div>
</sec:authorize>
<!-- ==== GREYWRAP ==== -->
<div id="greywrap">
    <div class="row">

        <div class="col-sm-6 col-md-4 groups-goods" ng-repeat="im in listAssetImages">
            <div class="thumbnail">
                <img ng-click="zoomImage(im.nameImage)" src="<c:url value="/resources/img/{{im.nameImage}}"/>"/>
                <div class="caption">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a ng-click="deleteImage(im)" href="#" class="btn btn-primary" role="button">Удалить</a>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div><!-- row -->
</div>
</div>
<!-- greywrap -->

