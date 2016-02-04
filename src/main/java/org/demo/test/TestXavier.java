package org.demo.test;

import java.io.File;
import java.io.IOException;

import org.telosys.tools.stats.impl.ModelStatsImpl;
import org.telosys.tools.stats.impl.UsersStatsImpl;

public class TestXavier {

	public static void main(String[] args) throws IOException {
		String s = File.separator;
		File f = new File("C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s);
		/*ModelStatsImpl imp = new ModelStatsImpl(f, "admin", "test", "test");
		System.out.println(imp.getLastModifiedDate());
		System.out.println(imp.getModelName());
		System.out.println(imp.getProjectName());*/
		
		UsersStatsImpl imp = new UsersStatsImpl(f, "admin");
		System.out.println(imp.getProjectsCount());
	}

}
