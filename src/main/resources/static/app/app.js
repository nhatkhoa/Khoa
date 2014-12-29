$show = {};
(function() {
    "use strict";
    var app = angular.module('ConceptMap', ["angularFileUpload", "ui.router", "ui.bootstrap", "ngStorage", "toaster", 'angular.filter', 'chieffancypants.loadingBar']);
    // --- Configuration
    app.config(function($stateProvider, $urlRouterProvider, cfpLoadingBarProvider) {

        cfpLoadingBarProvider.includeSpinner = true;
        // --- Đường dẫn mặc định /#/cmap
        //$urlRouterProvider.otherwise("/cmap");

        $stateProvider.state('cmap', {
            url: "/cmap",
            templateUrl: "app/partials/cmap_list.html",
            controller: "CMapCtrl"
        })
        .state('complete', {
            url: "/complete",
            onEnter: function($stateParams, $state, $modal, $localStorage, $rootScope, toaster, accountService) {
                $modal.open({
                    templateUrl: "app/partials/complete.html",
                    controller: function($scope) {
                        $scope.teacher = $localStorage.data.teacher ? 1 : 0; 
                        $scope.email = $localStorage.data.email; 
                        $scope.update = function() {

                            if ($scope.email === "") {
                                toaster.pop('warning', "Chú ý", "Bạn cần nhập chính xác Email");
                                return;
                            }
                            accountService.postEmail($scope.email)
                                .success(function() {
                                    toaster.pop("success", "Hoàn Thành", "Cập nhật email thành công !");
                                    $rootScope.$account.email = $scope.email;
                                });
                            accountService.postTeacher($scope.teacher)
                                .success(function() {
                                    toaster.pop("success", "Hoàn Thành", "Cập nhật loại tài khoản thành công !");
                                    $rootScope.$account.teacher = ($scope.teacher === 1) ? true : false;
                                });
                            window.location.assign("../../");
                            $scope.$dismiss();
                        }

                        $scope.ok = function() {
                            $scope.$dismiss();
                        };
                    }
                });

            }
        }).state('assign', {
            url: "/assign",
            templateUrl: "app/partials/assign_list.html",
            controller: "AssignCtrl"
        });
        }).run(
        ['$rootScope', '$state', '$stateParams','$localStorage',
            function($rootScope, $state, $stateParams, $localStorage) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                console.log("Web site bắt đầu chạy : ", Date.now());

            }
        ]);


})();
