package org.telosys.tools.helper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.telosys.tools.entities.User;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper classe to parse CSV file.
 *
 * @author Maël Naccache
 * @version 1.0
 */
public class UsersCsvParser {

	private File file;

	public UsersCsvParser(File file) {
		this.file = file;
	}


	public List<User> parse() throws IOException, ParseException {
		List<User> users = new ArrayList<>();
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withColumnSeparator(';');
		mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
		Iterator<String[]> it = mapper.readerFor(String[].class).with(schema).readValues(file);
		while(it.hasNext()) {
			String[] row = it.next();
			users.add(new User(row[0], row[2], row[3], row[4], row[5]));
		}
		return users;
	}

}
