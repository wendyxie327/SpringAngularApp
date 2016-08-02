controllers.controller("NavigationController", ['$rootScope', '$scope', '$http', '$location', '$sessionStorage', function ($rootScope, $scope, $http, $location, $sessionStorage) {

    $scope.$storage = $sessionStorage;
    $scope.model = {};

    var reloadNavigation = function(){
        $http.get("/ws/navigation/model").success(function(data) {
            $scope.model = data;	//返回内容为NavigationModelResponse
        })
    };

    /*
     * scope是angularJS中的作用域(其实就是存储数据的地方)，scope是html和单个controller之间的桥梁，数据绑定就靠他了
     * 搜索的时候，优先找自己的scope，如果没有找到就沿着作用域链向上搜索，直至到达根作用域rootScope。
     * rootscope是各个controller中scope的桥梁。用rootscope定义的值，可以在各个controller中使用			*/
    $rootScope.$on("reloadNavigation", function(){
        reloadNavigation();
    });

    reloadNavigation();

    /*
     * $emit只能向parent controller传递event与data 
     * $broadcast只能向child controller传递event与data 
     * $on用于接收event与data	*/
    $scope.logout = function () {
        $http.post('logout', {}).success(function () {
            $sessionStorage.authenticated = false;
            $sessionStorage.principal = undefined;
            $rootScope.$emit("reloadNavigation", {});
            $location.path("/");
        }).error(function (data) {
            $sessionStorage.principal = undefined;
            $sessionStorage.authenticated = false;
            $rootScope.$emit("reloadNavigation", {});
            $location.path("/");
        });
    }

}]);