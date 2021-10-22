package softixx.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;

public class UList {

	public static String toString(List<String> list) {
		val delim = ",";
		val str = list.stream()
					  .map(Object::toString)
					  .collect(Collectors.joining(delim));
		return str;
	}
	
	public static List<String> toList(String[] source) {
		if(source != null && source.length >= 1) {
			return new ArrayList<>(Arrays.asList(source));
		}
		return new ArrayList<>();
	}
	
	public static String[] toArray(List<String> list) {
		if(list != null && !list.isEmpty()) {
			return list.toArray(new String[0]); 
		}
		return new String[] {};
	}
	
	public static List<String> union(List<String> listOne, List<String> listTwo) {
		if(listOne != null && listTwo != null) {
			val set = new HashSet<String>();
		    set.addAll(listOne);
		    set.addAll(listTwo);
				
		    return new ArrayList<>(set);
		}
		return new ArrayList<>();
	}
	
	public static List<String> intersection(List<String> listOne, List<String> listTwo) {
		if(listOne != null && listTwo != null) {
			val result = listOne.stream()
								.distinct()
								.filter(listTwo::contains)
								.collect(Collectors.toSet());
			return new ArrayList<>(result);
		}
		return new ArrayList<>();
	}
	
	public static List<String> difference(List<String> listOne, List<String> listTwo) {
		if(listOne != null && listTwo != null) {
			val differences = listOne.stream()
		            				 .filter(element -> !listTwo.contains(element))
		            				 .collect(Collectors.toList());
			return differences;
		}
		return new ArrayList<>();
	}
}