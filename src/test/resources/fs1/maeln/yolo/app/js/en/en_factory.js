'use strict';

/**
 * Factory for En
 */
enModule.factory('En', ['$http', 'restURL', function($http, restURL) {

	// REST Service URL to manage en
    var entityURL = restURL + '/en';
	
	/**
     * Validate en
     * @param en en
     * @throws validation exception
     */
	var validate = function (en) {
		var errors = [];
		if(errors.length > 0) {
			throw errors;
		}
    };
	
	return {
        /**
         * Get all ens as list items
         * @return all ens as list items
         */
    	getAllAsListItems: function() {
        	return $http.get(restURL + '/items/en');
    	},

        /**
         * Get all ens
         * @return all ens
         */
    	getAll: function() {
        	return $http.get(entityURL);
    	},

        /**
         * Get en
         * @return en
         */
    	get: function(no_primary_key_for_entity_En) {
    	    var url = entityURL + '/' + no_primary_key_for_entity_En;
        	return $http.get(url);
    	},

        /**
         * Create a new en
         * @param en en
         * @return en saved
         */
		create: function(en) {
			validate(en)
			var url = entityURL;
			return $http.post(url, en);
    	},

        /**
         * Update en
         * @param en en
         * @return en saved
         */
    	update: function(en) {
			validate(en)
			var url = entityURL + '/' + no_primary_key_for_entity_En;
			return $http.put(url, en);
    	},

		/**
         * Delete en
         */
    	delete: function(no_primary_key_for_entity_En) {
        	var url = entityURL + '/' + no_primary_key_for_entity_En;
        	return $http.delete(url);
    	}
	};
	return $this;
}]);

