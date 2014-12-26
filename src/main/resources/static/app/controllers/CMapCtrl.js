(function() {
    'use strict';

    var app = angular.module("ConceptMap");

    app.controller('CMapCtrl', function($scope, toaster, $modal, $localStorage, cmapService, $rootScope) {
        // --- Bộ lọc từ khóa (danh sách hiển thị theo bộ lọc)
	$scope.$filter = "";
	// --- Lắng nghe : khi có thông báo "search" thì nhận key và set $filter theo key
        $scope.$on('search', function(event, arg) {
             $scope.filter(arg);
        });
        
        // --- Hàm thay đổi filter (dành cho button lọc theo tên member)
        $scope.filter = function(key) {
                $scope.$filter = key;
        }
        // --- Sử dụng dữ liệu từ cache
        $scope.cmaps = $localStorage.data.cmaps;
        
        // --- Mỗi khi HomeCtrl load lại dữ liệu mới (data là CMapDetail)
        $scope.$on('data', function(event, data) {
            $scope.cmaps = data.cmaps;
        });
        
        
            
        // --- Hàm mở bảng vẽ concept map
        $scope.$draw = function(mode, id) {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/draw.html',
                controller: 'DrawCtrl',
                size: 'lg',
                resolve: {
                    // ---- id của cmap ( nếu id trống thì mặc định là -1)
                    id: function() {
                        if (id == undefined)
                            return -1;
                        return id;
                    },
                    mode: function() {
                        // ---- Chế độ vẽ là Xem/Sửa/Tạo mới
                        if (mode == undefined)
                            return 0;
                        return mode;
                    }
                }
            });
            // ---- Kết quả trả về từ bản vẽ
            modalInstance.result.then(function(cmap) {
                // --- Thêm concept map mới vào list
                $scope.cmaps.push(cmap);
                // ---- cập nhật lại danh sách concept map
                $localStorage.data.cmaps = $scope.cmaps;
                $scope.$state.go("cmap");

            }, function() {});
        };
        // --- Đặt $show là biến Global sử dụng mọi nơi
        $show = $scope.$draw;
        
        
        // --- Hàm mở modal share
        $scope.$share = function(id) {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/share.html',
                controller: 'ShareCtrl',
                size: 'sm',
                resolve: {
                    id: function() {
                        return id;
                    }
                }
            });

        };
        
        
        // --- Hàm trả về index của một cmap trong list cmaps theo id
        var findIndex = function(id) {
            angular.forEach($scope.cmaps, function(val) {
                if (val.id === id)
                    return val;
                return null;
            });
        }
        // --- Hàm xóa cmaps
        $scope.$delete = function(item) {
            // --- Gọi dịch vụ xóa cmap
            cmapService.del(item.id)
                .success(function(data) {
                    toaster.pop("warring", "Thông Báo", "Đã xóa Concept Map " + item.title);
                    // --- Xóa cmap tren khỏi list
                    $scope.cmaps.splice($scope.cmaps.indexOf(item), 1);
                    // ---- cập nhật lại danh sách concept map
                    $localStorage.data.cmaps = $scope.cmaps;
                })
                .error(function() {
                    toaster.pop("error", "Thất Bại","Cmap " + item.title + " đang được chia sẽ !");
                });


        }

    });


})();
