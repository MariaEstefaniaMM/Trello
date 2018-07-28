package memm.org.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	private static String hashtext;
	
	public static String returnEncrypt(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1,hash);
		hashtext = number.toString(16);
		return hashtext;	
	}
}