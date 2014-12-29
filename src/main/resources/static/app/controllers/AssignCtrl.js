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

        $scope.student = function(a, b){
            if(a===0)
        	return 0;
            return a/b*100;
        }
        
        // --- Hàm mở popup tạo bài tập mới
        $scope.$postUser = function(item) {
            // --- Kiểm tra giảng viên
            if (!$localStorage.data.teacher) {
                toaster.pop("error", "Cảnh báo", "Bạn không có quyền truy cập!");
                return;
            };
            
            // --- Lấy index của assign
            var index = $scope.assigns.indexOf(item);
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/postUser.html',
                controller: 'PostUserCtrl',
                size: 'sm',
                resolve: {
                    id: function() {
                        return item.id;
                    }
                }
            });
            // ---- Kết quả trả về từ popup khi đóng
            modalInstance.result.then(function(count) {
        	// --- Cập nhật lại assign
        	item.users = item.users + count;
        	 $scope.assigns[index] = item;
        	$localStorage.data.assigns = $scope.assigns;
            }, function() {});
        };


        // --- Hàm mở popup nộp bài tập
        $scope.$submit = function(assign) {
           
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/submit.html',
                controller: 'SubmitCtrl',
                size: 'sm',
                resolve: {
                    id: function() {
                        return assign.id;
                    }
                }
            });
            // ---- Kết quả trả về từ popup khi đóng
            modalInstance.result.then(function(score) {
        	// --- Cập nhật điểm
        	$scope.assigns[$scope.assigns.indexOf(assign)].score = score;
        	
        	console.log($scope.assigns[$scope.assigns.indexOf(assign)].score);
        	// --- Cập nhật cache
        	$localStorage.data.assigns = $scope.assigns;
            }, function() {});
        };

        
        // --- Hiện bản vẽ FeedBack
        $scope.$feedBack = function(id) {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/feedback.html',
                controller: 'FeedBackCtrl',
                windowClass: 'modal-draw',
                resolve: {
                    // ---- id của cmap ( nếu id trống thì mặc định là -1)
                    id: function() {
                        if (id == undefined)
                            return -1;
                        return id;
                    }
                }
            

            }, function() {});
        };
        
     // --- Hiện popup upload tài liệu
        $scope.$upload = function(id) {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/upload.html',
                controller: 'UploadCtrl',
                size: 'lg',
                resolve: {
                    // ---- id của cmap ( nếu id trống thì mặc định là -1)
                    id: function() {
                        if (id == undefined)
                            return -1;
                        return id;
                    }
                }
            

            }, function() {});
        };

    });

})();
