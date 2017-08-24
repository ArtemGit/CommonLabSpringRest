<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .image img {
    -moz-transition: all 1s ease-out;
    -o-transition: all 1s ease-out;
    -webkit-transition: all 1s ease-out;
    }

    .image img:hover{
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -o-transform: scale(1.1);
    }
    .image {
        overflow:hidden;
    }
</style>

<div id="greywrap">
    <div class="row">

        <div class="col-sm-6 col-md-4 groups-goods" ng-repeat="g in groups">
            <div class="thumbnail">
                <div class="image"><a href="#">
                <img   src="<c:url value="/resources/img/{{g.image}}"/>"/>
                </a></div>
                <div class="caption">
                    <span ng-bind="g.name"></span>
                    <p><a href="#" ng-click="getListGroupGoods(g.name)" class="btn btn-primary" role="button">Товары группы</a>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a href="#" ng-click="remove(g.name)" class="btn btn-default" role="button">Удалить группу</a></p>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div><!-- row -->
</div>
<!-- greywrap -->