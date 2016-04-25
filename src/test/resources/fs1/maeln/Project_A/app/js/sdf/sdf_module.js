'use strict';

/* Module for Sdf */

var sdfModule = angular.module('sdf.module', ['myApp']);

/**
 * Module for sdf
 */
sdfModule.config(['$routeProvider', function($routeProvider) {
    // Pages routes
    $routeProvider.when('/sdf',    {templateUrl: 'partials/sdf/sdf_list.html', controller: 'SdfCtrl'});
    $routeProvider.when('/sdf/new', {templateUrl: 'partials/sdf/sdf_form.html', controller: 'SdfCtrl'});
    $routeProvider.when('/sdf/no_primary_key_for_entity_Sdf', {templateUrl: 'partials/sdf/sdf_form.html', controller: 'SdfCtrl'});
}]);
