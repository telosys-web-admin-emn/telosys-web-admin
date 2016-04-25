'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('sdf.module'));
  
  describe('SdfCtrl', function(){
    var SdfCtrl, Sdf,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
    beforeEach(inject(function($injector) {
    	$controller = $injector.get('$controller');
    	$q = $injector.get('$q');
    	$rootScope = $injector.get('$rootScope');
    	$scope = $rootScope.$new();
    	$routeParams = $injector.get('$routeParams');
    	$httpBackend = $injector.get('$httpBackend');
    	
    	// location is mocked due to redirection in browser : karma does not support it
    	$location = {
    		path: jasmine.createSpy("path").andCallFake(function() {
        	    return "";
        	})
    	};
    	
    	// Messages
    	MessageHandler = {
    		cleanMessage: jasmine.createSpy("cleanMessage"),
    		addSuccess: jasmine.createSpy("addSuccess"),
    		manageError: jasmine.createSpy("manageError"),
    		manageException: jasmine.createSpy("manageException"),
    	};

    	// Sdf service
    	Sdf = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'sdf1'});
    			return deferred.promise;
    		}
    	};
		
				SdfCtrl = $controller('SdfCtrl', {
    		'Sdf': Sdf,
			    		'$scope': $scope,
    		'$routeParams': $routeParams,
    		'$http': $httpBackend,
    		'$location': $location,
    		'MessageHandler': MessageHandler
    	});
    }));

    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
    	$httpBackend.verifyNoOutstandingRequest();
    });
    
    it('init', function() {
    	$rootScope.$apply();
    	expect($scope.mode).toBeNull();
    	expect($scope.sdf).toBeNull();
    	expect($scope.sdfs).toBe('sdf1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshSdfList', function() {
    	// given
    	Sdf.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'sdf2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshSdfList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.sdfs).toBe('sdf2');
    });
    
    it('refreshSdf', function() {
    	// given
    	Sdf.get = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'sdf'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshSdf();
    	$scope.$apply();
    	
    	// then
    	expect($scope.sdf).toBe('sdf');
    });
    
	it('goToSdfList', function() {
    	// given
    	spyOn($scope, "refreshSdfList");
    	
    	// when
    	$scope.goToSdfList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshSdfList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/sdf');
    });
    
    it('goToSdf', function() {
    	// given
    	spyOn($scope, "refreshSdf");
    	
    	// when
    	$scope.goToSdf();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshSdf).toHaveBeenCalledWith();
    	expect($location.path).toHaveBeenCalledWith('/sdf');
    });
    
    it('save : create', function() {
    	// given
    	$scope.sdf = {, name:'sdf'};
    	
    	$scope.mode = 'create';
    	Sdf.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'sdfSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.sdf).toBe('sdfSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.sdf = {, name:'sdf'};
    	
    	$scope.mode = 'update';
    	Sdf.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'sdfSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.sdf).toBe('sdfSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	Sdf.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToSdfList");
    	
    	// when
    	$scope.delete();
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToSdfList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : sdf create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/sdf/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.sdf).toBeNull();
    	expect($scope.sdfs).toBe('sdf1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});