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
	
	/**
	 * Returns a list of all subtables.
	 * 
	 * @return
	 */
	public ArrayList<Table> getSubTables() {
		return this.subTables;
	}
	
	/**
	 * Returns the subtable with the key key. If none exists, it returns null.
	 * 
	 * @param key
	 * @return
	 */
	public Table getSubTable(String key) {
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
	
	/**
	 * Removes the subtable table from this table.
	 * 
	 * @param table
	 */
	public void removeSubTable(Table table) {
		this.subTables.remove(table);
	}
	
	/**
	 * Removes the subtable with the key key from this table.
	 * 
	 * @param key
	 */
	public void removeSubTable(String key) {
		Table toRemove = null;
		for(Table table : this.subTables) {
			if(table.getKey().equals(key)) {
				toRemove = table;
			}
		}
		
		if(toRemove != null) {
			this.subTables.remove(toRemove);
		}
	}

	/**
	 * Acts the same as Table.serialize() (Implicitly calls Serializer.serialize(this, 0)). See also: Object.toString()
	 * 
	 */
	@Override
	public String toString() {
		return Serializer.serialize(this, 0);
	}
	
	/**
	 * Returns a string representation of this table that can be re-read into a table object.
	 * 
	 * @return
	 */
	public String serialize() {
		return Serializer.serialize(this, 0);
	}
}
