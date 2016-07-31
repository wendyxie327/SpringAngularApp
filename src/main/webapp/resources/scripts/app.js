var app = angular.module('myApp',	////定义一个ng-app  
    [
        'ui.bootstrap',
        'services',
        'controllers',
        'pascalprecht.translate',
        'ngCookies',
        'ui.router',
        'ngRoute',
        'ngStorage',
        'angularjs-dropdown-multiselect'
    ]
);

app.config(function ($translateProvider) {	//设置路由，负责把url指向不同的方法
    $translateProvider.useUrlLoader('/ws/messageBundle/properties');
    $translateProvider.useCookieStorage();
    $translateProvider.preferredLanguage('en');	//默认选择语言
    $translateProvider.fallbackLanguage('en');
});

app.directive('fieldValidationErrorPlaceHolder', function () {
    return {
        restrict: 'AE',
        templateUrl: '/resources/directives/fieldValidationErrorPlaceHolder.html',
        replace: true,
        scope: {
            'error': '=error'
        }
    };
});

var services = angular.module('services', ['ngResource']);
var controllers = angular.module('controllers', ['ngResource']);
