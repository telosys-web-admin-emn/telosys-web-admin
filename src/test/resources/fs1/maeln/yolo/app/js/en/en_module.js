'use strict';

/* Module for En */

var enModule = angular.module('en.module', ['myApp']);

/**
 * Module for en
 */
enModule.config(['$routeProvider', function($routeProvider) {
    // Pages routes
    $routeProvider.when('/en',    {templateUrl: 'partials/en/en_list.html', controller: 'EnCtrl'});
    $routeProvider.when('/en/new', {templateUrl: 'partials/en/en_form.html', controller: 'EnCtrl'});
    $routeProvider.when('/en/no_primary_key_for_entity_En', {templateUrl: 'partials/en/en_form.html', controller: 'EnCtrl'});
}]);
