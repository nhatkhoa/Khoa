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
	
	$scope.add = function(){
	    if($scope.link.trim() === ''){
		toaster.pop("error", "Chú ý", "Cần nhập link đường dẫn trước!");
		return;
	    }
	    // --- Lấy index concept đang select
	    var index = $scope.data.concept.indexOf($scope.select);
	    // --- Lấy id concept đang chọn
	    var id = $scope.select.id;
	    // --- Gọi service post đường dẫn
	    assignService.postUrl(id, $scope.link)
	    .success(function(response){
		// --- Thông báo
		toaster.pop("success", "Đã thêm thành công", $scope.link);
		// --- Thêm vào danh sách docs của concept hiện tại
		$scope.select.docs.push(response);
		$scope.data.concept[index] = $scope.select;
		console.log($scope.data.concept[index]);
		// --- Làm trống đường link
		 $scope.link = "";
	    })
	    
	}
	
	$scope.upload = function(){
	    // --- Lấy index concept đang select
	    var index = $scope.data.concept.indexOf($scope.select);
	    // --- Lấy id concept đang chọn
	    var id = $scope.select.id;
	    // --- Gọi service post đường dẫn
	    assignService.upload(id, $scope.file)
	    .success(function(response){
		// --- Thông báo
		toaster.pop("success", "Đã thêm thành công", $scope.link);
		// --- Thêm vào danh sách docs của concept hiện tại
		$scope.select.docs.push(response);
		$scope.data.concept[index] = $scope.select;
		console.log($scope.data.concept[index]);
		// --- Làm trống đường link
		 $scope.link = "";
	    })
	    
	}
	
	$scope.del = function(item){
	    // --- Lấy index concept đang select
	    var index = $scope.data.concept.indexOf($scope.select);
	    
	    // --- Lấy index url nhấn 
	    var indexUrl = $scope.select.docs.indexOf(item);
	    
	    // --- Gọi service xóa
	    assignService.delUrl(item.id)
	    .success(function(){
		// --- Thông báo
		toaster.pop("success", "Xóa thành cồng", item.url);
		// --- Xóa khỏi danh sách hiện tại
		$scope.select.docs.splice(indexUrl,1);
		// --- Cập nhật vào data chính
		$scope.data.concept[index] = $scope.select;
		
		console.log($scope.data.concept[index]);
	    })
	    .error(function(){
		toaster.pop("error", "Mất kết nối", "Vui lòng thử lại sau!");
	    })
	    
	}
	
	
    });

})();
