controllers.controller("LoginPageController", ['$rootScope', '$scope', '$http', '$location', '$sessionStorage', function ($rootScope, $scope, $http, $location, $sessionStorage) {

    var authenticate = function (credentials, callback) {
        var headers = credentials ? {
        	//btoa 将字符串进行Base64编码
            authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/user', {headers: headers}).then(function (response) {
            if (response.data.name) {
                $sessionStorage.principal = response.data;
                $sessionStorage.authenticated = true;
            } else {
                $sessionStorage.authenticated = false;
            }
            callback && callback();
        }, function () {
            $rootScope.authenticated = false;
            callback && callback();
        });
    };

    authenticate();
    $scope.credentials = {};

    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($sessionStorage.authenticated) {
                $location.path("/");
                $scope.error = false;
                $rootScope.$emit("reloadNavigation", {});
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };

}]);