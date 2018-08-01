package top.meem.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	private static Properties p = null;
	
	private PropertiesUtils(){}
	
	public  static synchronized Properties getInstance(String path){
		if(p == null)
			return getProperties(path);
		return p;
	}

	private static Properties getProperties(String path) {
		// TODO Auto-generated method stub
		
		p= new Properties();
        try {  
        	FileInputStream input = new FileInputStream(path); 
        	p.load(input);   
		  } catch (IOException e) {   
			e.printStackTrace();   
		  }   
        
		return p;
	}
}
