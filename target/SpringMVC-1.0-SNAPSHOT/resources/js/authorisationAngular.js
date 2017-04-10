var app = angular.module("myApp", ['ngSanitize', 'ngRoute']);

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
//This controlller load  Authorisation form on the page
app.controller("enranceFormConroller", function ($sce, $scope, $http) {

    $scope.showFormAuthorisation = function () {
        $http.get("/authFormGet").then(function successCallback(response) {

            $scope.html = $sce.trustAsHtml(response.data);

        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    };



    $scope.delAuthForm = function () {
        angular.element(document.querySelector('.Myform')).remove();
    };

    /////////////////////////////////////
    $scope.showFormRegistration = function () {
        $http.get("/registrationFormGet").then(function successCallback(response) {

            $scope.html = $sce.trustAsHtml(response.data);

        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    };
    /////////////////////////////////////

    //////////Factory for manipulate ojbcet client///////////////
    app.factory('Application', ['$resource', function ($resource) {
        //$resource() function returns an object of resource class
        return $resource(
            '/goodsAdminPage/application/:idAp',
            {idAp: '@idAp'},
            {
                update: {
                    method: 'PUT'
                }

            }
        );
    }]);
    /////////////////////////////////////
    registrateClient
});

