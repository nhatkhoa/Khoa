(function() {
    'use strict';

    var app = angular.module("ConceptMap");

    app.controller('SubmitCtrl', function($scope, toaster, $modal,
        $localStorage, assignService, $rootScope, $modalInstance, id) 
    {
       // --- Lấy id của người dùng dùng để lọc cmap
        $scope.author = $localStorage.data.id;
         // --- Lấy danh sách cmap của người dùng hiện tại
        $scope.cmaps = $localStorage.data.cmaps;

        // --- Mặc định sẽ lấy cmap đầu tiên
        $scope.cmap = $scope.cmaps[0];

        //--- Hàm nợp bài tập
        $scope.submit = function() {
            // --- Gọi submit với id : assign id, cmap là id cmap được chọn
            assignService.submit(id, $scope.cmap).success(
                function(response) {
                    // --- Lấy điểm 
                    $scope.score = response;
                    // --- Trả về điểm cho assignCtrl
                    $modalInstance.close($scope.score);

                }).error(function() {
                toaster.pop("error", "Mất kết nối", "Vui lòng thử lại sau!");

            });
        }
        

        
    });

})();
