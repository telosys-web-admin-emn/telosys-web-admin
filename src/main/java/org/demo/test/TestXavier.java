package org.demo.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.telosys.tools.entities.User;
import org.telosys.tools.helper.UsersCsvParser;
import org.telosys.tools.stats.impl.ModelStatsImpl;
import org.telosys.tools.stats.impl.UsersStatsImpl;

public class TestXavier {

	public static void main(String[] args) throws IOException, ParseException {
		String s = File.separator;
		File usersCsv = new File("C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s+"users.csv"+s);
		File f = new File("C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s);
		/*ModelStatsImpl imp = new ModelStatsImpl(f, "admin", "test", "test");
		System.out.println(imp.getLastModifiedDate());
		System.out.println(imp.getModelName());
		System.out.println(imp.getProjectName());*/
		UsersCsvParser parser = new UsersCsvParser(usersCsv);
		User user = parser.parse().stream().filter(u -> u.getUsername().equals("admin")).findAny().get();
		UsersStatsImpl imp = new UsersStatsImpl(user, f);
		System.out.println(imp.getProjectsCount());
		System.out.println(imp.getDiskUsage());
	}

}
