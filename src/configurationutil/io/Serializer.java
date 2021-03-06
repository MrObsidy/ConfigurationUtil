package configurationutil.io;

import java.util.ArrayList;

import configurationutil.type.Configuration;

public class Serializer {
	
	private static String generateTabs(int tabCount) {
		String tabs = "";
		
		int i = 0;
		while(i < tabCount) {
			tabs = tabs + "  ";
			++i;
		}
		
		return tabs;
	}
	
	public static String serialize(Configuration config, int tabs) {
		String serialized = generateTabs(tabs) + "['" + config.getKey() + "'] : ";
		
		if(config.hasValue()) {
			serialized = serialized + "'" + config.getValue() + "';\n";
		} else {
			serialized = serialized + "{ \n";
			ArrayList<Configuration> subConfigs = config.getSubConfigurations();
			for (Configuration subConfig : subConfigs) {
				serialized = serialized + Serializer.serialize(subConfig, tabs + 1);
			}
			serialized = serialized + generateTabs(tabs) + "}; \n";
		}
		
		return serialized;
	}
}
