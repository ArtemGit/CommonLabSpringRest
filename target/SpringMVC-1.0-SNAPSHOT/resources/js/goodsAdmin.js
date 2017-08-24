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
app.directive('validFile', function () {
    return {
        require: 'ngModel',
        link: function (scope, el, attrs, ngModel) {

            //change event is fired when file is selected
            el.bind('change', function () {
                scope.$apply(function () {
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
app.directive('positive', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            if (!ctrl) return;
            ctrl.$validators.positive = function (value) {
                return value > 0;
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
app.controller("Controller", ['$resource', '$sce', '$scope', '$http', 'Goodsgroup', 'Asset', 'Application',
    'StaticGoodsGroup', 'StaticAssetStatus', 'AssetImage',
    function ($resource, $sce, $scope, $http, Goodsgroup, Asset, Application,
              StaticGoodsGroup, StaticAssetStatus, AssetImage) {

        var self = this;
        $scope.group = new Goodsgroup();
        $scope.groups = [];
        $scope.listGoods = [];
        self.showListGroups = function () {
            ///get list groups
            $scope.groups = Goodsgroup.query(function (success) {
                    if ($scope.groups.length === 0)
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
        /////////////////////для выбора группы из выпадающего списка
        $scope.chooseGoodsGroup = 0;
        $scope.showSelectValue = function () {
            $scope.chooseGoodsGroup = $scope.selectedGroup;
        };
        $scope.staticGoodsGroupList = [];
        $scope.bankGoodsGroup = new StaticGoodsGroup();

        $scope.showFormRegistrationGroupGoods = function () {
            $http.get("/goodsAdminPage/formRegistrationGroupGoods").then(function successCallback(response) {
                $scope.staticGoodsGroupList = StaticGoodsGroup.query(function () {
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                    $scope.html = $sce.trustAsHtml(response.data);
                }, function (errresponse) {
                    alert("Ошибки работы сервиса");
                });

            }, function errorCallback(response) {
                alert("Ошибки работы сервиса");
            });
        }

        $scope.ifExist = function (name) {
            for (var i = 0; i < $scope.groups.length; i++) {
                if ($scope.groups[i].name === name) {
                    return true;
                }
            }
            return false;
        }

        $scope.remove = function (name) {
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

        ////////////////////////////////////////////////////// для статусов товаров
        $scope.chooseIdstaticAssetStatus = 0;
        $scope.showSelectValueStaticAssetStatusIdstaticAssetStatus = function () {
            $scope.chooseIdstaticAssetStatus = $scope.selectedStatus;
        };
        $scope.staticAssetStatusIdstaticAssetStatusList = [];
        $scope.staticAssetStatusIdstaticAssetStatus = new StaticAssetStatus();
        //////////////////////////////////////////////////////
        $scope.goods = new Asset();
        var select;
        self.showAllGoods = function (groupname) {
            $http
            ({
                url: "/goodsAdminPage/listGoodsGroups/goodsPage/html/" + groupname,
                method: "GET",
            })
                .then(function successCallback(response) {
                    //запрос за формой
                    //но просим сначала для селекта данные -  потом отображаем
                    /////////////////////для выбора группы из выпадающего списка
                    $scope.staticAssetStatusIdstaticAssetStatusList = StaticAssetStatus.query(function (responce) {
                        //////////////////////чистим динамик блоки и вставляем полученную форму с данными селекта
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                        $scope.html2 = $sce.trustAsHtml(response.data);
                        ///////////////////////////////////////////////////////////////////////////////////////
                        //////////////////просим данные по товарам
                        $http
                        ({
                            url: "/goodsAdminPage/listGoodsGroups/goodsPage/" + groupname,
                            method: "GET",
                        })
                            .then(function successCallback(response) {
                                $scope.listGoods = response.data;
                            }, function errorCallback(response) {
                                alert("Ошибки работы сервиса");
                            });
                        ////////////////////////////////////////////////////////////////////
                    }, function (errresponse) {
                        alert("Ошибки работы сервиса");
                    });

                    /////////////////////////////////////////////////////////////////
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
        }


        $scope.getListGroupGoods = function (groupname) {
            self.showAllGoods(groupname);

        };

        self.createGoods = function () {
            $scope.goods.goodsGroupName = angular.element(document.querySelector('#groupOfGoods')).html();
            var name = $scope.goods.goodsGroupName;
            $scope.goods.staticAssetStatusIdstaticAssetStatus = $scope.selectedStatus.idstaticAssetStatus;
            $scope.goods.$save(function () {
                    $http.get("/goodsAdminPage/addGoodsSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                        $scope.html = $sce.trustAsHtml(response.data);
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
            $scope.goods.goodsGroupName = angular.element(document.querySelector('#groupOfGoods')).html();
            var name = $scope.goods.goodsGroupName;
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

        $scope.submit = function () {
            if ($scope.goods.idAsset == null) {
                console.log('Saving New Goods', $scope.goods.idAsset);
                self.createGoods();
            } else {
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
            $scope.goods.goodsGroupName = angular.element(document.querySelector('#groupOfGoods')).html();
            var name = $scope.goods.goodsGroupName;
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
        $scope.application = new Application();
        var assetIdAsset;
        $scope.appList = [];
        $scope.makeApplication = function (idAsset, assetNameModel) {
            $http.get("/goodsAdminPage/formApplicationForGoods/add").then(function successCallback(response) {
                var formApplication = response.data;
                $scope.application.assetIdAsset = idAsset;
                //просим данные по пользователю
                $http
                ({
                    url: "/currentClient",
                    method: "GET",
                })
                    .then(function successCallback(response) {
                        var client = response.data;
                        //чистим и формируем заявку
                        angular.element(document.querySelector('.myPanel')).empty();
                        $scope.application.fio = client.name;
                        $scope.application.phone = client.phone;
                        $scope.html3 = $sce.trustAsHtml(formApplication);
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });

            }, function errorCallback(response) {
                alert("Ошибки работы сервиса");
            });
        };
        $scope.registrAppl = function () {
            $scope.application.date = convertDate(new Date());
            $scope.application.$save(function () {
                    angular.element(document.querySelector('.myPanel')).empty();
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    $scope.html = $sce.trustAsHtml("<div" + +"class='alert alert-success Myclose'" + +"ng-click='deleteAlert()'><button type='button' class='close'" + +"data-dismiss='lert'>X</button>" + +"Заявка успешно создана!" + +"</div>");
                },
                function (errorResult) {
                    angular.element(document.querySelector('.myPanel')).empty();
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    if (errorResult.status === 500)
                        $http.get("/goodsAdminPage/addApplicationErrorMessage").then(function successCallback(response) {
                            $scope.html = $sce.trustAsHtml(response.data);
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });
                    if (errorResult.status === 302) {
                        $http.get("/goodsAdminPage/applicationExistMessageWarning").then(function successCallback(response) {
                            $scope.html = $sce.trustAsHtml(response.data);
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                    }
                    if (errorResult.status === 409) {
                        $http.get("/goodsAdminPage/applicationAssetSelledMessageError").then(function successCallback(response) {
                            $scope.html = $sce.trustAsHtml(response.data);
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                    }
                }
            );
            self.reset();
        };
        $scope.showApplicationList = function () {
            $scope.appList = Application.query(function (success) {
                    if ($scope.appList.length === 0)
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
///////////////////////////////////////////////////////////////////////////Список картинок по товару
        $scope.assetImage = new AssetImage();
        $scope.listAssetImages = [];

        $scope.imageList = function (idAsset) {
            //просим форму для картинок
            $http
            ({
                url: "/goodsAdminPage/asset/form/image/" + idAsset,
                method: "GET",
            })
                .then(function successCallback(response) {
                    //запрос за формой выше
                    angular.element(document.querySelector('.dynamicBlockMessage')).empty();
                    angular.element(document.querySelector('.dynamicBlockMessage2')).empty();
                    $scope.html2 = $sce.trustAsHtml(response.data);
                    //но просим данные о пути к картинкам -  потом отображаем
                    //////////////////просим данные по товарам
                    $http
                    ({
                        url: "/goodsAdminPage/asset/path/to/image/" + idAsset,
                        method: "GET",
                    })
                        .then(function successCallback(response) {
                            $scope.listAssetImages = response.data;
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
        }
////////////////////////////////////удалить картинку
        $scope.deleteImage = function (image) {
            $http
            ({
                url: "/delete/asset/image/" + image.idassetImage,
                method: "GET",
            })
                .then(function successCallback(response) {
                    $scope.html = $sce.trustAsHtml(response.data);
                    $scope.imageList(image.assetIdAsset);
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
        };

        $scope.editApplicationForm = function (application) {
            $scope.application = angular.copy(application);
            angular.element(document.querySelector('.dynamic-html3')).empty();

            $http.get("/goodsAdminPage/formApplicationForGoods/edit").then(function successCallback(response) {
                $scope.html3 = $sce.trustAsHtml(response.data);
            }, function errorCallback(response) {
                alert("Ошибки работы сервиса");
            });
        };
        $scope.editApplication = function () {
            $scope.application.$update(function () {
                    $http.get("/application/updateSuccessMessage").then(function successCallback(response) {
                        angular.element(document.querySelector('.dynamic-html3')).empty();
                        $scope.html3 = $sce.trustAsHtml(response.data);
                        $scope.showApplicationList();
                    }, function errorCallback(response) {
                        alert("Ошибки работы сервиса");
                    });
                },
                function (errorResult) {
                    if (errorResult.status === 404)
                        $http.get("/application/updateErrorMessage").then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamic-html3')).empty();
                            $scope.html3 = $sce.trustAsHtml(response.data);
                            $scope.showApplicationList();
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                });
        };
        var tempApplication;
        var tempStatus;
        $scope.formSetPriceRealisation = function (application, status, assetIdAsset) {
            tempApplication = application;
            tempStatus = status;
            $http.get("/application/form/" + tempApplication.assetIdAsset)
                .then(function successCallback(response) {
                    angular.element(document.querySelector('.dynamic-html3')).empty();
                    $scope.html3 = $sce.trustAsHtml(response.data);
                }, function errorCallback(response) {
                    alert("Ошибки работы сервиса");
                });
        };
        $scope.changeApplicationStatusRelease = function (price) {
            $scope.changeApplicationStatus(tempApplication, tempStatus, price);
        };
        $scope.changeApplicationStatus = function (application, status, price) {
            application.status = status;
            if (status == 6)
                application.price = price;
            application.$update(function () {
                    $http.get("/application/statusUpdateSuccessMessage/" + status + "/" + application.idapplication)
                        .then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamic-html3')).empty();
                            $scope.html3 = $sce.trustAsHtml(response.data);
                            $scope.showApplicationList();
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });
                },
                function (errorResult) {
                    if (errorResult.status === 404)
                        $http.get("/application/statusUpdateErrorMessage/" + status).then(function successCallback(response) {
                            angular.element(document.querySelector('.dynamic-html3')).empty();
                            $scope.html3 = $sce.trustAsHtml(response.data);
                            $scope.showApplicationList();
                        }, function errorCallback(response) {
                            alert("Ошибки работы сервиса");
                        });

                });
        };
        $scope.listAssetsBy = function (groupname, filter) {
            $scope.goods = new Asset();
            var Users = $resource('/goodsAdminPage/listGoodsGroups/:filter/:groupname',
                {filter: '@filter', groupname: '@groupname'});
            $scope.listGoods = Users.query({filter: filter, groupname: groupname}, function (responce) {
                $scope.listGoods = responce;
            }, function (responce) {
                alert("Ошибки сервера!!");
            });
        };
        $scope.closeForm = function () {
            angular.element(document.querySelector('.dynamic-html3')).empty();
        };
        $scope.zoomImage = function (src) {
            angular.element(document.querySelector('#image-asset-zoom')).attr('src', '/resources/img/'+src);
            angular.element(document.querySelector('#asset-image-container')).css('opacity', '0');
            angular.element(document.querySelector('#image-asset-zoom')).css('visibility', 'visible');
            angular.element(document.querySelector('#image-asset-zoom')).css('z-index', '100');
            angular.element(document.querySelector('#image-asset-zoom')).css('position', 'fixed');
            angular.element(document.querySelector('#image-asset-zoom')).css('width', ($(document).width() / 1.5) + "px");
            angular.element(document.querySelector('#image-asset-zoom')).css('height', ($(document).height() / 1.5) + "px");
            angular.element(document.querySelector('#image-asset-zoom')).css('left', ((($(document).width() / 2) - ((($(document).width() / 1.5)) / 2)) + "px"));
            angular.element(document.querySelector('#image-asset-zoom')).css('top', ((($(document).width() / 4) - ((($(document).width() / 1.5)) / 4)) + "px"));
            angular.element(document.querySelector('#mask')).css('background-color', 'rgba(0,0,0,.5)');
            angular.element(document.querySelector('#mask')).css('height', '100%');
            angular.element(document.querySelector('#mask')).css('position', 'fixed');
            angular.element(document.querySelector('#mask')).css('width', '100%');
            angular.element(document.querySelector('#mask')).css('top', '0');
            angular.element(document.querySelector('#mask')).css('left', '0');
            angular.element(document.querySelector('#mask')).css('visibility', 'visible');
        };
        $scope.zoomOutImage = function () {

            angular.element(document.querySelector('#asset-image-container')).css('opacity', '100');
            angular.element(document.querySelector('#image-asset-zoom')).css('visibility', 'hidden');
            angular.element(document.querySelector('#mask')).css('visibility', 'hidden');
        };
    }]);


/////////////////////////////////////
function convertDate(date) {
    var yyyy = date.getFullYear().toString();
    var mm = (date.getMonth() + 1).toString();
    var dd = date.getDate().toString();

    var mmChars = mm.split('');
    var ddChars = dd.split('');

    return yyyy + '-' + (mmChars[1] ? mm : "0" + mmChars[0]) + '-' + (ddChars[1] ? dd : "0" + ddChars[0]);
}
////////////////////////////фабрика статических групп товаров из БД
app.factory('StaticGoodsGroup', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource('/staticGoodsGroup');
}]);
////////////////////////////фабрика статических статусов товаров из БД
app.factory('StaticAssetStatus', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource('/staticAssetStatus');
}]);
////////////////////////////фабрика картинки для товара
app.factory('AssetImage', ['$resource', function ($resource) {
    //$resource() function returns an object of resource class
    return $resource(
        '/assetImage/:idImage',
        {idImage: '@idImage'},
        {
            update: {
                method: 'PUT'
            }

        }
    );
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
    }
};
/////////////////////////////////////

