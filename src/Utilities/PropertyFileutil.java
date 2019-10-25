package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileutil 
{
	public static String getValueforKey(String key)throws Throwable
	{
		Properties configprop=new Properties();
		FileInputStream fis=new FileInputStream("D:\\mrng930batch\\ERP_Stock\\PropetyFile\\Environment.properties");
				configprop.load(fis);
		return configprop.getProperty(key);
		
}
}