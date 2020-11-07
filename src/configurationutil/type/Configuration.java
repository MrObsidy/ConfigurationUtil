package configurationutil.type;

import java.util.ArrayList;

import configurationutil.io.Serializer;

public class Configuration {
	private final String key;
	private  String value;
	
	private boolean value_lk;
	
	private final ArrayList<Configuration> subConfigs = new ArrayList<Configuration>();
	
	public Configuration(String key) {
		this.key = key;
	}
	
	public void setValue(String value) {
		this.value = value;
		this.value_lk = true;
	}
	
	public boolean hasValue() {
		return this.value_lk;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public ArrayList<Configuration> getSubConfigurations() {
		return this.subConfigs;
	}
	
	public Configuration getSubConfiguration(String key) {
		for(Configuration config : this.subConfigs) {
			if(config.getKey().equals(key)) return config;
		}
		return null;
	}
	
	public void addSubConfiguration(Configuration config) {
		this.subConfigs.add(config);
	}
	
	@Override
	public String toString() {
		return Serializer.serialize(this, 0);
	}
}
