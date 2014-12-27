(function() {
    'use strict';

    var app = angular.module("ConceptMap");

    app.controller('AssignCreateCtrl', function($scope, toaster, $modal,
	    $localStorage, assignService, $rootScope, $modalInstance) {

	$scope.cmaps = $localStorage.data.cmaps;
	$scope.author = $localStorage.data.id;
	$scope.cmap = $scope.cmaps[0];

	$scope.clear = function() {
	    $scope.dt = null;
	};

	$scope.open = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();

	    $scope.opened = true;
	};

	$scope.dateOptions = {
	    formatYear : 'yy',
	    startingDay : 1
	};

	$scope.format = 'dd/MM/yyyy';

	// --- Hàm đóng popup
	$scope.cancel = function() {
	    $modalInstance.dismiss('cancel');
	};

	$scope.post = function() {
	    var temp = {};
	    temp.cmap_id = $scope.cmap;
	    temp.topic = $scope.topic;
	    temp.info = $scope.info;
	    temp.deadline = $scope.deadline;

	    console.log(temp);
	    assignService.create(temp).success(
		    function(response) {
			toaster.pop("success", "Thành Công",
				"Thêm bài tập mới thành công!");
			$modalInstance.close(response);
		    }).error(function() {
		toaster.pop("error", "Mất kết nối", "Vui lòng thử lại sau!");
	    })
	}
    });

})();
