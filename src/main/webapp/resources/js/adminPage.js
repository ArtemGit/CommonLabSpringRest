var app = angular.module("adminApp", ['ngResource', 'ngSanitize', 'ngRoute']);


//////////////directive for compiling getting from server parts of html
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
app.controller("adminController", ['$sce', '$scope', '$http', 'Users', function ($sce, $scope, $http, Users) {

////////////////count workers get from server

    var self = this;
    $scope.user = new Users();
//////////////////get form for registing User
    $scope.showFormUserRegistration = function () {

        //empty content

        angular.element(document.querySelector('.dynamicBlock')).empty();
        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
        angular.element(document.querySelector('.row.start')).remove();
        //get form from server

        $http.get("/adminPage/UserRegistration").then(function successCallback(response) {
            $scope.html = $sce.trustAsHtml(response.data);
        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    }
//////////////////////////////////////////////
//////////////////get table workerUser watching
    $scope.showWorkersList = function () {

        //emptu content
        angular.element(document.querySelector('.dynamicBlock')).empty();
        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
        angular.element(document.querySelector('.row.start')).remove();
        //get form from server
        $http.get("/adminPage/tableUsers").then(function successCallback(response) {
            self.fetchAllUsers();
            $scope.html2 = $sce.trustAsHtml(response.data);
        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    }
//////////////////////////////////////////////
    //////////////////get table clients watching
    $scope.showClientsList = function () {

        //empty content
        angular.element(document.querySelector('.dynamicBlock')).empty();
        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
        angular.element(document.querySelector('.row.start')).remove();
        //get form from server
        $http.get("/adminPage/tableClients").then(function successCallback(response) {
            self.fetchAllClients();
            $scope.html2 = $sce.trustAsHtml(response.data);
        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    }
//////////////////////////////////////////////
//////////////////////////////////////////////

//////////////////////////////////////////////


    ////////////////////////delete message from server
    $scope.deleteAlert = function () {
        angular.element(document.querySelector('.Myclose')).remove();
    };
    ///////////////////////////////////////////////////////
    $scope.users = [];

    //Get all users
    self.fetchAllUsers = function () {
     $scope.users = Users.query();
     };
    //Get all users
    self.fetchAllClients = function () {
            $http.get('/user/clientsList')
                .then(function(result) {
                    $scope.users  = result.data;
                });
    };
    //Add user to DB
    self.createUser = function () {

        $scope.user.$save(function () {
                    $http.get("/user/addSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        angular.element(document.querySelector('.dynamicBlock')).empty();
                        $scope.html2 = $sce.trustAsHtml(response.data);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
            },
            function (errorResult) {
                if (errorResult.status === 409)
                    $http.get("/user/addErrorMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        angular.element(document.querySelector('.dynamicBlock')).empty();
                        $scope.html2 = $sce.trustAsHtml(response.data);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });

            }
        );
    };

    //Change information about User
    self.updateUser = function () {
        $scope.user.$update(function () {
                $http.get("/user/updateSuccessMessage").then(function successCallback(response) {
                    angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                    $scope.html3 = $sce.trustAsHtml(response.data);
                    self.fetchAllUsers();
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
            },
            function (errorResult) {
                if (errorResult.status === 404)
                    $http.get("/user/updateErrorMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        $scope.html3 = $sce.trustAsHtml(response.data);
                        self.fetchAllUsers();
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });

            });
    };

     self.deleteUser = function (identity) {
        var user = Users.get({username: identity}, function () {
            user.$delete(function () {
                    $http.get("/user/deleteSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        $scope.html3 = $sce.trustAsHtml(response.data);
                        self.fetchAllUsers();
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                },
                function (errorResult) {
                    if (errorResult.status === 404)
                        $http.get("/user/deleteErrorMessage").then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                            $scope.html3 = $sce.trustAsHtml(response.data);
                            self.fetchAllUsers();
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                });
        });
    };
    //Constant work for display information
    self.fetchAllUsers();
    //On submit Create or Update User
    $scope.submit = function () {
        if(!$scope.ifExist($scope.user.username)){
            console.log('Saving New User', $scope.user);
            self.createUser();
        }else{
            console.log('Upddating user with id ', $scope.user.username);
            self.updateUser();
        }
        self.reset();
    };

    $scope.edit = function (username) {
        console.log('login to be edited', username);
        for (var i = 0; i < $scope.users.length; i++) {
            if ($scope.users[i].username === username) {
                $scope.user = angular.copy($scope.users[i]);
                break;
            }
        }
        $scope.showFormUserRegistration();
    };

    $scope.remove = function (username) {
     console.log('login to be deleted', username);
     self.deleteUser(username);
     };

    //reset Form
    self.reset = function () {
        $scope.user = new Users();
        $scope.myForm.$setPristine();
    };
$scope.ifExist=function(username)
{
    for (var i = 0; i < $scope.users.length; i++) {
        if ($scope.users[i].username === username) {
            return true;
        }
    }
    return false;
}
}]);


//Seach in Table on JavaScript
function seachInUserTable() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTB");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        if (tr[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
    }};
    /////////////////////////////////////
