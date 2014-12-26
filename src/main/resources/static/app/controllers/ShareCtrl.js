(function($) {
    var app = angular.module("ConceptMap");

    var ShareCtrl = function($scope, $http, $localStorage, cmapService,
	    $modalInstance, toaster, id) {

	// --- Lấy danh sách member đã share và chưa share
	cmapService.shareList(id).success(function(response) {
	    $scope.members = response.unshare;
	    $scope.shareLists = response.share;
	}).error(function(err) {
	    toaster.pop("error", "Lỗi kết nối", err);
	    $modalInstance.close();
	});

	$scope.share = function(index) {
	    $scope.shareLists.push($scope.members[index]);
	    $scope.members.splice(index, 1);
	};
	$scope.unshare = function(index) {
	    var item = $scope.shareLists[index];
	    cmapService.unshare(id, item.id).success(
		    function() {
			toaster.pop("success", "Hoàn Thành",
				"Đã hủy chia sẽ với " + item.name);
			$scope.members.push($scope.shareLists[index]);
			$scope.shareLists.splice(index, 1);
		    }).error(function(err) {
		toaster.pop("error", "Lỗi kết nối", err);
	    });

	};

	$scope.ok = function() {
	    var list = [];
	    angular.forEach($scope.shareLists, function(val) {
		list.push(val.id);

	    });
	    cmapService.share(id, list).success(function() {
		toaster.pop("success", "Thành công", "Chia sẽ thành công");
	    }).error(
		    function() {
			toaster.pop("error", "Thất Bại",
				"Quá trình chia sẽ thất bại.");
		    });
	    $modalInstance.close();
	};

	$scope.cancel = function() {
	    $modalInstance.dismiss('cancel');
	};

    };

    app.controller('ShareCtrl', ShareCtrl);
})($);
