var app = angular.module("myApp", ['ngResource', 'ngSanitize', 'ngRoute']);

app.directive('dynamic', function ($compile) {
    return {
        restrict: 'A',
        replace: true,
        link: function (scope, ele, attrs) {
            scope.$watch(attrs.dynamic, function (html) {
                ele.html(html);
                $compile(ele.contents())(scope);
            });
        }
    };
});
//////////Factory for manipulate objcet static_goods_group in bank///////////////
app.factory('StaticGoodsGroup', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource('/staticGoodsGroup');
}]);
//////////Factory for manipulate ojbcet User///////////////
app.factory('Users', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource(
        '/user/:username',
        {username: '@username'},
        {
            update: {
                method: 'PUT'
            }

        }
    );
}]);
//This controlller load  Authorisation form on the page
app.controller("enranceFormConroller",
    ['$sce', '$scope', '$http', 'StaticGoodsGroup','Users',
    function ($sce, $scope, $http, StaticGoodsGroup,Users) {

    $scope.user=new Users();

    $scope.showFormAuthorisation = function () {
        $http.get("/authFormGet").then(function successCallback(response) {

            $scope.html = $sce.trustAsHtml(response.data);

        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    }


    $scope.delAuthForm = function () {
        angular.element(document.querySelector('.Myform')).remove();
    }
    //////////////////////////////////////
    var self = this;
    $scope.bankGoodsGroup = new StaticGoodsGroup();
    $scope.bankGoodsGroupList = [];
    ///////////////////////////////////// получить форму регистрации пользователя с чекбоксами
    $scope.showFormRegistration = function () {
        $http.get("/registrationFormGet").then(function successCallback(response) {
            //делаем запрос на список chekbox
            $scope.bankGoodsGroupList = StaticGoodsGroup.query();
            if ($scope.bankGoodsGroupList.length == 0)
                alert("Регистрация не возможна, возможно сервис не приступил к работе");
            else {
                $scope.html = $sce.trustAsHtml(response.data);
            }

        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    }
    /////////////////////////////////////  зарегистрировать пользователя
    $scope.registrateClient = function () {

    }

}]);

