package configurationutil.io;

import java.util.ArrayList;
import java.util.regex.Pattern;

import configurationutil.type.Configuration;

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
		
		truncated = input.split(Pattern.quote(": {"), 2)[1];
		truncated = new StringBuilder(truncated).reverse().toString();
		truncated = truncated.split(Pattern.quote("};"), 2)[0];
		truncated = new StringBuilder(truncated).reverse().toString();
		
		
		//System.out.println(truncated);
		
		return truncated;
	}
	
	private static String[] subdivideSegments(String input) {
		ArrayList<String> segments = new ArrayList<String>();
		
		String[] inchars = input.split(Pattern.quote("\n"));
		
		int openingBrackets = 0;
		String segment = "";
		for(String c : inchars) {
			if(c.contains("{")) {
				segment = segment + c + "\n";
				openingBrackets++;
			} else if (c.contains("}")) { 
				openingBrackets--;
				if(openingBrackets == 0) {
					segment = segment + c + "\n";
					segments.add(segment);
					segment = "";
				}
			} else if (!c.contains("{") && !c.contains("}") && openingBrackets != 0){
				segment = segment + c + "\n";
			} else {
				segments.add(c);
			}
		}
		
		//sort out empty lines
		ArrayList<String> removableSegments = new ArrayList<String>();
		for(String s : segments) {
			if(!s.contains(":")) {
				removableSegments.add(s);
			}
		}
		
		for(String s : removableSegments) {
			segments.remove(s);
		}
		
		return segments.toArray(new String[segments.size()]); //not sure if this does anything, however it makes me rest easier
	}
	
	public static Configuration unserializeConfiguration(String string) {
		//System.out.println("Fired");
		//System.out.println(string);
		
		Configuration config = new Configuration(getKey(string));
		
		//System.out.println(string);
		
		if(!string.contains("{") && !string.contains("}")) {
			//we're dealing with a simple key-value pair
			//System.out.println(getValue(string));
			//System.out.println(string + string.contains("{"));
			config.setValue(getValue(string));
			
			return config;
		}
		
		string = truncateSegment(string);
		
		String[] segments = subdivideSegments(string);
		for(String segment : segments) {
			//System.out.println("SEGMENT");
			//System.out.println(segment + "\n");
			config.addSubConfiguration(unserializeConfiguration(segment));
		}
		
		return config;
	}
}
