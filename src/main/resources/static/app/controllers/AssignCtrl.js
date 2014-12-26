(function() {
    'use strict';

    var app = angular.module("ConceptMap");

    app.controller('AssignCtrl', function($scope, $http, $modal, $localStorage, toaster, accountService) {
        // --- Lấy danh sách bài tập trong cache
        $scope.assigns = $localStorage.data.assigns;

        // --- Khi nhận được thông báo cập nhật dữ liệu
        $scope.$on("data", function(response) {
            // --- Cập nhật lại danh sách bài tập
            $scope.assigns = response.assigns;
        });

        $scope.teacher = $localStorage.data.teacher;


        // --- Hàm mở popup tạo bài tập mới
        $scope.$postUser = function(id) {
            // --- Kiểm tra giảng viên
            if (!$localStorage.data.teacher) {
                toaster.pop("error", "Cảnh báo", "Bạn không có quyền truy cập!");
                return;
            };
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/postUser.html',
                controller: 'PostUserCtrl',
                size: 'sm',
                resolve: {
                    id: function() {
                        return id;
                    }
                }
            });
            // ---- Kết quả trả về từ popup khi đóng
            modalInstance.result.then(function(assign) {



            }, function() {});
        };


        // --- Hàm mở popup nộp bài tập
        $scope.$submit = function(id) {
           
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/submit.html',
                controller: 'SubmitCtrl',
                size: 'sm',
                resolve: {
                    id: function() {
                        return id;
                    }
                }
            });
            // ---- Kết quả trả về từ popup khi đóng
            modalInstance.result.then(function(assign) {



            }, function() {});
        };

        
        // --- Hiện bản vẽ FeedBack
        $scope.$feedBack = function(id) {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/feedback.html',
                controller: 'FeedBackCtrl',
                size: 'lg',
                resolve: {
                    // ---- id của cmap ( nếu id trống thì mặc định là -1)
                    id: function() {
                        if (id == undefined)
                            return -1;
                        return id;
                    }
                }
            });

            }, function() {});
        };

    });

})();