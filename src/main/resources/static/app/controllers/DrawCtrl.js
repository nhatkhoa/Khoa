(function() {
    "use strict";

    var app = angular.module("ConceptMap");

    app.controller('DrawCtrl', function($scope, $modalInstance, $http, $localStorage, toaster, cmapService, mode, id) {

        // ------------------- Thông tin cmap---------------------------------/
        $scope.title = "";
        $scope.info = "";
        $scope.mode = mode;
        //-- Mặc định người tạo là -1 (member hiện tại)
        $scope.author_id = -1;

        //------------------- Thông tin về bản vẽ------------------------------/
        //-- Dữ liệu bản vẽ
        $scope.model = new go.GraphLinksModel();

        // --- Nếu là chế độ xem và update
        if ($scope.mode == 0) {
            $scope.id = id;
            var success = function(response) {
                // --- Thêm vào danh sách cmap offline
                $scope.model = new go.GraphLinksModel(response.nodeDataArray, response.linkDataArray);
                $scope.title = response.title;
                $scope.info = response.info;
            };
            var error = function(err) {
                toaster.pop("error", "Thất Bại", "Tải dữ liệu thất bại. Lỗi kết nối!");
                $modalInstance.dismiss('cancel');
            };

            cmapService.get(id).success(success).error(error);
        };

        // --- Lệnh tạo mới concept map
        $scope.postNew = function() {
            if ($scope.title === "" || $scope.info === "") {
                toaster.pop('warning', "Chú ý", "Bạn cần nhập Tiêu đề và mô tả!");
                return;
            }

            var log = null;
            var temp = JSON.parse($scope.model.toJSON());
            var nodes = [];
            var links = [];

            angular.forEach(temp.nodeDataArray, function(value, key) {
                var temp = {};
                console.log(value.loc);
                temp.key = value.key;
                temp.text = value.text;
                temp.loc = value.loc;
                nodes.push(temp);
            });
            angular.forEach(temp.linkDataArray, function(value, key) {
                var temp = {};
                temp.from = value.from;
                temp.to = value.to;
                temp.text = value.text;
                links.push(temp);
            });

            var cmap = {};
            cmap.id = -1;
            cmap.title = $scope.title;
            cmap.info = $scope.info;
            cmap.author_id = -1;
            cmap.nodeDataArray = nodes;
            cmap.linkDataArray = links;

            cmapService.post(JSON.stringify(cmap))
                .success(function(response) {
                    toaster.pop("success", "Thành Công", "Tạo Concepts Map thành công!");
                    $modalInstance.close(response);
                })
                .error(function() {
                    toaster.pop("error", "Mất kết nối", "Không thể tạo concept map này !");
                });
        };

        // --- Lệnh update thay đổi concept map
        $scope.update = function() {
            var temp = JSON.parse($scope.model.toJSON());
            var nodes = [];
            var links = [];

            angular.forEach(temp.nodeDataArray, function(value, key) {
                var temp = {};
                temp.key = value.key;
                temp.text = value.text;
                nodes.push(temp);
            });
            angular.forEach(temp.linkDataArray, function(value, key) {
                var temp = {};
                temp.from = value.from;
                temp.to = value.to;
                temp.text = value.text;
                links.push(temp);
            });

            var cmap = {};
            cmap.id = $scope.id;
            cmap.title = $scope.title;
            cmap.info = $scope.info;
            cmap.author_id = $scope.$account.id;
            cmap.nodeDataArray = nodes;
            cmap.linkDataArray = links;

            cmapService.post(JSON.stringify(cmap)).success(function(response) {
                toaster.pop("success", "Thành Công", "Tạo Concepts Map thành công!");
                $modalInstance.close(response);
            });

        };

        $scope.ok = function() {
            $modalInstance.close($scope.selected.item);
        };

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };


    });

})();
