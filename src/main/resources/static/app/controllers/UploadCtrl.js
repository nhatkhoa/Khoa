(function() {
    'use strict';

    var app = angular.module("ConceptMap");

    app.controller('UploadCtrl', function($scope, toaster, $modal,
	    $localStorage, assignService, $rootScope, $modalInstance, id) {
	
	
	// -- Lấy danh sách các concept và tài liệu hiện tại của nó
	assignService.listUpload(id).success(
		function(response) {
		    $scope.data = response;
		    $scope.select = response.concept[0];
		    toaster.pop("info", "Hướng dẫn",
			    "Nhấn vào tên concept và chọn tài liệu!");
		}).error(function() {
	    toaster.pop("error", "Mất kết nối", "Vui lòng thử lại sau!");
	    $modalInstance.close();
	});
	
	

	// --- Đóng popup
	$scope.cancel = function() {
	    $modalInstance.dismiss('cancel');
	};
	
	$scope.open = function(concept){
	    $scope.select = concept;
	}
	
	$scope.add = function(item){
	    if($scope.link === ""){
		toaster.pop("error", "Chú ý", "Cần nhập link đường dẫn trước!");
		return;
	    }
	    // --- Lấy id concept đang chọn
	    var id = $scope.select.id;
	    // --- Gọi service post đường dẫn
	    assignService.postUrl(id, $scope.link)
	    .success(function(response){
		// --- Thông báo
		toaster.pop("success", "Thành công", "Đã thêm : " + $scope.link);
		// --- Thêm vào danh sách docs của concept hiện tại
		$scope.select.docs.push(response);
		$scope.data.concept[$scope.data.concept.indexOf(item)] = $scope.select.docs.push(response);
		// --- Làm trống đường link
		 $scope.link = "";
	    })
	    
	}
    });

})();
