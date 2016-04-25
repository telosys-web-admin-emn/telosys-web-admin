'use strict';

/**
 * Factory for Sdf
 */
sdfModule.factory('Sdf', ['$http', 'restURL', function($http, restURL) {

	// REST Service URL to manage sdf
    var entityURL = restURL + '/sdf';
	
	/**
     * Validate sdf
     * @param sdf sdf
     * @throws validation exception
     */
	var validate = function (sdf) {
		var errors = [];
		if(errors.length > 0) {
			throw errors;
		}
    };
	
	return {
        /**
         * Get all sdfs as list items
         * @return all sdfs as list items
         */
    	getAllAsListItems: function() {
        	return $http.get(restURL + '/items/sdf');
    	},

        /**
         * Get all sdfs
         * @return all sdfs
         */
    	getAll: function() {
        	return $http.get(entityURL);
    	},

        /**
         * Get sdf
         * @return sdf
         */
    	get: function(no_primary_key_for_entity_Sdf) {
    	    var url = entityURL + '/' + no_primary_key_for_entity_Sdf;
        	return $http.get(url);
    	},

        /**
         * Create a new sdf
         * @param sdf sdf
         * @return sdf saved
         */
		create: function(sdf) {
			validate(sdf)
			var url = entityURL;
			return $http.post(url, sdf);
    	},

        /**
         * Update sdf
         * @param sdf sdf
         * @return sdf saved
         */
    	update: function(sdf) {
			validate(sdf)
			var url = entityURL + '/' + no_primary_key_for_entity_Sdf;
			return $http.put(url, sdf);
    	},

		/**
         * Delete sdf
         */
    	delete: function(no_primary_key_for_entity_Sdf) {
        	var url = entityURL + '/' + no_primary_key_for_entity_Sdf;
        	return $http.delete(url);
    	}
	};
	return $this;
}]);

