package com.example.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringToArray {
public static List<Map<String, Object>> StringToArray(String string) {
	List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	String string2=string.substring(2,string.length()-2);
	String[] sourceStrArray = string2.split(",|\\}|\\{|:|\"");
	String[] strArray = new String [sourceStrArray.length];
	int j=0;	
	for (int i = 0; i < sourceStrArray.length; i++) {
		if(!"".equals(sourceStrArray[i])) {
	       strArray[j++]=sourceStrArray[i];
		}
	}
	for (int i = 0; i <j; i++) {
		Map<String, Object>map=new HashMap<String, Object>();
		for (int k = i; k <=i+13;) {
			map.put(strArray[k++],strArray[k++]);
		}
		i=i+13;
		list.add(map);
	}
	System.out.println(list);
	return list;
}
}
