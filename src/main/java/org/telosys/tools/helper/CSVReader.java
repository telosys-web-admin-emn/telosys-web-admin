package org.telosys.tools.helper;

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Helper classe to parse CSV file.
 *
 * @author Maël Naccache
 * @version 1.0
 */
public class CSVReader
{
	private String text;
	private List<List<String>> datas;

	public CSVReader(String text)
	{
		this.text = text;
		this.datas = new LinkedList<List<String>>();
		this.parse_data();
	}

	private void parse_data()
	{
		String[] lines = this.text.split(System.getProperty("line.separator"));
		for(String line : lines)
		{
			String[] values = line.split(";");
			if(values.length > 0)
			{
				LinkedList value_list = new LinkedList<String>();
				for(String value : values)
					value_list.add(value);
				this.datas.add(value_list);
			}
		}
	}

	public Integer numberOfLines()
	{
		return this.datas.size();
	}

	public Integer numberOfColumn(int line)
	{
		return this.datas.get(line).size();
	}

	public String getColumn(int line, int column)
	{
		return this.datas.get(line).get(column);
	}

	public List<String> getLine(int line)
	{
		return this.datas.get(line);
	}
}
