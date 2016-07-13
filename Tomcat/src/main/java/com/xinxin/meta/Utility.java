package com.xinxin.meta;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.mysql.jdbc.Blob;

public class Utility {
	
	public static String blobToString(Blob input) throws SQLException, IOException{
		//debug
		String output = " ";
		if(input != null){
			InputStream is = input.getBinaryStream();			
			ByteArrayInputStream bais = (ByteArrayInputStream)is;
			byte[] byte_data = new byte[bais.available()];
			bais.read(byte_data, 0,byte_data.length);
			output = new String(byte_data,"UTF-8");
			is.close();
		}
		return output;
	}
	
	public static String blobProcesser(String input) throws SQLException, IOException{
		//debug
		String output = " ";
		if(input != null){			
			ByteArrayInputStream bais =  new ByteArrayInputStream(input.getBytes());
			byte[] byte_data = new byte[bais.available()];
			bais.read(byte_data, 0,byte_data.length);
			output = new String(byte_data,"UTF-8");
		}
		return output;
	}
}
