/*
 * JUnit test case for Hhhh caching service
 * Created on 12 janv. 2016 ( Date ISO 2016-01-12 - Time 09:59:46 )
 * Generated by Telosys Tools Generator ( version 3.0.0 )
 */

package org.demo.test.cache;


import org.demo.bean.Hhhh ;
import org.demo.cache.HhhhCache ;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for Hhhh caching service
 * 
 * @author Telosys Tools StatisticsSnapshotGenerator
 *
 */
public class HhhhCacheTest 
{
	protected static final java.util.Date now = new java.util.Date();

	private final static void populate(Hhhh hhhh) {
		hhhh.setMyfield( "A" ) ;
	}

	@Test
	public void testPutGetRemove() {
		
		System.out.println("Testing class HhhhCache ..." );
		
		Hhhh hhhh = new Hhhh();
		populate(hhhh);
		System.out.println("Entity populated : " + hhhh );
		
		HhhhCache.putHhhh(hhhh) ;	// Store in cache	
		
		Hhhh hhhh2 = HhhhCache.getHhhh(  );
		Assert.assertTrue( hhhh == hhhh2 ) ; // Same instance
		
		HhhhCache.removeHhhh(   ) ; // Remove from cache	
		
		Hhhh hhhh3 = HhhhCache.getHhhh(  );
		Assert.assertTrue( null == hhhh3 ) ; // Not in cache
		
	}
}
