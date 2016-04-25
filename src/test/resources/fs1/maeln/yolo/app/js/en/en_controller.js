'use strict';

/**
 * Controller for En
 **/
enModule.controller('EnCtrl', ['En',  '$scope', '$routeParams', '$http', '$location', '$cookies', 'MessageHandler', 'restURL', function(En, $scope, $routeParams, $http, $location, $cookies, MessageHandler, restURL) {
	    // edition mode
    $scope.mode = null;
    
	// list of ens
    $scope.ens = [];
	// en to edit
    $scope.en = null;

	// referencies entities
	$scope.items = {};

    /**
     * Load all referencies entities
     */
	$scope.loadAllReferencies = function() {
    };
    
    /**
     * Refresh ens list
     */
    $scope.refreshEnList = function() {
    	try {
			$scope.ens = [];
        	En.getAll().then(
				function(success) {
        	        $scope.ens = success.data;
            	}, 
	            MessageHandler.manageError);
    	} catch(ex) {
    		MessageHandler.manageException(ex);
    	}
    }
    /**
     * Refresh en
     */
    $scope.refreshEn = function(no_primary_key_for_entity_En) {
    	try {
        	$scope.en = null;
	        En.get(no_primary_key_for_entity_En).then(
				function(success) {
        	        $scope.en = success.data;
            	}, 
	            MessageHandler.manageError);
    	  } catch(ex) {
        	MessageHandler.manageException(ex);
    	}
    }

    /**
     * Go to the ens list page
     */
    $scope.goToEnList = function() {
        $scope.refreshEnList();
        $location.path('/en');
    }
    /**
     * Go to the en edit page
     */
    $scope.goToEn = function(no_primary_key_for_entity_En) {
        $scope.refreshEn(no_primary_key_for_entity_En);
        $location.path('/en/'+no_primary_key_for_entity_En);
    }

    // Actions

    /**
     * Save en
     */
    $scope.save = function() {
    	try {
			MessageHandler.cleanMessage();
			var save;
			if( $scope.mode === 'create' ) {
        		save = En.create;
			} else {
				save = En.update;
			}
			save($scope.en).then(
    	        function(success) {
	                MessageHandler.addSuccess('save ok');
                	$scope.en = success.data;
            	},
        	    MessageHandler.manageError);
    	} catch(ex) {
        	MessageHandler.manageException(ex);
    	}
    };
    /**
     * Delete en
     */
    $scope.delete = function(no_primary_key_for_entity_En) {
	    try {
			MessageHandler.cleanMessage();
    	    En.delete(no_primary_key_for_entity_En).then(
				function(success) {
                	$scope.goToEnList();
            	}, 
                MessageHandler.manageError);
        } catch(ex) {
            MessageHandler.manageException(ex);
        }
    };
    
    // Main
	MessageHandler.cleanMessage();
    if( $location.path().endsWith('/new') ) {
        // Creation page
        $scope.en = {};
        $scope.mode = 'create';
		$scope.loadAllReferencies();
        $scope.bookorderitem = null;
    } else if( no_primary_key_for_entity_En ) {
        // Edit page
		$scope.loadAllReferencies();
		$scope.refreshEn(no_primary_key_for_entity_En);
    } else {
        // List page
        $scope.refreshEnList();
    }
    
    
}]);
