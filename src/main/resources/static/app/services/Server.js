(function() {
    "use strict";

    var app = angular.module("ConceptMap");
    // ---- Get thông tin tài khoản và danh sách concept map
    app.factory('accountService', function($http) {
	var loginHttp = function() {
	    return $http.get("/data/login");
	};

	var accountHttp = function() {
	    return $http.get("/data/account");
	};

	var logoutHttp = function() {
	    return $http.get("/data/logout");
	};

	var updateHttp = function(data) {
	    return $http.post("/data/account", data);
	};

	return {
	    get : accountHttp,
	    login : loginHttp,
	    logout : logoutHttp,
	    postEmail : updateHttp,
	};
    });

    // ---- Get, Post, Delele, Update Concept Map
    app.factory('cmapService', function($http) {
	var getHttp = function(id) {
	    return $http.get("data/cmaps/" + id);
	};

	var postHttp = function(data) {
	    return $http.post("data/cmaps", data);
	};

	var deleteHttp = function(id) {
	    return $http.get("data/cmaps/" + id + "/delete");
	};

	var shareHttp = function(id, list) {
	    return $http.post("data/cmaps/" + id + "/share", list);
	};

	var unshareHttp = function(id, user_id) {
	    return $http.get("data/cmaps/" + id + "/unshare/" + user_id);
	};

	var shareListHttp = function(id) {
	    return $http.get("data/cmaps/" + id + "/members");
	};

	return {
	    get : getHttp,
	    post : postHttp,
	    del : deleteHttp,
	    share : shareHttp,
	    unshare : unshareHttp,
	    shareList : shareListHttp
	};
    });

    // ---- User Service
    app.factory('userService', function($http) {
	// --- Lấy user theo id
	var getHttp = function(id) {
	    return $http.get("data/users/" + id);
	};
	// --- TÌm kiếm member theo tên
	var findHttp = function(user_name) {
	    return $http.post("data/users/find", user_name);
	};
	// --- Hàm lấy danh sách tên tất cả thành viên cho autocomplete
	var listHttp = function() {
	    return $http.get("data/users/list");
	};

	return {
	    get : getHttp,
	    find : findHttp,
	    list : listHttp,
	};
    });

    // ---- Assign Service
    app.factory('assignService', function($http, $upload) {
	// --- Lấy assign theo id
	var getHttp = function(id) {
	    return $http.get("data/assigns/" + id);
	};

	// --- Tạo assign mới
	var createHttp = function(assign) {
	    return $http.post("data/assigns", assign);
	};

	// --- Nộp bài tập lên hệ thống
	var submitHttp = function(id, cmap_id) {
	    return $http.get("data/assigns/submit/" + id + "/" + cmap_id);
	};

	// --- Nộp bài tập lên hệ thống
	var getMemHttp = function(id) {
	    return $http.get("data/assigns/" + id + "/members");
	};

	// --- Nộp bài tập lên hệ thống
	var postUserHttp = function(id, mem_id) {
	    return $http.get("data/assigns/" + id + "/" + mem_id);
	};

	// --- Lấy feedback
	var feedHttp = function(id) {
	    return $http.get("data/assigns/" + id + "/feedback");
	};

	// --- Lấy ListUpload Up tài liệu
	var listUploadHttp = function(id) {
	    return $http.get("data/assigns/" + id + "/uploads");
	};

	// --- Post url cho tài liệu
	var postUrlHttp = function(id, url) {
	    return $http.post("data/assigns/" + id + "/posturl", url);
	};

	// --- Post url cho tài liệu
	var uploadHttp = function(id, file) {
	    var fd = new FormData();
	    fd.append('file', file);
	    
	    return $http.post("docs/" + id, fd, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': "multipart/form-data; boundary='data'"}
	        });
	};

	// --- Post url cho tài liệu
	var delUrlHttp = function(id) {
	    return $http.post("data/assigns/docs/" + id + "/delete");
	};

	return {
	    get : getHttp,
	    submit : submitHttp,
	    create : createHttp,
	    members : getMemHttp,
	    postUser : postUserHttp,
	    feedBack : feedHttp,
	    listUpload : listUploadHttp,
	    postUrl : postUrlHttp,
	    upload : uploadHttp,
	    delUrl : delUrlHttp
	};
    });
})();
