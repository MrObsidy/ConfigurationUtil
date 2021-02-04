package configurationutil.io;

import java.util.ArrayList;
import java.util.regex.Pattern;

import configurationutil.type.Table;

public class Unserializer {
	
	private static String getKey(String input) {
		//System.out.println(input);
		input = input.split(Pattern.quote(":"), 2)[0];
		input = input.split(Pattern.quote("['"), 2)[1];
		input = input.split(Pattern.quote("']"), 2)[0];
		
		return input;
	}
	
	private static String getValue(String input) {
		input = input.split(Pattern.quote(":"), 2)[1];
		//System.out.println(input);
		input = input.split(Pattern.quote("'"), 2)[1];
		input = input.split(Pattern.quote("'"), 2)[0];
		
		return input;
	}
	
	private static String truncateSegment(String input) {
		//System.out.println(input);
		
		String truncated = "";
		
		String[] lines = input.split(Pattern.quote("\n"));
		
		int count = 0;
		for(String line : lines) {
			if(line.contains("{")) {
				if(count != 0) {
					truncated = truncated + line + "\n";
				}
				count++;
			} else if(line.contains("};")) {
				count--;
				if(count != 0) {
					truncated = truncated + line + "\n";
					//System.out.println(count + truncated);
				}
			} else {
				truncated = truncated + line + "\n";
			}
		}
		
		//System.out.println(truncated);
		
		return truncated;
	}
	
	private static String[] subdivideSegments(String input) {
		ArrayList<String> segments = new ArrayList<String>();
		
		//System.out.println("===");
		//System.out.println(input);
		
		String[] inchars = input.split(Pattern.quote("\n"));
		
		int openingBrackets = 0;
		String segment = "";
		for(String c : inchars) {
			if(c.contains("{")) {
				segment = segment + c + "\n";
				openingBrackets++;
			} else if (c.contains("}")) { 
				segment = segment + c + "\n";
				openingBrackets--;
				if(openingBrackets == 0) {
					segments.add(segment);
					segment = "";
				}
			} else if(openingBrackets == 0) {
				segments.add(c);
			} else {
				segment = segment + c + "\n";
			}
		}
		
		//sort out empty lines
		ArrayList<String> removableSegments = new ArrayList<String>();
		for(String s : segments) {
			if(!(s.contains(";") || s.contains("{"))) {
				//System.out.println("added " + s + "!");
				removableSegments.add(s);
			}
		}
		
		for(String s : removableSegments) {
			segments.remove(s);
		}
		
		return segments.toArray(new String[segments.size()]); //not sure if this does anything, however it makes me rest easier
	}
	
	public static Table unserialize(String string) throws IllegalArgumentException {
		
		try {
			Table config = new Table(getKey(string));
		
			if(!string.contains("{") && !string.contains("}")) {
			//we're dealing with a simple key-value pair
				config.setValue(getValue(string));
			
				return config;
			}
		
			string = truncateSegment(string);
		
			String[] segments = subdivideSegments(string);
			for(String segment : segments) {
				//System.out.println(segment);
				config.addSubTable(unserialize(segment));
			}
			
			//System.out.println(config.serialize());
			
			return config;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Table could not be parsed: " + e.getCause().toString(), e);
		}
	}
}
