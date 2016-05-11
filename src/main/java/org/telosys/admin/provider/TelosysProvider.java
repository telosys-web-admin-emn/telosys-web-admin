package org.telosys.admin.provider;

import org.nanoj.web.tinymvc.Action;
import org.nanoj.web.tinymvc.provider.SpecificActionProvider;
import org.telosys.admin.actions.DeleteUserAction;
import org.telosys.admin.actions.StatisticsAction;
import org.telosys.admin.actions.UserAction;
import org.telosys.admin.actions.UsersAction;

import java.util.HashMap;
import java.util.Map;

public class TelosysProvider extends SpecificActionProvider
{

	private final static Map<String, Class<? extends Action>> map = new HashMap<>();

	//--- Define actions here
	static
	{
		map.put("welcome", StatisticsAction.class);
		map.put("users", UsersAction.class);
		map.put("user", UserAction.class);
		map.put("deleteUser", DeleteUserAction.class);
	}

	public TelosysProvider()
	{
		super(map);
	}

}
