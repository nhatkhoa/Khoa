(function() {
    var app = angular.module("ConceptMap");

    var PostUserCtrl = function($scope, $http, $localStorage, assignService,
        $modalInstance, toaster, id) {

        // --- Lấy danh sách member chưa được gán
        assignService.members(id).success(function(response) {
            // --- Gán danh sách member bằng dữ liệu trả về khi gọi lệnh get
            $scope.members = response;

        }).error(function(err) {
            toaster.pop("error", "Lỗi kết nối", err);
            // --- Đóng popup
            $modalInstance.close();
        });

        // --- Gọi để gán bài tập cho member
        // --- Thực hiện lênh ajax lên server, nếu thành công thì xóa member khỏi danh sách
        $scope.post = function(item) {

            assignService.postUser(id, item.id).success(
                function() {
                    toaster.pop("success", "Hoàn Thành",
                        "Đã gán bài tập cho " + item.id);
                    // --- xóa member khỏi list
                    $scope.members.splice($scope.members.indexOf(item), 1);

                }).error(function(err) {
                toaster.pop("error", "Lỗi kết nối", err);
            });

        };


        $scope.cancel = function() {
        	// --- Đóng popup
            $modalInstance.close();
        };

    };

    app.controller('PostUserCtrl', PostUserCtrl);
})();
