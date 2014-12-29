(function() {
    "use strict";

    var app = angular.module("ConceptMap");

    app.controller('FeedBackCtrl', function($scope, $modalInstance, $http, toaster, assignService, id, $location) {

        //------------------- Thông tin về bản vẽ------------------------------/
        //-- Dữ liệu bản vẽ
        $scope.model = new go.GraphLinksModel();

        var success = function(response) {
            // --- Thêm vào danh sách cmap offline
            $scope.model = new go.GraphLinksModel(response.nodeDataArray, response.linkDataArray);
            $scope.feed = response;
            // --- Hướng dẫn chú thích
            toaster.pop("error", "MÀU ĐỎ", "Những khái niệm và quan hệ sai.");
            toaster.pop("success", "MÀU XANH", "Những khái niệm và quan hệ đúng.");
        };
        var error = function(err) {
            toaster.pop("error", "Thất Bại", "Tải dữ liệu thất bại. Lỗi kết nối!");
            $modalInstance.dismiss('cancel');
        };
        // --- Gọi service ajax lấy feedback
        assignService.feedBack(id).success(success).error(error);

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

      

    });

})();
