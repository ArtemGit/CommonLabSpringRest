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
app.directive('validFile',function(){
    return {
        require:'ngModel',
        link:function(scope,el,attrs,ngModel){

            //change event is fired when file is selected
            el.bind('change',function(){
                scope.$apply(function(){
                    ngModel.$setViewValue(el.val());
                    ngModel.$render();
                });
            });
        }
    };
});
//////////Factory for manipulate ojbcet goodsGroups///////////////
app.factory('Goodsgroup', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource(
        '/goodsAdminPage/data/:name',
        {name: '@name'},
        {
            update: {
                method: 'PUT'
            }

        }
    );
}]);
//////////Factory for manipulate ojbcet goodsGroups///////////////
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
////////////////////////Goods Interface
app.directive('positive', [function() {
    return {
        require: 'ngModel',
        link: function(scope, elem, attrs, ctrl) {
            if (!ctrl) return;
            ctrl.$validators.positive = function(value) {
                return value && value >= 0;
            };
        }
    };
}]);
//////////Factory for manipulate ojbcet Goods///////////////
app.factory('Asset', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource(
        '/goodsAdminPage/asset/:idAsset',
        {idAsset: '@idAsset'},
        {
            update: {
                method: 'PUT'
            }

        }
    );
}]);
//This controlller load  Authorisation form on the page
app.controller("Controller", ['$sce', '$scope', '$http', 'Goodsgroup','Asset','Application', function ($sce, $scope, $http, Goodsgroup,Asset,Application) {

    var self = this;
    $scope.group = new Goodsgroup();
    $scope.groups = [];
    $scope.listGoods = [];
    self.showListGroups = function () {
        ///get list groups
        $scope.groups = Goodsgroup.query(function (success) {
            if($scope.groups.length===0)
                $http.get("/goodsAdminPage/getWarningMessage").then(function successCallback(response) {
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    $scope.html = $sce.trustAsHtml(response.data);
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
            else {
                angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                $http.get("/goodsAdminPage/listGoodsGroups").then(function successCallback(response) {
                    angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                    $scope.html2 = $sce.trustAsHtml(response.data);
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
            }
            }
        );

    };
    self.showListGroups();
    $scope.showFormRegistrationGroupGoods=function()
    {
        $http.get("/goodsAdminPage/formRegistrationGroupGoods").then(function successCallback(response) {
            angular.element(document.querySelector('.dynamicBlockMessage')).empty();
            angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
            $scope.html = $sce.trustAsHtml(response.data);
        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    }

    $scope.ifExist=function(name)
    {
        for (var i = 0; i < $scope.groups.length; i++) {
            if ($scope.groups[i].name === name) {
                return true;
            }
        }
        return false;
    }

$scope.remove=function(name)
    {
        var group = Goodsgroup.get({name: name}, function () {
            group.$delete(function () {
                    $http.get("/goodsAdminPage/deleteSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html = $sce.trustAsHtml(response.data);
                        self.showListGroups();
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                },
                function (errorResult) {
                    if (errorResult.status === 404)
                        $http.get("/goodsAdminPage/deleteErrorMessage").then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                            angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                            $scope.html = $sce.trustAsHtml(response.data);
                            self.showListGroups();
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });
                    if (errorResult.status === 403)
                        $http.get("/goodsAdminPage/deleteErrorMessageGroupIsNotEmpty").then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                            angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                            $scope.html = $sce.trustAsHtml(response.data);
                            self.showListGroups();
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                });
        });
    }

//////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
/////////////////Goods interface//////////////////////////////////////////////
  /* $scope.ifExistGoodsWithSuchName=function(goodsName)
   {
       for(var i=0;i<$scope.listGoods.length;i++)
           if($scope.listGoods[i].assetNameModel.toLowerCase()=== goodsName)
               return true;
       return false;
   }*/
    $scope.goods = new Asset();
  self.showAllGoods=function(groupname)
  {
      $http
      ({
          url: "/goodsAdminPage/listGoodsGroups/goodsPage/html/"+groupname,
          method: "GET",
      })
          .then(function successCallback(response) {
              angular.element(document.querySelector('.dynamicBlockMessage')).empty();
              angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
              $scope.html2 = $sce.trustAsHtml(response.data);
              $http
              ({
                  url: "/goodsAdminPage/listGoodsGroups/goodsPage/"+groupname,
                  method: "GET",
              })
                  .then(function successCallback(response) {
                      $scope.listGoods = response.data;
                  }, function errorCallback(response) {
                      alert("Ошибки работы сервиса");
                  });
          }, function errorCallback(response) {
              alert("Ошибки работы сервиса");
          });
  }
   $scope.getListGroupGoods=function(groupname)
   {
       self.showAllGoods(groupname);

   };

    self.createGoods = function () {
        $scope.goods.goodsGroupName=  angular.element(document.querySelector('#groupOfGoods')).html();
        var name= $scope.goods.goodsGroupName;
        $scope.goods.$save(function () {
                $http.get("/goodsAdminPage/addGoodsSuccessMessage").then(function successCallback(response) {
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    $scope.html= $sce.trustAsHtml(response.data);
                    self.showAllGoods(name);
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
            },
            function (errorResult) {
                if (errorResult.status === 409)
                    $http.get("/goodsAdminPage/addGoodsErrorMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html = $sce.trustAsHtml(response.data);
                        self.showAllGoods(name);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });

            }
        );
    };
    self.updateGoods = function () {
        $scope.goods.goodsGroupName =  angular.element(document.querySelector('#groupOfGoods')).html();
        var name= $scope.goods.goodsGroupName;
        new Asset($scope.goods).$update(function () {
                $http.get("/goodsAdminPage/updateGoodsSuccessMessage").then(function successCallback(response) {
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    $scope.html = $sce.trustAsHtml(response.data);
                    self.showAllGoods(name);
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
            },
            function (errorResult) {
                if (errorResult.status === 404)
                    $http.get("/goodsAdminPage/updateGoodsErrorMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html = $sce.trustAsHtml(response.data);
                        self.showAllGoods(name);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });

            });
    };
    $scope.edit = function (id) {
        console.log('id to be edited', id);
        for (var i = 0; i < $scope.listGoods.length; i++) {
            if ($scope.listGoods[i].idAsset === id) {
                $scope.goods = angular.copy($scope.listGoods[i]);
                break;
            }
        }
    };
    $scope.submit = function() {
        if($scope.goods.idAsset==null){
            console.log('Saving New Goods', $scope.goods.idAsset);
            self.createGoods();
        }else{
            console.log('Upddating Goods with id ', $scope.goods.idAsset);
            self.updateGoods();
            console.log('Goods updated with id ', $scope.goods.idAsset);
        }
        self.reset();
    };
    //reset Form
    self.reset = function () {
        $scope.goods = new Asset();
        $scope.myForm.$setPristine();
    };
    $scope.removeAsset = function (id) {
        console.log('goods to be deleted', id);
        self.deleteGoods(id);
    };
    self.deleteGoods = function (id) {
        $scope.goods.goodsGroupName=  angular.element(document.querySelector('#groupOfGoods')).html();
        var name= $scope.goods.goodsGroupName;
        var goods = Asset.get({idAsset: id}, function () {
            goods.$delete(function () {
                    $http.get("/goodsAdminPage/deleteGoodsSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html = $sce.trustAsHtml(response.data);
                         self.showAllGoods(name);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                },
                function (errorResult) {
                    if (errorResult.status === 404)
                        $http.get("/goodsAdminPage/deleteGoodsErrorMessage").then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                            $scope.html = $sce.trustAsHtml(response.data);
                            self.showAllGoods(name);
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });


                });
        });
    };
///////////////////////////Applications
    $scope.application=new Application();
    var assetIdAsset;
    $scope.appList=[];
    $scope.makeApplication=function(idAsset,assetNameModel)
    {
        $http.get("/goodsAdminPage/formApplicationForGoods").then(function successCallback(response) {
            angular.element(document.querySelector('.myPanel')).empty();
            $scope.html3 = $sce.trustAsHtml(response.data);
            $scope.assetOrdering = assetNameModel;
            assetIdAsset=idAsset;
        }, function errorCallback(response) {
            alert("Ошибки работы сервиса");
        });
    };
    $scope.registrAppl=function () {
        $scope.application.assetIdAsset=assetIdAsset;
        $scope.application.date=convertDate(new Date());
            $scope.application.$save(function () {
                    angular.element(document.querySelector('.myPanel')).empty();
                    $http.get("/goodsAdminPage/addApplicationSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html= $sce.trustAsHtml(response.data);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                },
                function (errorResult) {
                    if (errorResult.status === 409)
                        $http.get("/goodsAdminPage/addApplicationErrorMessage").then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                            $scope.html = $sce.trustAsHtml(response.data);
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                }
            );
        self.reset();
    };
    $scope.showApplicationList=function () {
        $scope.appList = Application.query(function (success) {
                if($scope.appList.length===0)
                    $http.get("/goodsAdminPage/applicationList/getWarningMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html = $sce.trustAsHtml(response.data);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                else {
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    $http.get("/goodsAdminPage/applicationList/listApplications").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        $scope.html2 = $sce.trustAsHtml(response.data);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                }
            }
        );
    };
}]);


/////////////////////////////////////
function convertDate(date) {
    var yyyy = date.getFullYear().toString();
    var mm = (date.getMonth()+1).toString();
    var dd  = date.getDate().toString();

    var mmChars = mm.split('');
    var ddChars = dd.split('');

    return yyyy + '-' + (mmChars[1]?mm:"0"+mmChars[0]) + '-' + (ddChars[1]?dd:"0"+ddChars[0]);
}
