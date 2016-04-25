'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('en.module'));
  
  describe('EnCtrl', function(){
    var EnCtrl, En,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
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

    	// En service
    	En = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'en1'});
    			return deferred.promise;
    		}
    	};
		
				EnCtrl = $controller('EnCtrl', {
    		'En': En,
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
    	expect($scope.en).toBeNull();
    	expect($scope.ens).toBe('en1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshEnList', function() {
    	// given
    	En.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'en2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshEnList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.ens).toBe('en2');
    });
    
    it('refreshEn', function() {
    	// given
    	En.get = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'en'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshEn();
    	$scope.$apply();
    	
    	// then
    	expect($scope.en).toBe('en');
    });
    
	it('goToEnList', function() {
    	// given
    	spyOn($scope, "refreshEnList");
    	
    	// when
    	$scope.goToEnList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshEnList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/en');
    });
    
    it('goToEn', function() {
    	// given
    	spyOn($scope, "refreshEn");
    	
    	// when
    	$scope.goToEn();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshEn).toHaveBeenCalledWith();
    	expect($location.path).toHaveBeenCalledWith('/en');
    });
    
    it('save : create', function() {
    	// given
    	$scope.en = {, name:'en'};
    	
    	$scope.mode = 'create';
    	En.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'enSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.en).toBe('enSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.en = {, name:'en'};
    	
    	$scope.mode = 'update';
    	En.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'enSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.en).toBe('enSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	En.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToEnList");
    	
    	// when
    	$scope.delete();
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToEnList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : en create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/en/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.en).toBeNull();
    	expect($scope.ens).toBe('en1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});