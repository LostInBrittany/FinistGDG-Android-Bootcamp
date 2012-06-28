package org.finistgdg.bootcamp.android.tools;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RandomString {

	private static Log logger = LogFactory.getLog(RandomString.class);
	
	private static Random random = new Random();
	
	public static String generate(int i) {
		if ((i<0) || (i>10)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int j=0; j<i; j++){
			sb.append((char) random.nextInt(128));
		}
		return sb.toString();
	}
	
	public static String randomWithMD5(String key){
		
		
		return DigestUtils.md5Hex(key+generate(10));
		
	}
}
