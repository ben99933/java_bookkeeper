package clwhthr.util.hash;

import java.math.BigInteger;
import java.security.MessageDigest;

public final class SHA {
	
	private static final String KEY_SHA = "SHA";   
    public static String getResult(String inputStr)
    {
        BigInteger sha =null;
        byte[] inputData = inputStr.getBytes();   
        try {
             MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
             messageDigest.update(inputData);
             sha = new BigInteger(messageDigest.digest());   
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return sha.toString(32);
    }
}
