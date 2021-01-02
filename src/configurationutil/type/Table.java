package configurationutil.type;

import java.util.ArrayList;

import configurationutil.io.Serializer;
import configurationutil.io.Unserializer;

public class Table {
	private final String key;
	private  String value;
	
	private boolean value_lk;
	
	private final ArrayList<Table> subTables = new ArrayList<Table>();
	
	public static Table fromString(String input) {
		return Unserializer.unserialize(input);
	}
	
	public Table(String key) {
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
	
	public ArrayList<Table> getSubConfigurations() {
		return this.subTables;
	}
	
	public Table getSubConfiguration(String key) {
		for(Table table : this.subTables) {
			if(table.getKey().equals(key)) return table;
		}
		return null;
	}
	
	/**
	 * Adds a subtable to this table. If a subtable with the key of par is already
	 * in this table, it gets replaced by par.
	 * 
	 * @param par
	 */
	public void addSubTable(Table par) {
		int index = 0;
		for(Table table : this.subTables) {
			if(table.getKey().equals(par.getKey())) {
				this.subTables.set(index, par);
				return;
			}
			index++;
		}
		this.subTables.add(par);
	}
	
	@Override
	public String toString() {
		return Serializer.serialize(this, 0);
	}
	
	public String serialize() {
		return Serializer.serialize(this, 0);
	}
}
