/*
 * JUnit test case for Entity1 caching service
 * Created on 4 févr. 2016 ( Date ISO 2016-02-04 - Time 13:41:24 )
 * Generated by Telosys Tools Generator ( version 3.0.0 )
 */

package org.demo.test.cache;


import org.demo.bean.Entity1 ;
import org.demo.cache.Entity1Cache ;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for Entity1 caching service
 * 
 * @author Telosys Tools Generator
 *
 */
public class Entity1CacheTest 
{
	protected static final java.util.Date now = new java.util.Date();

	private final static void populate(Entity1 entity1) {
		entity1.setMyfield( "A" ) ;
	}

	@Test
	public void testPutGetRemove() {
		
		System.out.println("Testing class Entity1Cache ..." );
		
		Entity1 entity1 = new Entity1();
		populate(entity1);
		System.out.println("Entity populated : " + entity1 );
		
		Entity1Cache.putEntity1(entity1) ;	// Store in cache	
		
		Entity1 entity12 = Entity1Cache.getEntity1(  );
		Assert.assertTrue( entity1 == entity12 ) ; // Same instance
		
		Entity1Cache.removeEntity1(   ) ; // Remove from cache	
		
		Entity1 entity13 = Entity1Cache.getEntity1(  );
		Assert.assertTrue( null == entity13 ) ; // Not in cache
		
	}
}