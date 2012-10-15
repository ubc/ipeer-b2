package ca.ubc.ctlt.ipeerb2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;

import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

public class iPeerB2Util {
	
	public static Group findGroupInList(Group group, List<Group> groupList) {
		for(Group g : groupList) {
			if (g.getName() != null && g.getName().equals(group.getName())) {
				return g;
			}
		}
		
		return null;
	}
	
	public static User findUserInList(User user, List<User> userList) {
		for(User u : userList) {
			if (u.getUsername() != null && u.getUsername().equals(user.getUsername())) {
				return u;
			}
		}
		
		return null;
	}
	
	public static String calcSignature(String username, long timestamp, String token, String secret) {
		SecretKey secretKey = null;
		String baseString = username+timestamp+token;
	    byte[] text = baseString.getBytes();
	    byte[] keyBytes = secret.getBytes();
	    secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
	    Mac mac = null;
		try {
			mac = Mac.getInstance("HmacSHA1");
		    mac.init(secretKey);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}

	    return new String(Base64.encode(mac.doFinal(text))).trim();
	}
	
	public static String urlEncode(String param) {
		String encoded = "";
		try {
			encoded = URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		return encoded;
	}
}
