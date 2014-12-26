(function() {
    'use strict';

    var app = angular.module("ConceptMap");

    app.controller('HomeCtrl', function($scope, $http, $localStorage, $rootScope, $modal, toaster, accountService) {
	// --- Hàm xóa trắng dữ liệu
	var emptyData = function() {
            $rootScope.$account = null;
            // --- Xóa dữ liệu lưu trữ cache
            $localStorage.$reset();
        };

        $scope.$state = $rootScope.$state
        //--- Thiết lập hàm tìm kiếm khái niệm
        $scope.key = "";
        // --- Lắng nghe biến key : nếu key thay đổi thì thông báo "search" và trả ra dữ key mới
        $scope.$watch("key", function(newVal) {
            $rootScope.$broadcast('search', newVal);
        });
        // --- Sử dụng cache phòng trường hợp không kết nối được
        $scope.$account = $localStorage.data;
        $rootScope.$broadcast('data', $scope.$account);
        
        // --- Khi lấy dữ liệu thành công
        var success = function(response) {
            toaster.pop('success', "Thông báo", "Thiết lập kết nối dữ liệu thành công!");
            // --- Xóa trống những dữ liệu trước
            emptyData();
            // --- Khởi tạo $account chứa toàn bộ thông tin tài khoản
            $scope.$account = response;
            // --- Lưu trữ cache dữ liệu offline
            $localStorage.data = response;
            // --- Thông báo và chuyển danh sách cmap cho CMapCtrl
            $rootScope.$broadcast('data', response);
            // --- Chuyển link về /#/cmap (Trang chính)
            $scope.$state.go("cmap");

        };
        // --- Khi quá trình lấy dữ liệu thất bại
        var error = function() {
            
            toaster.pop('success', "Thông báo", "Kết nối dữ liệu thất bại, vui lòng kiểm tra kết nối!");
        };
        // --- Gọi service lấy thông tin và dữ liệu chung từ server
        accountService.get().success(success).error(error);

        $scope.logout = function(){
            // --- Xóa dữ liệu cache
            emptyData();
            accountService.logout();
        }

        // --- Hàm thêm bài tập mới dành cho giảng viên

        // --- Hàm mở popup tạo bài tập mới
        $scope.$assignNew = function() {
            // --- Kiểm tra giảng viên
            if(!$scope.$account.teacher)
        	{
        	toaster.pop("error","Cảnh báo", "Bạn không có quyền truy cập!");
        	return;
        	};
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/assign_new.html',
                controller: 'AssignCreateCtrl',
                size: 'sm'
            });
            // ---- Kết quả trả về từ bản vẽ
            modalInstance.result.then(function(assign) {
                // --- Thêm concept map mới vào list
                $scope.$account.assigns.push(assign);
                // ---- cập nhật lại danh sách concept map
                $localStorage.data.assigns = $scope.$account.assigns;
                
                // --- Định hướng sang assign (trang bài tập)
                $scope.$state.go("assign");
                
                // --- Thông báo cập nhật data cho assign controller
                $rootScope.$broadcast('data', $scope.$account);

            }, function() {});
        };
        

    });

})();
