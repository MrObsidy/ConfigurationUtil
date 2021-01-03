package configurationutil.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import configurationutil.type.Table;

@Deprecated
public class ConfigurationFileHandler {
	
	@Deprecated
	/**
	 * This is untested, use at your own risk.
	 * 
	 * Writes a table to a file.
	 * 
	 * @param config
	 * @param file
	 * @throws IOException
	 */
	public static void writeTableToFile(Table table, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		String serializedConfig = table.serialize();
		writer.write(serializedConfig);
		writer.close();
	}
	
	@Deprecated
	/**
	 * This is untested, use at your own risk.
	 * 
	 * Reads a table from a file and returns it.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Table readTableFromFile(File file) throws IOException {
		FileReader reader = new FileReader(file);
		char[] fileContents = null;
		reader.read(fileContents);
		reader.close();
		
		return Table.fromString(new String(fileContents));
	}
}
