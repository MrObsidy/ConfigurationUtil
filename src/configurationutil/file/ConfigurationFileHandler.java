package configurationutil.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import configurationutil.io.Serializer;
import configurationutil.io.Unserializer;
import configurationutil.type.Configuration;

public class ConfigurationFileHandler {
	public static void writeConfigurationToFile(Configuration config, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		String serializedConfig = Serializer.serialize(config, 0);
		writer.write(serializedConfig);
		writer.close();
	}
	
	public static Configuration readConfigurationFromFile(File file) throws IOException {
		FileReader reader = new FileReader(file);
		char[] fileContents = null;
		reader.read(fileContents);
		reader.close();
		
		return Unserializer.unserializeConfiguration(new String(fileContents));
	}
}
