package org.demo.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

public class TestXavier {

	public static void main(String[] args) throws IOException, ParseException {
		String s = File.separator;
		File usersCsv = new File("C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s+"users.csv"+s);
		File f = new File("C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s);
		/*ModelStatsImpl imp = new ModelStatsImpl(f, "admin", "test", "test");
		System.out.println(imp.getLastModifiedDate());
		System.out.println(imp.getModelName());
		System.out.println(imp.getProjectName());*/
		UsersFileDAO dao = new UsersFileDAO(Configuration.getTelosysSaasLocation()+"/fs/user.cvs");
		User user = dao.loadAllUsers().get("user1");
		UsersStatsImpl imp = new UsersStatsImpl(user, f);
		System.out.println(imp.getModelsCount());
		System.out.println(imp.getDiskUsage());
	}

}
