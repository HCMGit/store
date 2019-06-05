package com.example.pojo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SQLCheck {
	
	

/**正则表达式**/
  private static String reg = "/select|update|delete|exec|count|'|\"|=|;|>|<|%/i";
  private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
 public static boolean isValid(String str)
    {
        if (sqlPattern.matcher(str).find())
        {
            System.out.println("未能通过过滤器：str=" + str);
            return false;
        }
        return true;
    }  
	
			
}
