'use strict';

/**
 * Controller for Sdf
 **/
sdfModule.controller('SdfCtrl', ['Sdf',  '$scope', '$routeParams', '$http', '$location', '$cookies', 'MessageHandler', 'restURL', function(Sdf, $scope, $routeParams, $http, $location, $cookies, MessageHandler, restURL) {
	    // edition mode
    $scope.mode = null;
    
	// list of sdfs
    $scope.sdfs = [];
	// sdf to edit
    $scope.sdf = null;

	// referencies entities
	$scope.items = {};

    /**
     * Load all referencies entities
     */
	$scope.loadAllReferencies = function() {
    };
    
    /**
     * Refresh sdfs list
     */
    $scope.refreshSdfList = function() {
    	try {
			$scope.sdfs = [];
        	Sdf.getAll().then(
				function(success) {
        	        $scope.sdfs = success.data;
            	}, 
	            MessageHandler.manageError);
    	} catch(ex) {
    		MessageHandler.manageException(ex);
    	}
    }
    /**
     * Refresh sdf
     */
    $scope.refreshSdf = function(no_primary_key_for_entity_Sdf) {
    	try {
        	$scope.sdf = null;
	        Sdf.get(no_primary_key_for_entity_Sdf).then(
				function(success) {
        	        $scope.sdf = success.data;
            	}, 
	            MessageHandler.manageError);
    	  } catch(ex) {
        	MessageHandler.manageException(ex);
    	}
    }

    /**
     * Go to the sdfs list page
     */
    $scope.goToSdfList = function() {
        $scope.refreshSdfList();
        $location.path('/sdf');
    }
    /**
     * Go to the sdf edit page
     */
    $scope.goToSdf = function(no_primary_key_for_entity_Sdf) {
        $scope.refreshSdf(no_primary_key_for_entity_Sdf);
        $location.path('/sdf/'+no_primary_key_for_entity_Sdf);
    }

    // Actions

    /**
     * Save sdf
     */
    $scope.save = function() {
    	try {
			MessageHandler.cleanMessage();
			var save;
			if( $scope.mode === 'create' ) {
        		save = Sdf.create;
			} else {
				save = Sdf.update;
			}
			save($scope.sdf).then(
    	        function(success) {
	                MessageHandler.addSuccess('save ok');
                	$scope.sdf = success.data;
            	},
        	    MessageHandler.manageError);
    	} catch(ex) {
        	MessageHandler.manageException(ex);
    	}
    };
    /**
     * Delete sdf
     */
    $scope.delete = function(no_primary_key_for_entity_Sdf) {
	    try {
			MessageHandler.cleanMessage();
    	    Sdf.delete(no_primary_key_for_entity_Sdf).then(
				function(success) {
                	$scope.goToSdfList();
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
        $scope.sdf = {};
        $scope.mode = 'create';
		$scope.loadAllReferencies();
        $scope.bookorderitem = null;
    } else if( no_primary_key_for_entity_Sdf ) {
        // Edit page
		$scope.loadAllReferencies();
		$scope.refreshSdf(no_primary_key_for_entity_Sdf);
    } else {
        // List page
        $scope.refreshSdfList();
    }
    
    
}]);
