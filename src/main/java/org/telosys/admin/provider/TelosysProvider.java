package org.telosys.admin.provider;

import org.nanoj.web.tinymvc.Action;
import org.nanoj.web.tinymvc.provider.SpecificActionProvider;
import org.telosys.admin.actions.StatisticsAction;
import org.telosys.admin.actions.UserAction;
import org.telosys.admin.actions.UsersAction;
import org.telosys.admin.actions.WelcomeAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexa on 30/03/2016.
 */
public class TelosysProvider extends SpecificActionProvider
{

	private final static Map<String, Class<? extends Action>> map = new HashMap<>();

	//--- Define actions here
	static
	{
		map.put("", WelcomeAction.class);
		map.put("welcome", WelcomeAction.class);
		map.put("users", UsersAction.class);
		map.put("user", UserAction.class);
		map.put("statistics", StatisticsAction.class);
	}

	public TelosysProvider()
	{
		super(map);
	}

}