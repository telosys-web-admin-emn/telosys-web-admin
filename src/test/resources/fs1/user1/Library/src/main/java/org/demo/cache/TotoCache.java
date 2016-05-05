/*
 * Very basic JavaBean cache
 * Created on 12 janv. 2016 ( Date ISO 2016-01-12 - Time 09:59:46 )
 * Generated by Telosys Tools Generator ( version 3.0.0 )
 */

package org.demo.cache;

import java.util.Hashtable;
import java.util.Map;

import org.demo.bean.Toto ;

/**
 * Very basic cache for Toto instances (just for the Telosys Tools demo)
 * 
 * @author Telosys Tools StatisticsSnapshotGenerator
 *
 */
public class TotoCache
{
	private final static Map<String,Toto> cache = new Hashtable<String,Toto>() ;
	
	/**
	 * Build the cache key from the Primary Key field(s)
	 * @return the key
	 */
	private final static String getKey(  ) {
		return ""  ;
	}

	/**
	 * Put the given Toto instance in the cache
	 * @param Toto instance to be stored
	 */
	public final static void putToto(Toto toto ) {
		String key = getKey(  ) ;
		cache.put(key, toto );
	}
	
	/**
	 * Get the Toto instance for the given primary key
	 * @return the Toto instance (or null if none)
	 */
	public final static Toto getToto(  ) {
		String key = getKey(  ) ;
		return cache.get(key);
	}

	/**
	 * Removes the Toto associated with the given primary key
	 */
	public final static void removeToto (  ) {
		String key = getKey(  ) ;
		cache.remove(key);
	}
	
	/**
	 * Removes the given Toto from the cache using its primary key
	 * @param Toto instance to be removed
	 */
	public final static void removeToto (Toto toto ) { 
		String key = getKey(  ) ;
		cache.remove(key);
	}

}
