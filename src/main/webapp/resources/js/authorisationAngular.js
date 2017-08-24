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
        '/registrate/:username',
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
    ['$sce', '$scope', '$http', 'StaticGoodsGroup', 'Users',
        function ($sce, $scope, $http, StaticGoodsGroup, Users) {

            $scope.user = new Users();

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
                    $scope.bankGoodsGroupList = StaticGoodsGroup.query(function () {
                        $scope.html = $sce.trustAsHtml(response.data);
                    }, function (errresponse) {
                        alert("Ошибки работы сервиса");
                    });

                }, function errorCallback(errresponse) {
                    alert("Ошибки работы сервиса");
                });
            }
            ///////////////////////////////////проверить выбрана ли 1 группа хотябы
            /////////////////////////////////////  зарегистрировать пользователя
            $scope.registrateClient = function () {
                var chooseGoodsGroups = [];
                angular.forEach($scope.bankGoodsGroupList, function (group) {
                    if (group.selected)
                        chooseGoodsGroups.push(group.groupname);
                });
                if (chooseGoodsGroups.length > 0) {
                    $scope.user.$save(function () {

//добавляем логин он же телефон текущего пользователя в конец массива
                        chooseGoodsGroups.push($scope.user.phone);
                        $.ajax({
                            type: "GET",
                            url: "/client/desire/groups",
                            data: {
                                myArray: chooseGoodsGroups, //notice that "myArray" matches the value for @RequestParam
                                //on the Java side
                            },
                            success: function (response) {
                                alert("Вы успешно зарегистрированы!!!");
                            },
                            error: function (e) {
                                alert("Ошибки сервера:" + e.status + " регистрация невозможна!!");
                            }
                        });
                    }, function errorCallback(response) {
                        if (e.status === 409)
                            alert("Уже существует пользователь с таким телефоном!!");
                        else
                            alert("Ошибки сервера:" + e.status + " регистрация невозможна!!");

                    });
                }
                else alert("Выберите пожалуйста хотябы 1 группу товаров");
                angular.element(document.querySelector('.dynamicBlock')).empty();
            }
        }]);

